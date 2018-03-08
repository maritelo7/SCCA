/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Persistencia.Seguimiento;
import Persistencia.TallerHasSeguimiento;
import Persistencia.controladores.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author marianacro
 */
public class TallerHasSeguimientoJpaController implements Serializable {

    public TallerHasSeguimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TallerHasSeguimiento tallerHasSeguimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seguimiento idSeguimiento = tallerHasSeguimiento.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento = em.getReference(idSeguimiento.getClass(), idSeguimiento.getIdSeguimiento());
                tallerHasSeguimiento.setIdSeguimiento(idSeguimiento);
            }
            em.persist(tallerHasSeguimiento);
            if (idSeguimiento != null) {
                idSeguimiento.getTallerHasSeguimientoList().add(tallerHasSeguimiento);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TallerHasSeguimiento tallerHasSeguimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TallerHasSeguimiento persistentTallerHasSeguimiento = em.find(TallerHasSeguimiento.class, tallerHasSeguimiento.getIdTallerSeguimiento());
            Seguimiento idSeguimientoOld = persistentTallerHasSeguimiento.getIdSeguimiento();
            Seguimiento idSeguimientoNew = tallerHasSeguimiento.getIdSeguimiento();
            if (idSeguimientoNew != null) {
                idSeguimientoNew = em.getReference(idSeguimientoNew.getClass(), idSeguimientoNew.getIdSeguimiento());
                tallerHasSeguimiento.setIdSeguimiento(idSeguimientoNew);
            }
            tallerHasSeguimiento = em.merge(tallerHasSeguimiento);
            if (idSeguimientoOld != null && !idSeguimientoOld.equals(idSeguimientoNew)) {
                idSeguimientoOld.getTallerHasSeguimientoList().remove(tallerHasSeguimiento);
                idSeguimientoOld = em.merge(idSeguimientoOld);
            }
            if (idSeguimientoNew != null && !idSeguimientoNew.equals(idSeguimientoOld)) {
                idSeguimientoNew.getTallerHasSeguimientoList().add(tallerHasSeguimiento);
                idSeguimientoNew = em.merge(idSeguimientoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tallerHasSeguimiento.getIdTallerSeguimiento();
                if (findTallerHasSeguimiento(id) == null) {
                    throw new NonexistentEntityException("The tallerHasSeguimiento with id " + id + " no longer exists.");
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
            TallerHasSeguimiento tallerHasSeguimiento;
            try {
                tallerHasSeguimiento = em.getReference(TallerHasSeguimiento.class, id);
                tallerHasSeguimiento.getIdTallerSeguimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tallerHasSeguimiento with id " + id + " no longer exists.", enfe);
            }
            Seguimiento idSeguimiento = tallerHasSeguimiento.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento.getTallerHasSeguimientoList().remove(tallerHasSeguimiento);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.remove(tallerHasSeguimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TallerHasSeguimiento> findTallerHasSeguimientoEntities() {
        return findTallerHasSeguimientoEntities(true, -1, -1);
    }

    public List<TallerHasSeguimiento> findTallerHasSeguimientoEntities(int maxResults, int firstResult) {
        return findTallerHasSeguimientoEntities(false, maxResults, firstResult);
    }

    private List<TallerHasSeguimiento> findTallerHasSeguimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TallerHasSeguimiento.class));
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

    public TallerHasSeguimiento findTallerHasSeguimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TallerHasSeguimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getTallerHasSeguimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TallerHasSeguimiento> rt = cq.from(TallerHasSeguimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
