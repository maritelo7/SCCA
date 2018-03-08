/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.controladores;

import Persistencia.ObservacionGeneral;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Persistencia.Seguimiento;
import Persistencia.controladores.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author marianacro
 */
public class ObservacionGeneralJpaController implements Serializable {

    public ObservacionGeneralJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ObservacionGeneral observacionGeneral) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seguimiento idSeguimiento = observacionGeneral.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento = em.getReference(idSeguimiento.getClass(), idSeguimiento.getIdSeguimiento());
                observacionGeneral.setIdSeguimiento(idSeguimiento);
            }
            em.persist(observacionGeneral);
            if (idSeguimiento != null) {
                idSeguimiento.getObservacionGeneralList().add(observacionGeneral);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ObservacionGeneral observacionGeneral) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ObservacionGeneral persistentObservacionGeneral = em.find(ObservacionGeneral.class, observacionGeneral.getIdObservacionGral());
            Seguimiento idSeguimientoOld = persistentObservacionGeneral.getIdSeguimiento();
            Seguimiento idSeguimientoNew = observacionGeneral.getIdSeguimiento();
            if (idSeguimientoNew != null) {
                idSeguimientoNew = em.getReference(idSeguimientoNew.getClass(), idSeguimientoNew.getIdSeguimiento());
                observacionGeneral.setIdSeguimiento(idSeguimientoNew);
            }
            observacionGeneral = em.merge(observacionGeneral);
            if (idSeguimientoOld != null && !idSeguimientoOld.equals(idSeguimientoNew)) {
                idSeguimientoOld.getObservacionGeneralList().remove(observacionGeneral);
                idSeguimientoOld = em.merge(idSeguimientoOld);
            }
            if (idSeguimientoNew != null && !idSeguimientoNew.equals(idSeguimientoOld)) {
                idSeguimientoNew.getObservacionGeneralList().add(observacionGeneral);
                idSeguimientoNew = em.merge(idSeguimientoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = observacionGeneral.getIdObservacionGral();
                if (findObservacionGeneral(id) == null) {
                    throw new NonexistentEntityException("The observacionGeneral with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ObservacionGeneral observacionGeneral;
            try {
                observacionGeneral = em.getReference(ObservacionGeneral.class, id);
                observacionGeneral.getIdObservacionGral();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The observacionGeneral with id " + id + " no longer exists.", enfe);
            }
            Seguimiento idSeguimiento = observacionGeneral.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento.getObservacionGeneralList().remove(observacionGeneral);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.remove(observacionGeneral);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ObservacionGeneral> findObservacionGeneralEntities() {
        return findObservacionGeneralEntities(true, -1, -1);
    }

    public List<ObservacionGeneral> findObservacionGeneralEntities(int maxResults, int firstResult) {
        return findObservacionGeneralEntities(false, maxResults, firstResult);
    }

    private List<ObservacionGeneral> findObservacionGeneralEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ObservacionGeneral.class));
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

    public ObservacionGeneral findObservacionGeneral(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ObservacionGeneral.class, id);
        } finally {
            em.close();
        }
    }

    public int getObservacionGeneralCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ObservacionGeneral> rt = cq.from(ObservacionGeneral.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
