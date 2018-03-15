/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.Controlladores;

import Persistencia.Controlladores.exceptions.NonexistentEntityException;
import Persistencia.Controlladores.exceptions.PreexistingEntityException;
import Persistencia.Recepcionista;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author marianacro
 */
public class RecepcionistaJpaController implements Serializable {

    public RecepcionistaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recepcionista recepcionista) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(recepcionista);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecepcionista(recepcionista.getNumPersonal()) != null) {
                throw new PreexistingEntityException("Recepcionista " + recepcionista + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recepcionista recepcionista) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            recepcionista = em.merge(recepcionista);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = recepcionista.getNumPersonal();
                if (findRecepcionista(id) == null) {
                    throw new NonexistentEntityException("The recepcionista with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recepcionista recepcionista;
            try {
                recepcionista = em.getReference(Recepcionista.class, id);
                recepcionista.getNumPersonal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recepcionista with id " + id + " no longer exists.", enfe);
            }
            em.remove(recepcionista);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recepcionista> findRecepcionistaEntities() {
        return findRecepcionistaEntities(true, -1, -1);
    }

    public List<Recepcionista> findRecepcionistaEntities(int maxResults, int firstResult) {
        return findRecepcionistaEntities(false, maxResults, firstResult);
    }

    private List<Recepcionista> findRecepcionistaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recepcionista.class));
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

    public Recepcionista findRecepcionista(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recepcionista.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecepcionistaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recepcionista> rt = cq.from(Recepcionista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
