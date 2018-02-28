/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.controlladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.HojaSeguimiento;
import persistencia.Seguimiento;
import persistencia.controlladores.exceptions.NonexistentEntityException;

/**
 *
 * @author marianacro
 */
public class HojaSeguimientoJpaController implements Serializable {

    public HojaSeguimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HojaSeguimiento hojaSeguimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seguimiento idSeguimiento = hojaSeguimiento.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento = em.getReference(idSeguimiento.getClass(), idSeguimiento.getIdSeguimiento());
                hojaSeguimiento.setIdSeguimiento(idSeguimiento);
            }
            em.persist(hojaSeguimiento);
            if (idSeguimiento != null) {
                idSeguimiento.getHojaSeguimientoCollection().add(hojaSeguimiento);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HojaSeguimiento hojaSeguimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HojaSeguimiento persistentHojaSeguimiento = em.find(HojaSeguimiento.class, hojaSeguimiento.getIdHojaSeguimiento());
            Seguimiento idSeguimientoOld = persistentHojaSeguimiento.getIdSeguimiento();
            Seguimiento idSeguimientoNew = hojaSeguimiento.getIdSeguimiento();
            if (idSeguimientoNew != null) {
                idSeguimientoNew = em.getReference(idSeguimientoNew.getClass(), idSeguimientoNew.getIdSeguimiento());
                hojaSeguimiento.setIdSeguimiento(idSeguimientoNew);
            }
            hojaSeguimiento = em.merge(hojaSeguimiento);
            if (idSeguimientoOld != null && !idSeguimientoOld.equals(idSeguimientoNew)) {
                idSeguimientoOld.getHojaSeguimientoCollection().remove(hojaSeguimiento);
                idSeguimientoOld = em.merge(idSeguimientoOld);
            }
            if (idSeguimientoNew != null && !idSeguimientoNew.equals(idSeguimientoOld)) {
                idSeguimientoNew.getHojaSeguimientoCollection().add(hojaSeguimiento);
                idSeguimientoNew = em.merge(idSeguimientoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hojaSeguimiento.getIdHojaSeguimiento();
                if (findHojaSeguimiento(id) == null) {
                    throw new NonexistentEntityException("The hojaSeguimiento with id " + id + " no longer exists.");
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
            HojaSeguimiento hojaSeguimiento;
            try {
                hojaSeguimiento = em.getReference(HojaSeguimiento.class, id);
                hojaSeguimiento.getIdHojaSeguimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hojaSeguimiento with id " + id + " no longer exists.", enfe);
            }
            Seguimiento idSeguimiento = hojaSeguimiento.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento.getHojaSeguimientoCollection().remove(hojaSeguimiento);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.remove(hojaSeguimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HojaSeguimiento> findHojaSeguimientoEntities() {
        return findHojaSeguimientoEntities(true, -1, -1);
    }

    public List<HojaSeguimiento> findHojaSeguimientoEntities(int maxResults, int firstResult) {
        return findHojaSeguimientoEntities(false, maxResults, firstResult);
    }

    private List<HojaSeguimiento> findHojaSeguimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HojaSeguimiento.class));
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

    public HojaSeguimiento findHojaSeguimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HojaSeguimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getHojaSeguimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HojaSeguimiento> rt = cq.from(HojaSeguimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
