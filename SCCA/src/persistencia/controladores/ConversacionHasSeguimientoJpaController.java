/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.controladores;

import Persistencia.ConversacionHasSeguimiento;
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
public class ConversacionHasSeguimientoJpaController implements Serializable {

    public ConversacionHasSeguimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConversacionHasSeguimiento conversacionHasSeguimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seguimiento idSeguimiento = conversacionHasSeguimiento.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento = em.getReference(idSeguimiento.getClass(), idSeguimiento.getIdSeguimiento());
                conversacionHasSeguimiento.setIdSeguimiento(idSeguimiento);
            }
            em.persist(conversacionHasSeguimiento);
            if (idSeguimiento != null) {
                idSeguimiento.getConversacionHasSeguimientoList().add(conversacionHasSeguimiento);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConversacionHasSeguimiento conversacionHasSeguimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConversacionHasSeguimiento persistentConversacionHasSeguimiento = em.find(ConversacionHasSeguimiento.class, conversacionHasSeguimiento.getIdConversacionSeguimiento());
            Seguimiento idSeguimientoOld = persistentConversacionHasSeguimiento.getIdSeguimiento();
            Seguimiento idSeguimientoNew = conversacionHasSeguimiento.getIdSeguimiento();
            if (idSeguimientoNew != null) {
                idSeguimientoNew = em.getReference(idSeguimientoNew.getClass(), idSeguimientoNew.getIdSeguimiento());
                conversacionHasSeguimiento.setIdSeguimiento(idSeguimientoNew);
            }
            conversacionHasSeguimiento = em.merge(conversacionHasSeguimiento);
            if (idSeguimientoOld != null && !idSeguimientoOld.equals(idSeguimientoNew)) {
                idSeguimientoOld.getConversacionHasSeguimientoList().remove(conversacionHasSeguimiento);
                idSeguimientoOld = em.merge(idSeguimientoOld);
            }
            if (idSeguimientoNew != null && !idSeguimientoNew.equals(idSeguimientoOld)) {
                idSeguimientoNew.getConversacionHasSeguimientoList().add(conversacionHasSeguimiento);
                idSeguimientoNew = em.merge(idSeguimientoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = conversacionHasSeguimiento.getIdConversacionSeguimiento();
                if (findConversacionHasSeguimiento(id) == null) {
                    throw new NonexistentEntityException("The conversacionHasSeguimiento with id " + id + " no longer exists.");
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
            ConversacionHasSeguimiento conversacionHasSeguimiento;
            try {
                conversacionHasSeguimiento = em.getReference(ConversacionHasSeguimiento.class, id);
                conversacionHasSeguimiento.getIdConversacionSeguimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conversacionHasSeguimiento with id " + id + " no longer exists.", enfe);
            }
            Seguimiento idSeguimiento = conversacionHasSeguimiento.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento.getConversacionHasSeguimientoList().remove(conversacionHasSeguimiento);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.remove(conversacionHasSeguimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConversacionHasSeguimiento> findConversacionHasSeguimientoEntities() {
        return findConversacionHasSeguimientoEntities(true, -1, -1);
    }

    public List<ConversacionHasSeguimiento> findConversacionHasSeguimientoEntities(int maxResults, int firstResult) {
        return findConversacionHasSeguimientoEntities(false, maxResults, firstResult);
    }

    private List<ConversacionHasSeguimiento> findConversacionHasSeguimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConversacionHasSeguimiento.class));
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

    public ConversacionHasSeguimiento findConversacionHasSeguimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConversacionHasSeguimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getConversacionHasSeguimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConversacionHasSeguimiento> rt = cq.from(ConversacionHasSeguimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
