/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.Controlladores;

import Persistencia.Bitacora;
import Persistencia.Controlladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Persistencia.Seguimiento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author marianacro
 */
public class BitacoraJpaController implements Serializable {

    public BitacoraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bitacora bitacora) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seguimiento idSeguimiento = bitacora.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento = em.getReference(idSeguimiento.getClass(), idSeguimiento.getIdSeguimiento());
                bitacora.setIdSeguimiento(idSeguimiento);
            }
            em.persist(bitacora);
            if (idSeguimiento != null) {
                idSeguimiento.getBitacoraList().add(bitacora);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bitacora bitacora) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bitacora persistentBitacora = em.find(Bitacora.class, bitacora.getIdBitacora());
            Seguimiento idSeguimientoOld = persistentBitacora.getIdSeguimiento();
            Seguimiento idSeguimientoNew = bitacora.getIdSeguimiento();
            if (idSeguimientoNew != null) {
                idSeguimientoNew = em.getReference(idSeguimientoNew.getClass(), idSeguimientoNew.getIdSeguimiento());
                bitacora.setIdSeguimiento(idSeguimientoNew);
            }
            bitacora = em.merge(bitacora);
            if (idSeguimientoOld != null && !idSeguimientoOld.equals(idSeguimientoNew)) {
                idSeguimientoOld.getBitacoraList().remove(bitacora);
                idSeguimientoOld = em.merge(idSeguimientoOld);
            }
            if (idSeguimientoNew != null && !idSeguimientoNew.equals(idSeguimientoOld)) {
                idSeguimientoNew.getBitacoraList().add(bitacora);
                idSeguimientoNew = em.merge(idSeguimientoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bitacora.getIdBitacora();
                if (findBitacora(id) == null) {
                    throw new NonexistentEntityException("The bitacora with id " + id + " no longer exists.");
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
            Bitacora bitacora;
            try {
                bitacora = em.getReference(Bitacora.class, id);
                bitacora.getIdBitacora();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bitacora with id " + id + " no longer exists.", enfe);
            }
            Seguimiento idSeguimiento = bitacora.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento.getBitacoraList().remove(bitacora);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.remove(bitacora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bitacora> findBitacoraEntities() {
        return findBitacoraEntities(true, -1, -1);
    }

    public List<Bitacora> findBitacoraEntities(int maxResults, int firstResult) {
        return findBitacoraEntities(false, maxResults, firstResult);
    }

    private List<Bitacora> findBitacoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bitacora.class));
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

    public Bitacora findBitacora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bitacora.class, id);
        } finally {
            em.close();
        }
    }

    public int getBitacoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bitacora> rt = cq.from(Bitacora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
