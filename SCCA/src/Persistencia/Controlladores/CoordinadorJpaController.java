/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.Controlladores;

import Persistencia.Controlladores.exceptions.NonexistentEntityException;
import Persistencia.Controlladores.exceptions.PreexistingEntityException;
import Persistencia.Coordinador;
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
public class CoordinadorJpaController implements Serializable {

    public CoordinadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Coordinador coordinador) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(coordinador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCoordinador(coordinador.getNumPersonal()) != null) {
                throw new PreexistingEntityException("Coordinador " + coordinador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Coordinador coordinador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            coordinador = em.merge(coordinador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = coordinador.getNumPersonal();
                if (findCoordinador(id) == null) {
                    throw new NonexistentEntityException("The coordinador with id " + id + " no longer exists.");
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
            Coordinador coordinador;
            try {
                coordinador = em.getReference(Coordinador.class, id);
                coordinador.getNumPersonal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The coordinador with id " + id + " no longer exists.", enfe);
            }
            em.remove(coordinador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Coordinador> findCoordinadorEntities() {
        return findCoordinadorEntities(true, -1, -1);
    }

    public List<Coordinador> findCoordinadorEntities(int maxResults, int firstResult) {
        return findCoordinadorEntities(false, maxResults, firstResult);
    }

    private List<Coordinador> findCoordinadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Coordinador.class));
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

    public Coordinador findCoordinador(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Coordinador.class, id);
        } finally {
            em.close();
        }
    }

    public int getCoordinadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Coordinador> rt = cq.from(Coordinador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
