/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.Asesor;
import persistencia.CatalogoEE;
import persistencia.Inscripcion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.ExperienciaEducativa;
import persistencia.controladores.exceptions.IllegalOrphanException;
import persistencia.controladores.exceptions.NonexistentEntityException;
import persistencia.controladores.exceptions.PreexistingEntityException;

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
        if (experienciaEducativa.getInscripcionCollection() == null) {
            experienciaEducativa.setInscripcionCollection(new ArrayList<Inscripcion>());
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
            Collection<Inscripcion> attachedInscripcionCollection = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionCollectionInscripcionToAttach : experienciaEducativa.getInscripcionCollection()) {
                inscripcionCollectionInscripcionToAttach = em.getReference(inscripcionCollectionInscripcionToAttach.getClass(), inscripcionCollectionInscripcionToAttach.getNumInscripcion());
                attachedInscripcionCollection.add(inscripcionCollectionInscripcionToAttach);
            }
            experienciaEducativa.setInscripcionCollection(attachedInscripcionCollection);
            em.persist(experienciaEducativa);
            if (numPersonal != null) {
                numPersonal.getExperienciaEducativaCollection().add(experienciaEducativa);
                numPersonal = em.merge(numPersonal);
            }
            if (codigo != null) {
                codigo.getExperienciaEducativaCollection().add(experienciaEducativa);
                codigo = em.merge(codigo);
            }
            for (Inscripcion inscripcionCollectionInscripcion : experienciaEducativa.getInscripcionCollection()) {
                ExperienciaEducativa oldNrcOfInscripcionCollectionInscripcion = inscripcionCollectionInscripcion.getNrc();
                inscripcionCollectionInscripcion.setNrc(experienciaEducativa);
                inscripcionCollectionInscripcion = em.merge(inscripcionCollectionInscripcion);
                if (oldNrcOfInscripcionCollectionInscripcion != null) {
                    oldNrcOfInscripcionCollectionInscripcion.getInscripcionCollection().remove(inscripcionCollectionInscripcion);
                    oldNrcOfInscripcionCollectionInscripcion = em.merge(oldNrcOfInscripcionCollectionInscripcion);
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
            Collection<Inscripcion> inscripcionCollectionOld = persistentExperienciaEducativa.getInscripcionCollection();
            Collection<Inscripcion> inscripcionCollectionNew = experienciaEducativa.getInscripcionCollection();
            List<String> illegalOrphanMessages = null;
            for (Inscripcion inscripcionCollectionOldInscripcion : inscripcionCollectionOld) {
                if (!inscripcionCollectionNew.contains(inscripcionCollectionOldInscripcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inscripcion " + inscripcionCollectionOldInscripcion + " since its nrc field is not nullable.");
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
            Collection<Inscripcion> attachedInscripcionCollectionNew = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionCollectionNewInscripcionToAttach : inscripcionCollectionNew) {
                inscripcionCollectionNewInscripcionToAttach = em.getReference(inscripcionCollectionNewInscripcionToAttach.getClass(), inscripcionCollectionNewInscripcionToAttach.getNumInscripcion());
                attachedInscripcionCollectionNew.add(inscripcionCollectionNewInscripcionToAttach);
            }
            inscripcionCollectionNew = attachedInscripcionCollectionNew;
            experienciaEducativa.setInscripcionCollection(inscripcionCollectionNew);
            experienciaEducativa = em.merge(experienciaEducativa);
            if (numPersonalOld != null && !numPersonalOld.equals(numPersonalNew)) {
                numPersonalOld.getExperienciaEducativaCollection().remove(experienciaEducativa);
                numPersonalOld = em.merge(numPersonalOld);
            }
            if (numPersonalNew != null && !numPersonalNew.equals(numPersonalOld)) {
                numPersonalNew.getExperienciaEducativaCollection().add(experienciaEducativa);
                numPersonalNew = em.merge(numPersonalNew);
            }
            if (codigoOld != null && !codigoOld.equals(codigoNew)) {
                codigoOld.getExperienciaEducativaCollection().remove(experienciaEducativa);
                codigoOld = em.merge(codigoOld);
            }
            if (codigoNew != null && !codigoNew.equals(codigoOld)) {
                codigoNew.getExperienciaEducativaCollection().add(experienciaEducativa);
                codigoNew = em.merge(codigoNew);
            }
            for (Inscripcion inscripcionCollectionNewInscripcion : inscripcionCollectionNew) {
                if (!inscripcionCollectionOld.contains(inscripcionCollectionNewInscripcion)) {
                    ExperienciaEducativa oldNrcOfInscripcionCollectionNewInscripcion = inscripcionCollectionNewInscripcion.getNrc();
                    inscripcionCollectionNewInscripcion.setNrc(experienciaEducativa);
                    inscripcionCollectionNewInscripcion = em.merge(inscripcionCollectionNewInscripcion);
                    if (oldNrcOfInscripcionCollectionNewInscripcion != null && !oldNrcOfInscripcionCollectionNewInscripcion.equals(experienciaEducativa)) {
                        oldNrcOfInscripcionCollectionNewInscripcion.getInscripcionCollection().remove(inscripcionCollectionNewInscripcion);
                        oldNrcOfInscripcionCollectionNewInscripcion = em.merge(oldNrcOfInscripcionCollectionNewInscripcion);
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
            Collection<Inscripcion> inscripcionCollectionOrphanCheck = experienciaEducativa.getInscripcionCollection();
            for (Inscripcion inscripcionCollectionOrphanCheckInscripcion : inscripcionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ExperienciaEducativa (" + experienciaEducativa + ") cannot be destroyed since the Inscripcion " + inscripcionCollectionOrphanCheckInscripcion + " in its inscripcionCollection field has a non-nullable nrc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Asesor numPersonal = experienciaEducativa.getNumPersonal();
            if (numPersonal != null) {
                numPersonal.getExperienciaEducativaCollection().remove(experienciaEducativa);
                numPersonal = em.merge(numPersonal);
            }
            CatalogoEE codigo = experienciaEducativa.getCodigo();
            if (codigo != null) {
                codigo.getExperienciaEducativaCollection().remove(experienciaEducativa);
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
