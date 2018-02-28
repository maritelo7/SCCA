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
import persistencia.Inscripcion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.Alumno;
import persistencia.controlladores.exceptions.IllegalOrphanException;
import persistencia.controlladores.exceptions.NonexistentEntityException;
import persistencia.controlladores.exceptions.PreexistingEntityException;

/**
 *
 * @author marianacro
 */
public class AlumnoJpaController implements Serializable {

    public AlumnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumno alumno) throws PreexistingEntityException, Exception {
        if (alumno.getInscripcionCollection() == null) {
            alumno.setInscripcionCollection(new ArrayList<Inscripcion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Inscripcion> attachedInscripcionCollection = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionCollectionInscripcionToAttach : alumno.getInscripcionCollection()) {
                inscripcionCollectionInscripcionToAttach = em.getReference(inscripcionCollectionInscripcionToAttach.getClass(), inscripcionCollectionInscripcionToAttach.getNumInscripcion());
                attachedInscripcionCollection.add(inscripcionCollectionInscripcionToAttach);
            }
            alumno.setInscripcionCollection(attachedInscripcionCollection);
            em.persist(alumno);
            for (Inscripcion inscripcionCollectionInscripcion : alumno.getInscripcionCollection()) {
                Alumno oldMatriculaOfInscripcionCollectionInscripcion = inscripcionCollectionInscripcion.getMatricula();
                inscripcionCollectionInscripcion.setMatricula(alumno);
                inscripcionCollectionInscripcion = em.merge(inscripcionCollectionInscripcion);
                if (oldMatriculaOfInscripcionCollectionInscripcion != null) {
                    oldMatriculaOfInscripcionCollectionInscripcion.getInscripcionCollection().remove(inscripcionCollectionInscripcion);
                    oldMatriculaOfInscripcionCollectionInscripcion = em.merge(oldMatriculaOfInscripcionCollectionInscripcion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlumno(alumno.getMatricula()) != null) {
                throw new PreexistingEntityException("Alumno " + alumno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumno alumno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno persistentAlumno = em.find(Alumno.class, alumno.getMatricula());
            Collection<Inscripcion> inscripcionCollectionOld = persistentAlumno.getInscripcionCollection();
            Collection<Inscripcion> inscripcionCollectionNew = alumno.getInscripcionCollection();
            List<String> illegalOrphanMessages = null;
            for (Inscripcion inscripcionCollectionOldInscripcion : inscripcionCollectionOld) {
                if (!inscripcionCollectionNew.contains(inscripcionCollectionOldInscripcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Inscripcion " + inscripcionCollectionOldInscripcion + " since its matricula field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Inscripcion> attachedInscripcionCollectionNew = new ArrayList<Inscripcion>();
            for (Inscripcion inscripcionCollectionNewInscripcionToAttach : inscripcionCollectionNew) {
                inscripcionCollectionNewInscripcionToAttach = em.getReference(inscripcionCollectionNewInscripcionToAttach.getClass(), inscripcionCollectionNewInscripcionToAttach.getNumInscripcion());
                attachedInscripcionCollectionNew.add(inscripcionCollectionNewInscripcionToAttach);
            }
            inscripcionCollectionNew = attachedInscripcionCollectionNew;
            alumno.setInscripcionCollection(inscripcionCollectionNew);
            alumno = em.merge(alumno);
            for (Inscripcion inscripcionCollectionNewInscripcion : inscripcionCollectionNew) {
                if (!inscripcionCollectionOld.contains(inscripcionCollectionNewInscripcion)) {
                    Alumno oldMatriculaOfInscripcionCollectionNewInscripcion = inscripcionCollectionNewInscripcion.getMatricula();
                    inscripcionCollectionNewInscripcion.setMatricula(alumno);
                    inscripcionCollectionNewInscripcion = em.merge(inscripcionCollectionNewInscripcion);
                    if (oldMatriculaOfInscripcionCollectionNewInscripcion != null && !oldMatriculaOfInscripcionCollectionNewInscripcion.equals(alumno)) {
                        oldMatriculaOfInscripcionCollectionNewInscripcion.getInscripcionCollection().remove(inscripcionCollectionNewInscripcion);
                        oldMatriculaOfInscripcionCollectionNewInscripcion = em.merge(oldMatriculaOfInscripcionCollectionNewInscripcion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = alumno.getMatricula();
                if (findAlumno(id) == null) {
                    throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno alumno;
            try {
                alumno = em.getReference(Alumno.class, id);
                alumno.getMatricula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Inscripcion> inscripcionCollectionOrphanCheck = alumno.getInscripcionCollection();
            for (Inscripcion inscripcionCollectionOrphanCheckInscripcion : inscripcionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alumno (" + alumno + ") cannot be destroyed since the Inscripcion " + inscripcionCollectionOrphanCheckInscripcion + " in its inscripcionCollection field has a non-nullable matricula field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(alumno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumno> findAlumnoEntities() {
        return findAlumnoEntities(true, -1, -1);
    }

    public List<Alumno> findAlumnoEntities(int maxResults, int firstResult) {
        return findAlumnoEntities(false, maxResults, firstResult);
    }

    private List<Alumno> findAlumnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumno.class));
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

    public Alumno findAlumno(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumno> rt = cq.from(Alumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
