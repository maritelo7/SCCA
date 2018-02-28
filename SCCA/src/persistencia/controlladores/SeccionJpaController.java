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
import persistencia.CatalogoEE;
import persistencia.Seccion;
import persistencia.controlladores.exceptions.NonexistentEntityException;
import persistencia.controlladores.exceptions.PreexistingEntityException;

/**
 *
 * @author marianacro
 */
public class SeccionJpaController implements Serializable {

    public SeccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Seccion seccion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CatalogoEE codigo = seccion.getCodigo();
            if (codigo != null) {
                codigo = em.getReference(codigo.getClass(), codigo.getCodigo());
                seccion.setCodigo(codigo);
            }
            em.persist(seccion);
            if (codigo != null) {
                codigo.getSeccionCollection().add(seccion);
                codigo = em.merge(codigo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSeccion(seccion.getNumSeccion()) != null) {
                throw new PreexistingEntityException("Seccion " + seccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Seccion seccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seccion persistentSeccion = em.find(Seccion.class, seccion.getNumSeccion());
            CatalogoEE codigoOld = persistentSeccion.getCodigo();
            CatalogoEE codigoNew = seccion.getCodigo();
            if (codigoNew != null) {
                codigoNew = em.getReference(codigoNew.getClass(), codigoNew.getCodigo());
                seccion.setCodigo(codigoNew);
            }
            seccion = em.merge(seccion);
            if (codigoOld != null && !codigoOld.equals(codigoNew)) {
                codigoOld.getSeccionCollection().remove(seccion);
                codigoOld = em.merge(codigoOld);
            }
            if (codigoNew != null && !codigoNew.equals(codigoOld)) {
                codigoNew.getSeccionCollection().add(seccion);
                codigoNew = em.merge(codigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = seccion.getNumSeccion();
                if (findSeccion(id) == null) {
                    throw new NonexistentEntityException("The seccion with id " + id + " no longer exists.");
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
            Seccion seccion;
            try {
                seccion = em.getReference(Seccion.class, id);
                seccion.getNumSeccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The seccion with id " + id + " no longer exists.", enfe);
            }
            CatalogoEE codigo = seccion.getCodigo();
            if (codigo != null) {
                codigo.getSeccionCollection().remove(seccion);
                codigo = em.merge(codigo);
            }
            em.remove(seccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Seccion> findSeccionEntities() {
        return findSeccionEntities(true, -1, -1);
    }

    public List<Seccion> findSeccionEntities(int maxResults, int firstResult) {
        return findSeccionEntities(false, maxResults, firstResult);
    }

    private List<Seccion> findSeccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Seccion.class));
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

    public Seccion findSeccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Seccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Seccion> rt = cq.from(Seccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
