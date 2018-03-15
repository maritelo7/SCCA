/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.Controlladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Persistencia.Asesor;
import Persistencia.CatalogoEE;
import Persistencia.Controlladores.exceptions.IllegalOrphanException;
import Persistencia.Controlladores.exceptions.NonexistentEntityException;
import Persistencia.Controlladores.exceptions.PreexistingEntityException;
import Persistencia.ExperienciaEducativa;
import Persistencia.Inscripcion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author marianacro
 */
public class ExperienciaEducativaJpaController implements Serializable {

    public ExperienciaEducativaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ExperienciaEducativa experienciaEducativa) throws PreexistingEntityException, Exception {
        if (experienciaEducativa.getInscripcionList() == null) {
            experienciaEducativa.setInscripcionList(new ArrayList<Inscripcion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asesor numPersonal = experienciaEducativa.getNumPersonal();
            if (numPersonal != null) {
                numPersonal = em.getReference(numPersonal.getClass(), numPersonal.getNumPersonal());
                experienciaEducativa.setNumPersonal(numPersonal);
            }
            CatalogoEE codigo = experienciaEducativa.getCodigo();
            if (codigo != null) {
                codigo = em.getReference(codigo.getClass(), codigo.getCodigo());
                experienciaEducativa.setCodigo(codigo);
            }
            List<Inscripcion> attachedInscripcionList = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionListInscripcionToAttach : experienciaEducativa.getInscripcionList()) {
                inscripcionListInscripcionToAttach = em.getReference(inscripcionListInscripcionToAttach.getClass(), inscripcionListInscripcionToAttach.getNumInscripcion());
                attachedInscripcionList.add(inscripcionListInscripcionToAttach);
            }
            experienciaEducativa.setInscripcionList(attachedInscripcionList);
            em.persist(experienciaEducativa);
            if (numPersonal != null) {
                numPersonal.getExperienciaEducativaList().add(experienciaEducativa);
                numPersonal = em.merge(numPersonal);
            }
            if (codigo != null) {
                codigo.getExperienciaEducativaList().add(experienciaEducativa);
                codigo = em.merge(codigo);
            }
            for (Inscripcion inscripcionListInscripcion : experienciaEducativa.getInscripcionList()) {
                ExperienciaEducativa oldNrcOfInscripcionListInscripcion = inscripcionListInscripcion.getNrc();
                inscripcionListInscripcion.setNrc(experienciaEducativa);
                inscripcionListInscripcion = em.merge(inscripcionListInscripcion);
                if (oldNrcOfInscripcionListInscripcion != null) {
                    oldNrcOfInscripcionListInscripcion.getInscripcionList().remove(inscripcionListInscripcion);
                    oldNrcOfInscripcionListInscripcion = em.merge(oldNrcOfInscripcionListInscripcion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findExperienciaEducativa(experienciaEducativa.getNrc()) != null) {
                throw new PreexistingEntityException("ExperienciaEducativa " + experienciaEducativa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ExperienciaEducativa experienciaEducativa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ExperienciaEducativa persistentExperienciaEducativa = em.find(ExperienciaEducativa.class, experienciaEducativa.getNrc());
            Asesor numPersonalOld = persistentExperienciaEducativa.getNumPersonal();
            Asesor numPersonalNew = experienciaEducativa.getNumPersonal();
            CatalogoEE codigoOld = persistentExperienciaEducativa.getCodigo();
            CatalogoEE codigoNew = experienciaEducativa.getCodigo();
            List<Inscripcion> inscripcionListOld = persistentExperienciaEducativa.getInscripcionList();
            List<Inscripcion> inscripcionListNew = experienciaEducativa.getInscripcionList();
            List<String> illegalOrphanMessages = null;
            for (Inscripcion inscripcionListOldInscripcion : inscripcionListOld) {
                if (!inscripcionListNew.contains(inscripcionListOldInscripcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inscripcion " + inscripcionListOldInscripcion + " since its nrc field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (numPersonalNew != null) {
                numPersonalNew = em.getReference(numPersonalNew.getClass(), numPersonalNew.getNumPersonal());
                experienciaEducativa.setNumPersonal(numPersonalNew);
            }
            if (codigoNew != null) {
                codigoNew = em.getReference(codigoNew.getClass(), codigoNew.getCodigo());
                experienciaEducativa.setCodigo(codigoNew);
            }
            List<Inscripcion> attachedInscripcionListNew = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionListNewInscripcionToAttach : inscripcionListNew) {
                inscripcionListNewInscripcionToAttach = em.getReference(inscripcionListNewInscripcionToAttach.getClass(), inscripcionListNewInscripcionToAttach.getNumInscripcion());
                attachedInscripcionListNew.add(inscripcionListNewInscripcionToAttach);
            }
            inscripcionListNew = attachedInscripcionListNew;
            experienciaEducativa.setInscripcionList(inscripcionListNew);
            experienciaEducativa = em.merge(experienciaEducativa);
            if (numPersonalOld != null && !numPersonalOld.equals(numPersonalNew)) {
                numPersonalOld.getExperienciaEducativaList().remove(experienciaEducativa);
                numPersonalOld = em.merge(numPersonalOld);
            }
            if (numPersonalNew != null && !numPersonalNew.equals(numPersonalOld)) {
                numPersonalNew.getExperienciaEducativaList().add(experienciaEducativa);
                numPersonalNew = em.merge(numPersonalNew);
            }
            if (codigoOld != null && !codigoOld.equals(codigoNew)) {
                codigoOld.getExperienciaEducativaList().remove(experienciaEducativa);
                codigoOld = em.merge(codigoOld);
            }
            if (codigoNew != null && !codigoNew.equals(codigoOld)) {
                codigoNew.getExperienciaEducativaList().add(experienciaEducativa);
                codigoNew = em.merge(codigoNew);
            }
            for (Inscripcion inscripcionListNewInscripcion : inscripcionListNew) {
                if (!inscripcionListOld.contains(inscripcionListNewInscripcion)) {
                    ExperienciaEducativa oldNrcOfInscripcionListNewInscripcion = inscripcionListNewInscripcion.getNrc();
                    inscripcionListNewInscripcion.setNrc(experienciaEducativa);
                    inscripcionListNewInscripcion = em.merge(inscripcionListNewInscripcion);
                    if (oldNrcOfInscripcionListNewInscripcion != null && !oldNrcOfInscripcionListNewInscripcion.equals(experienciaEducativa)) {
                        oldNrcOfInscripcionListNewInscripcion.getInscripcionList().remove(inscripcionListNewInscripcion);
                        oldNrcOfInscripcionListNewInscripcion = em.merge(oldNrcOfInscripcionListNewInscripcion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = experienciaEducativa.getNrc();
                if (findExperienciaEducativa(id) == null) {
                    throw new NonexistentEntityException("The experienciaEducativa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ExperienciaEducativa experienciaEducativa;
            try {
                experienciaEducativa = em.getReference(ExperienciaEducativa.class, id);
                experienciaEducativa.getNrc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The experienciaEducativa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Inscripcion> inscripcionListOrphanCheck = experienciaEducativa.getInscripcionList();
            for (Inscripcion inscripcionListOrphanCheckInscripcion : inscripcionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ExperienciaEducativa (" + experienciaEducativa + ") cannot be destroyed since the Inscripcion " + inscripcionListOrphanCheckInscripcion + " in its inscripcionList field has a non-nullable nrc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Asesor numPersonal = experienciaEducativa.getNumPersonal();
            if (numPersonal != null) {
                numPersonal.getExperienciaEducativaList().remove(experienciaEducativa);
                numPersonal = em.merge(numPersonal);
            }
            CatalogoEE codigo = experienciaEducativa.getCodigo();
            if (codigo != null) {
                codigo.getExperienciaEducativaList().remove(experienciaEducativa);
                codigo = em.merge(codigo);
            }
            em.remove(experienciaEducativa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ExperienciaEducativa> findExperienciaEducativaEntities() {
        return findExperienciaEducativaEntities(true, -1, -1);
    }

    public List<ExperienciaEducativa> findExperienciaEducativaEntities(int maxResults, int firstResult) {
        return findExperienciaEducativaEntities(false, maxResults, firstResult);
    }

    private List<ExperienciaEducativa> findExperienciaEducativaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ExperienciaEducativa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ExperienciaEducativa findExperienciaEducativa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ExperienciaEducativa.class, id);
        } finally {
            em.close();
        }
    }

    public int getExperienciaEducativaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ExperienciaEducativa> rt = cq.from(ExperienciaEducativa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
