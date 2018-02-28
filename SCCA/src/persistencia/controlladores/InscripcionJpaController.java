/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.controlladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.Alumno;
import persistencia.ExperienciaEducativa;
import persistencia.Seguimiento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.Inscripcion;
import persistencia.controlladores.exceptions.IllegalOrphanException;
import persistencia.controlladores.exceptions.NonexistentEntityException;

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
        if (inscripcion.getSeguimientoCollection() == null) {
            inscripcion.setSeguimientoCollection(new ArrayList<Seguimiento>());
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
            Collection<Seguimiento> attachedSeguimientoCollection = new ArrayList<Seguimiento>();
            for (Seguimiento seguimientoCollectionSeguimientoToAttach : inscripcion.getSeguimientoCollection()) {
                seguimientoCollectionSeguimientoToAttach = em.getReference(seguimientoCollectionSeguimientoToAttach.getClass(), seguimientoCollectionSeguimientoToAttach.getIdSeguimiento());
                attachedSeguimientoCollection.add(seguimientoCollectionSeguimientoToAttach);
            }
            inscripcion.setSeguimientoCollection(attachedSeguimientoCollection);
            em.persist(inscripcion);
            if (matricula != null) {
                matricula.getInscripcionCollection().add(inscripcion);
                matricula = em.merge(matricula);
            }
            if (nrc != null) {
                nrc.getInscripcionCollection().add(inscripcion);
                nrc = em.merge(nrc);
            }
            for (Seguimiento seguimientoCollectionSeguimiento : inscripcion.getSeguimientoCollection()) {
                Inscripcion oldNumInscripcionOfSeguimientoCollectionSeguimiento = seguimientoCollectionSeguimiento.getNumInscripcion();
                seguimientoCollectionSeguimiento.setNumInscripcion(inscripcion);
                seguimientoCollectionSeguimiento = em.merge(seguimientoCollectionSeguimiento);
                if (oldNumInscripcionOfSeguimientoCollectionSeguimiento != null) {
                    oldNumInscripcionOfSeguimientoCollectionSeguimiento.getSeguimientoCollection().remove(seguimientoCollectionSeguimiento);
                    oldNumInscripcionOfSeguimientoCollectionSeguimiento = em.merge(oldNumInscripcionOfSeguimientoCollectionSeguimiento);
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
            Collection<Seguimiento> seguimientoCollectionOld = persistentInscripcion.getSeguimientoCollection();
            Collection<Seguimiento> seguimientoCollectionNew = inscripcion.getSeguimientoCollection();
            List<String> illegalOrphanMessages = null;
            for (Seguimiento seguimientoCollectionOldSeguimiento : seguimientoCollectionOld) {
                if (!seguimientoCollectionNew.contains(seguimientoCollectionOldSeguimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Seguimiento " + seguimientoCollectionOldSeguimiento + " since its numInscripcion field is not nullable.");
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
            Collection<Seguimiento> attachedSeguimientoCollectionNew = new ArrayList<Seguimiento>();
            for (Seguimiento seguimientoCollectionNewSeguimientoToAttach : seguimientoCollectionNew) {
                seguimientoCollectionNewSeguimientoToAttach = em.getReference(seguimientoCollectionNewSeguimientoToAttach.getClass(), seguimientoCollectionNewSeguimientoToAttach.getIdSeguimiento());
                attachedSeguimientoCollectionNew.add(seguimientoCollectionNewSeguimientoToAttach);
            }
            seguimientoCollectionNew = attachedSeguimientoCollectionNew;
            inscripcion.setSeguimientoCollection(seguimientoCollectionNew);
            inscripcion = em.merge(inscripcion);
            if (matriculaOld != null && !matriculaOld.equals(matriculaNew)) {
                matriculaOld.getInscripcionCollection().remove(inscripcion);
                matriculaOld = em.merge(matriculaOld);
            }
            if (matriculaNew != null && !matriculaNew.equals(matriculaOld)) {
                matriculaNew.getInscripcionCollection().add(inscripcion);
                matriculaNew = em.merge(matriculaNew);
            }
            if (nrcOld != null && !nrcOld.equals(nrcNew)) {
                nrcOld.getInscripcionCollection().remove(inscripcion);
                nrcOld = em.merge(nrcOld);
            }
            if (nrcNew != null && !nrcNew.equals(nrcOld)) {
                nrcNew.getInscripcionCollection().add(inscripcion);
                nrcNew = em.merge(nrcNew);
            }
            for (Seguimiento seguimientoCollectionNewSeguimiento : seguimientoCollectionNew) {
                if (!seguimientoCollectionOld.contains(seguimientoCollectionNewSeguimiento)) {
                    Inscripcion oldNumInscripcionOfSeguimientoCollectionNewSeguimiento = seguimientoCollectionNewSeguimiento.getNumInscripcion();
                    seguimientoCollectionNewSeguimiento.setNumInscripcion(inscripcion);
                    seguimientoCollectionNewSeguimiento = em.merge(seguimientoCollectionNewSeguimiento);
                    if (oldNumInscripcionOfSeguimientoCollectionNewSeguimiento != null && !oldNumInscripcionOfSeguimientoCollectionNewSeguimiento.equals(inscripcion)) {
                        oldNumInscripcionOfSeguimientoCollectionNewSeguimiento.getSeguimientoCollection().remove(seguimientoCollectionNewSeguimiento);
                        oldNumInscripcionOfSeguimientoCollectionNewSeguimiento = em.merge(oldNumInscripcionOfSeguimientoCollectionNewSeguimiento);
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
            Collection<Seguimiento> seguimientoCollectionOrphanCheck = inscripcion.getSeguimientoCollection();
            for (Seguimiento seguimientoCollectionOrphanCheckSeguimiento : seguimientoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Inscripcion (" + inscripcion + ") cannot be destroyed since the Seguimiento " + seguimientoCollectionOrphanCheckSeguimiento + " in its seguimientoCollection field has a non-nullable numInscripcion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Alumno matricula = inscripcion.getMatricula();
            if (matricula != null) {
                matricula.getInscripcionCollection().remove(inscripcion);
                matricula = em.merge(matricula);
            }
            ExperienciaEducativa nrc = inscripcion.getNrc();
            if (nrc != null) {
                nrc.getInscripcionCollection().remove(inscripcion);
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
