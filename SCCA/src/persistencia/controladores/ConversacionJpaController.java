/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.Conversacion;
import persistencia.Reservacion;
import persistencia.controladores.exceptions.NonexistentEntityException;

/**
 *
 * @author marianacro
 */
public class ConversacionJpaController implements Serializable {

    public ConversacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Conversacion conversacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservacion idReservacion = conversacion.getIdReservacion();
            if (idReservacion != null) {
                idReservacion = em.getReference(idReservacion.getClass(), idReservacion.getIdReservacion());
                conversacion.setIdReservacion(idReservacion);
            }
            em.persist(conversacion);
            if (idReservacion != null) {
                idReservacion.getConversacionCollection().add(conversacion);
                idReservacion = em.merge(idReservacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Conversacion conversacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Conversacion persistentConversacion = em.find(Conversacion.class, conversacion.getIdConversacion());
            Reservacion idReservacionOld = persistentConversacion.getIdReservacion();
            Reservacion idReservacionNew = conversacion.getIdReservacion();
            if (idReservacionNew != null) {
                idReservacionNew = em.getReference(idReservacionNew.getClass(), idReservacionNew.getIdReservacion());
                conversacion.setIdReservacion(idReservacionNew);
            }
            conversacion = em.merge(conversacion);
            if (idReservacionOld != null && !idReservacionOld.equals(idReservacionNew)) {
                idReservacionOld.getConversacionCollection().remove(conversacion);
                idReservacionOld = em.merge(idReservacionOld);
            }
            if (idReservacionNew != null && !idReservacionNew.equals(idReservacionOld)) {
                idReservacionNew.getConversacionCollection().add(conversacion);
                idReservacionNew = em.merge(idReservacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = conversacion.getIdConversacion();
                if (findConversacion(id) == null) {
                    throw new NonexistentEntityException("The conversacion with id " + id + " no longer exists.");
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
            Conversacion conversacion;
            try {
                conversacion = em.getReference(Conversacion.class, id);
                conversacion.getIdConversacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conversacion with id " + id + " no longer exists.", enfe);
            }
            Reservacion idReservacion = conversacion.getIdReservacion();
            if (idReservacion != null) {
                idReservacion.getConversacionCollection().remove(conversacion);
                idReservacion = em.merge(idReservacion);
            }
            em.remove(conversacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Conversacion> findConversacionEntities() {
        return findConversacionEntities(true, -1, -1);
    }

    public List<Conversacion> findConversacionEntities(int maxResults, int firstResult) {
        return findConversacionEntities(false, maxResults, firstResult);
    }

    private List<Conversacion> findConversacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Conversacion.class));
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

    public Conversacion findConversacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Conversacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getConversacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Conversacion> rt = cq.from(Conversacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
