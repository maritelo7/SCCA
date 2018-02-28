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
import persistencia.ExperienciaEducativa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.CatalogoEE;
import persistencia.Seccion;
import persistencia.controlladores.exceptions.IllegalOrphanException;
import persistencia.controlladores.exceptions.NonexistentEntityException;
import persistencia.controlladores.exceptions.PreexistingEntityException;

/**
 *
 * @author marianacro
 */
public class CatalogoEEJpaController implements Serializable {

    public CatalogoEEJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CatalogoEE catalogoEE) throws PreexistingEntityException, Exception {
        if (catalogoEE.getExperienciaEducativaCollection() == null) {
            catalogoEE.setExperienciaEducativaCollection(new ArrayList<ExperienciaEducativa>());
        }
        if (catalogoEE.getSeccionCollection() == null) {
            catalogoEE.setSeccionCollection(new ArrayList<Seccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ExperienciaEducativa> attachedExperienciaEducativaCollection = new ArrayList<ExperienciaEducativa>();
            for (ExperienciaEducativa experienciaEducativaCollectionExperienciaEducativaToAttach : catalogoEE.getExperienciaEducativaCollection()) {
                experienciaEducativaCollectionExperienciaEducativaToAttach = em.getReference(experienciaEducativaCollectionExperienciaEducativaToAttach.getClass(), experienciaEducativaCollectionExperienciaEducativaToAttach.getNrc());
                attachedExperienciaEducativaCollection.add(experienciaEducativaCollectionExperienciaEducativaToAttach);
            }
            catalogoEE.setExperienciaEducativaCollection(attachedExperienciaEducativaCollection);
            Collection<Seccion> attachedSeccionCollection = new ArrayList<Seccion>();
            for (Seccion seccionCollectionSeccionToAttach : catalogoEE.getSeccionCollection()) {
                seccionCollectionSeccionToAttach = em.getReference(seccionCollectionSeccionToAttach.getClass(), seccionCollectionSeccionToAttach.getNumSeccion());
                attachedSeccionCollection.add(seccionCollectionSeccionToAttach);
            }
            catalogoEE.setSeccionCollection(attachedSeccionCollection);
            em.persist(catalogoEE);
            for (ExperienciaEducativa experienciaEducativaCollectionExperienciaEducativa : catalogoEE.getExperienciaEducativaCollection()) {
                CatalogoEE oldCodigoOfExperienciaEducativaCollectionExperienciaEducativa = experienciaEducativaCollectionExperienciaEducativa.getCodigo();
                experienciaEducativaCollectionExperienciaEducativa.setCodigo(catalogoEE);
                experienciaEducativaCollectionExperienciaEducativa = em.merge(experienciaEducativaCollectionExperienciaEducativa);
                if (oldCodigoOfExperienciaEducativaCollectionExperienciaEducativa != null) {
                    oldCodigoOfExperienciaEducativaCollectionExperienciaEducativa.getExperienciaEducativaCollection().remove(experienciaEducativaCollectionExperienciaEducativa);
                    oldCodigoOfExperienciaEducativaCollectionExperienciaEducativa = em.merge(oldCodigoOfExperienciaEducativaCollectionExperienciaEducativa);
                }
            }
            for (Seccion seccionCollectionSeccion : catalogoEE.getSeccionCollection()) {
                CatalogoEE oldCodigoOfSeccionCollectionSeccion = seccionCollectionSeccion.getCodigo();
                seccionCollectionSeccion.setCodigo(catalogoEE);
                seccionCollectionSeccion = em.merge(seccionCollectionSeccion);
                if (oldCodigoOfSeccionCollectionSeccion != null) {
                    oldCodigoOfSeccionCollectionSeccion.getSeccionCollection().remove(seccionCollectionSeccion);
                    oldCodigoOfSeccionCollectionSeccion = em.merge(oldCodigoOfSeccionCollectionSeccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCatalogoEE(catalogoEE.getCodigo()) != null) {
                throw new PreexistingEntityException("CatalogoEE " + catalogoEE + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CatalogoEE catalogoEE) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CatalogoEE persistentCatalogoEE = em.find(CatalogoEE.class, catalogoEE.getCodigo());
            Collection<ExperienciaEducativa> experienciaEducativaCollectionOld = persistentCatalogoEE.getExperienciaEducativaCollection();
            Collection<ExperienciaEducativa> experienciaEducativaCollectionNew = catalogoEE.getExperienciaEducativaCollection();
            Collection<Seccion> seccionCollectionOld = persistentCatalogoEE.getSeccionCollection();
            Collection<Seccion> seccionCollectionNew = catalogoEE.getSeccionCollection();
            List<String> illegalOrphanMessages = null;
            for (ExperienciaEducativa experienciaEducativaCollectionOldExperienciaEducativa : experienciaEducativaCollectionOld) {
                if (!experienciaEducativaCollectionNew.contains(experienciaEducativaCollectionOldExperienciaEducativa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ExperienciaEducativa " + experienciaEducativaCollectionOldExperienciaEducativa + " since its codigo field is not nullable.");
                }
            }
            for (Seccion seccionCollectionOldSeccion : seccionCollectionOld) {
                if (!seccionCollectionNew.contains(seccionCollectionOldSeccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Seccion " + seccionCollectionOldSeccion + " since its codigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ExperienciaEducativa> attachedExperienciaEducativaCollectionNew = new ArrayList<ExperienciaEducativa>();
            for (ExperienciaEducativa experienciaEducativaCollectionNewExperienciaEducativaToAttach : experienciaEducativaCollectionNew) {
                experienciaEducativaCollectionNewExperienciaEducativaToAttach = em.getReference(experienciaEducativaCollectionNewExperienciaEducativaToAttach.getClass(), experienciaEducativaCollectionNewExperienciaEducativaToAttach.getNrc());
                attachedExperienciaEducativaCollectionNew.add(experienciaEducativaCollectionNewExperienciaEducativaToAttach);
            }
            experienciaEducativaCollectionNew = attachedExperienciaEducativaCollectionNew;
            catalogoEE.setExperienciaEducativaCollection(experienciaEducativaCollectionNew);
            Collection<Seccion> attachedSeccionCollectionNew = new ArrayList<Seccion>();
            for (Seccion seccionCollectionNewSeccionToAttach : seccionCollectionNew) {
                seccionCollectionNewSeccionToAttach = em.getReference(seccionCollectionNewSeccionToAttach.getClass(), seccionCollectionNewSeccionToAttach.getNumSeccion());
                attachedSeccionCollectionNew.add(seccionCollectionNewSeccionToAttach);
            }
            seccionCollectionNew = attachedSeccionCollectionNew;
            catalogoEE.setSeccionCollection(seccionCollectionNew);
            catalogoEE = em.merge(catalogoEE);
            for (ExperienciaEducativa experienciaEducativaCollectionNewExperienciaEducativa : experienciaEducativaCollectionNew) {
                if (!experienciaEducativaCollectionOld.contains(experienciaEducativaCollectionNewExperienciaEducativa)) {
                    CatalogoEE oldCodigoOfExperienciaEducativaCollectionNewExperienciaEducativa = experienciaEducativaCollectionNewExperienciaEducativa.getCodigo();
                    experienciaEducativaCollectionNewExperienciaEducativa.setCodigo(catalogoEE);
                    experienciaEducativaCollectionNewExperienciaEducativa = em.merge(experienciaEducativaCollectionNewExperienciaEducativa);
                    if (oldCodigoOfExperienciaEducativaCollectionNewExperienciaEducativa != null && !oldCodigoOfExperienciaEducativaCollectionNewExperienciaEducativa.equals(catalogoEE)) {
                        oldCodigoOfExperienciaEducativaCollectionNewExperienciaEducativa.getExperienciaEducativaCollection().remove(experienciaEducativaCollectionNewExperienciaEducativa);
                        oldCodigoOfExperienciaEducativaCollectionNewExperienciaEducativa = em.merge(oldCodigoOfExperienciaEducativaCollectionNewExperienciaEducativa);
                    }
                }
            }
            for (Seccion seccionCollectionNewSeccion : seccionCollectionNew) {
                if (!seccionCollectionOld.contains(seccionCollectionNewSeccion)) {
                    CatalogoEE oldCodigoOfSeccionCollectionNewSeccion = seccionCollectionNewSeccion.getCodigo();
                    seccionCollectionNewSeccion.setCodigo(catalogoEE);
                    seccionCollectionNewSeccion = em.merge(seccionCollectionNewSeccion);
                    if (oldCodigoOfSeccionCollectionNewSeccion != null && !oldCodigoOfSeccionCollectionNewSeccion.equals(catalogoEE)) {
                        oldCodigoOfSeccionCollectionNewSeccion.getSeccionCollection().remove(seccionCollectionNewSeccion);
                        oldCodigoOfSeccionCollectionNewSeccion = em.merge(oldCodigoOfSeccionCollectionNewSeccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = catalogoEE.getCodigo();
                if (findCatalogoEE(id) == null) {
                    throw new NonexistentEntityException("The catalogoEE with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CatalogoEE catalogoEE;
            try {
                catalogoEE = em.getReference(CatalogoEE.class, id);
                catalogoEE.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catalogoEE with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ExperienciaEducativa> experienciaEducativaCollectionOrphanCheck = catalogoEE.getExperienciaEducativaCollection();
            for (ExperienciaEducativa experienciaEducativaCollectionOrphanCheckExperienciaEducativa : experienciaEducativaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CatalogoEE (" + catalogoEE + ") cannot be destroyed since the ExperienciaEducativa " + experienciaEducativaCollectionOrphanCheckExperienciaEducativa + " in its experienciaEducativaCollection field has a non-nullable codigo field.");
            }
            Collection<Seccion> seccionCollectionOrphanCheck = catalogoEE.getSeccionCollection();
            for (Seccion seccionCollectionOrphanCheckSeccion : seccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CatalogoEE (" + catalogoEE + ") cannot be destroyed since the Seccion " + seccionCollectionOrphanCheckSeccion + " in its seccionCollection field has a non-nullable codigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(catalogoEE);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CatalogoEE> findCatalogoEEEntities() {
        return findCatalogoEEEntities(true, -1, -1);
    }

    public List<CatalogoEE> findCatalogoEEEntities(int maxResults, int firstResult) {
        return findCatalogoEEEntities(false, maxResults, firstResult);
    }

    private List<CatalogoEE> findCatalogoEEEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CatalogoEE.class));
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

    public CatalogoEE findCatalogoEE(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CatalogoEE.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatalogoEECount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CatalogoEE> rt = cq.from(CatalogoEE.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
