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
import Persistencia.Alumno;
import Persistencia.Controlladores.exceptions.IllegalOrphanException;
import Persistencia.Controlladores.exceptions.NonexistentEntityException;
import Persistencia.ExperienciaEducativa;
import Persistencia.Inscripcion;
import Persistencia.Seguimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author marianacro
 */
public class InscripcionJpaController implements Serializable {

    public InscripcionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Inscripcion inscripcion) {
        if (inscripcion.getSeguimientoList() == null) {
            inscripcion.setSeguimientoList(new ArrayList<Seguimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno matricula = inscripcion.getMatricula();
            if (matricula != null) {
                matricula = em.getReference(matricula.getClass(), matricula.getMatricula());
                inscripcion.setMatricula(matricula);
            }
            ExperienciaEducativa nrc = inscripcion.getNrc();
            if (nrc != null) {
                nrc = em.getReference(nrc.getClass(), nrc.getNrc());
                inscripcion.setNrc(nrc);
            }
            List<Seguimiento> attachedSeguimientoList = new ArrayList<Seguimiento>();
            for (Seguimiento seguimientoListSeguimientoToAttach : inscripcion.getSeguimientoList()) {
                seguimientoListSeguimientoToAttach = em.getReference(seguimientoListSeguimientoToAttach.getClass(), seguimientoListSeguimientoToAttach.getIdSeguimiento());
                attachedSeguimientoList.add(seguimientoListSeguimientoToAttach);
            }
            inscripcion.setSeguimientoList(attachedSeguimientoList);
            em.persist(inscripcion);
            if (matricula != null) {
                matricula.getInscripcionList().add(inscripcion);
                matricula = em.merge(matricula);
            }
            if (nrc != null) {
                nrc.getInscripcionList().add(inscripcion);
                nrc = em.merge(nrc);
            }
            for (Seguimiento seguimientoListSeguimiento : inscripcion.getSeguimientoList()) {
                Inscripcion oldNumInscripcionOfSeguimientoListSeguimiento = seguimientoListSeguimiento.getNumInscripcion();
                seguimientoListSeguimiento.setNumInscripcion(inscripcion);
                seguimientoListSeguimiento = em.merge(seguimientoListSeguimiento);
                if (oldNumInscripcionOfSeguimientoListSeguimiento != null) {
                    oldNumInscripcionOfSeguimientoListSeguimiento.getSeguimientoList().remove(seguimientoListSeguimiento);
                    oldNumInscripcionOfSeguimientoListSeguimiento = em.merge(oldNumInscripcionOfSeguimientoListSeguimiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Inscripcion inscripcion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inscripcion persistentInscripcion = em.find(Inscripcion.class, inscripcion.getNumInscripcion());
            Alumno matriculaOld = persistentInscripcion.getMatricula();
            Alumno matriculaNew = inscripcion.getMatricula();
            ExperienciaEducativa nrcOld = persistentInscripcion.getNrc();
            ExperienciaEducativa nrcNew = inscripcion.getNrc();
            List<Seguimiento> seguimientoListOld = persistentInscripcion.getSeguimientoList();
            List<Seguimiento> seguimientoListNew = inscripcion.getSeguimientoList();
            List<String> illegalOrphanMessages = null;
            for (Seguimiento seguimientoListOldSeguimiento : seguimientoListOld) {
                if (!seguimientoListNew.contains(seguimientoListOldSeguimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Seguimiento " + seguimientoListOldSeguimiento + " since its numInscripcion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (matriculaNew != null) {
                matriculaNew = em.getReference(matriculaNew.getClass(), matriculaNew.getMatricula());
                inscripcion.setMatricula(matriculaNew);
            }
            if (nrcNew != null) {
                nrcNew = em.getReference(nrcNew.getClass(), nrcNew.getNrc());
                inscripcion.setNrc(nrcNew);
            }
            List<Seguimiento> attachedSeguimientoListNew = new ArrayList<Seguimiento>();
            for (Seguimiento seguimientoListNewSeguimientoToAttach : seguimientoListNew) {
                seguimientoListNewSeguimientoToAttach = em.getReference(seguimientoListNewSeguimientoToAttach.getClass(), seguimientoListNewSeguimientoToAttach.getIdSeguimiento());
                attachedSeguimientoListNew.add(seguimientoListNewSeguimientoToAttach);
            }
            seguimientoListNew = attachedSeguimientoListNew;
            inscripcion.setSeguimientoList(seguimientoListNew);
            inscripcion = em.merge(inscripcion);
            if (matriculaOld != null && !matriculaOld.equals(matriculaNew)) {
                matriculaOld.getInscripcionList().remove(inscripcion);
                matriculaOld = em.merge(matriculaOld);
            }
            if (matriculaNew != null && !matriculaNew.equals(matriculaOld)) {
                matriculaNew.getInscripcionList().add(inscripcion);
                matriculaNew = em.merge(matriculaNew);
            }
            if (nrcOld != null && !nrcOld.equals(nrcNew)) {
                nrcOld.getInscripcionList().remove(inscripcion);
                nrcOld = em.merge(nrcOld);
            }
            if (nrcNew != null && !nrcNew.equals(nrcOld)) {
                nrcNew.getInscripcionList().add(inscripcion);
                nrcNew = em.merge(nrcNew);
            }
            for (Seguimiento seguimientoListNewSeguimiento : seguimientoListNew) {
                if (!seguimientoListOld.contains(seguimientoListNewSeguimiento)) {
                    Inscripcion oldNumInscripcionOfSeguimientoListNewSeguimiento = seguimientoListNewSeguimiento.getNumInscripcion();
                    seguimientoListNewSeguimiento.setNumInscripcion(inscripcion);
                    seguimientoListNewSeguimiento = em.merge(seguimientoListNewSeguimiento);
                    if (oldNumInscripcionOfSeguimientoListNewSeguimiento != null && !oldNumInscripcionOfSeguimientoListNewSeguimiento.equals(inscripcion)) {
                        oldNumInscripcionOfSeguimientoListNewSeguimiento.getSeguimientoList().remove(seguimientoListNewSeguimiento);
                        oldNumInscripcionOfSeguimientoListNewSeguimiento = em.merge(oldNumInscripcionOfSeguimientoListNewSeguimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = inscripcion.getNumInscripcion();
                if (findInscripcion(id) == null) {
                    throw new NonexistentEntityException("The inscripcion with id " + id + " no longer exists.");
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
            Inscripcion inscripcion;
            try {
                inscripcion = em.getReference(Inscripcion.class, id);
                inscripcion.getNumInscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscripcion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Seguimiento> seguimientoListOrphanCheck = inscripcion.getSeguimientoList();
            for (Seguimiento seguimientoListOrphanCheckSeguimiento : seguimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inscripcion (" + inscripcion + ") cannot be destroyed since the Seguimiento " + seguimientoListOrphanCheckSeguimiento + " in its seguimientoList field has a non-nullable numInscripcion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Alumno matricula = inscripcion.getMatricula();
            if (matricula != null) {
                matricula.getInscripcionList().remove(inscripcion);
                matricula = em.merge(matricula);
            }
            ExperienciaEducativa nrc = inscripcion.getNrc();
            if (nrc != null) {
                nrc.getInscripcionList().remove(inscripcion);
                nrc = em.merge(nrc);
            }
            em.remove(inscripcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Inscripcion> findInscripcionEntities() {
        return findInscripcionEntities(true, -1, -1);
    }

    public List<Inscripcion> findInscripcionEntities(int maxResults, int firstResult) {
        return findInscripcionEntities(false, maxResults, firstResult);
    }

    private List<Inscripcion> findInscripcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inscripcion.class));
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

    public Inscripcion findInscripcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inscripcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Inscripcion> rt = cq.from(Inscripcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
