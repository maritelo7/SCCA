/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.controladores;

import Persistencia.CatalogoEE;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Persistencia.ExperienciaEducativa;
import java.util.ArrayList;
import java.util.List;
import Persistencia.Seccion;
import Persistencia.controladores.exceptions.IllegalOrphanException;
import Persistencia.controladores.exceptions.NonexistentEntityException;
import Persistencia.controladores.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (catalogoEE.getExperienciaEducativaList() == null) {
            catalogoEE.setExperienciaEducativaList(new ArrayList<ExperienciaEducativa>());
        }
        if (catalogoEE.getSeccionList() == null) {
            catalogoEE.setSeccionList(new ArrayList<Seccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ExperienciaEducativa> attachedExperienciaEducativaList = new ArrayList<ExperienciaEducativa>();
            for (ExperienciaEducativa experienciaEducativaListExperienciaEducativaToAttach : catalogoEE.getExperienciaEducativaList()) {
                experienciaEducativaListExperienciaEducativaToAttach = em.getReference(experienciaEducativaListExperienciaEducativaToAttach.getClass(), experienciaEducativaListExperienciaEducativaToAttach.getNrc());
                attachedExperienciaEducativaList.add(experienciaEducativaListExperienciaEducativaToAttach);
            }
            catalogoEE.setExperienciaEducativaList(attachedExperienciaEducativaList);
            List<Seccion> attachedSeccionList = new ArrayList<Seccion>();
            for (Seccion seccionListSeccionToAttach : catalogoEE.getSeccionList()) {
                seccionListSeccionToAttach = em.getReference(seccionListSeccionToAttach.getClass(), seccionListSeccionToAttach.getNumSeccion());
                attachedSeccionList.add(seccionListSeccionToAttach);
            }
            catalogoEE.setSeccionList(attachedSeccionList);
            em.persist(catalogoEE);
            for (ExperienciaEducativa experienciaEducativaListExperienciaEducativa : catalogoEE.getExperienciaEducativaList()) {
                CatalogoEE oldCodigoOfExperienciaEducativaListExperienciaEducativa = experienciaEducativaListExperienciaEducativa.getCodigo();
                experienciaEducativaListExperienciaEducativa.setCodigo(catalogoEE);
                experienciaEducativaListExperienciaEducativa = em.merge(experienciaEducativaListExperienciaEducativa);
                if (oldCodigoOfExperienciaEducativaListExperienciaEducativa != null) {
                    oldCodigoOfExperienciaEducativaListExperienciaEducativa.getExperienciaEducativaList().remove(experienciaEducativaListExperienciaEducativa);
                    oldCodigoOfExperienciaEducativaListExperienciaEducativa = em.merge(oldCodigoOfExperienciaEducativaListExperienciaEducativa);
                }
            }
            for (Seccion seccionListSeccion : catalogoEE.getSeccionList()) {
                CatalogoEE oldCodigoOfSeccionListSeccion = seccionListSeccion.getCodigo();
                seccionListSeccion.setCodigo(catalogoEE);
                seccionListSeccion = em.merge(seccionListSeccion);
                if (oldCodigoOfSeccionListSeccion != null) {
                    oldCodigoOfSeccionListSeccion.getSeccionList().remove(seccionListSeccion);
                    oldCodigoOfSeccionListSeccion = em.merge(oldCodigoOfSeccionListSeccion);
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
            List<ExperienciaEducativa> experienciaEducativaListOld = persistentCatalogoEE.getExperienciaEducativaList();
            List<ExperienciaEducativa> experienciaEducativaListNew = catalogoEE.getExperienciaEducativaList();
            List<Seccion> seccionListOld = persistentCatalogoEE.getSeccionList();
            List<Seccion> seccionListNew = catalogoEE.getSeccionList();
            List<String> illegalOrphanMessages = null;
            for (ExperienciaEducativa experienciaEducativaListOldExperienciaEducativa : experienciaEducativaListOld) {
                if (!experienciaEducativaListNew.contains(experienciaEducativaListOldExperienciaEducativa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ExperienciaEducativa " + experienciaEducativaListOldExperienciaEducativa + " since its codigo field is not nullable.");
                }
            }
            for (Seccion seccionListOldSeccion : seccionListOld) {
                if (!seccionListNew.contains(seccionListOldSeccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Seccion " + seccionListOldSeccion + " since its codigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ExperienciaEducativa> attachedExperienciaEducativaListNew = new ArrayList<ExperienciaEducativa>();
            for (ExperienciaEducativa experienciaEducativaListNewExperienciaEducativaToAttach : experienciaEducativaListNew) {
                experienciaEducativaListNewExperienciaEducativaToAttach = em.getReference(experienciaEducativaListNewExperienciaEducativaToAttach.getClass(), experienciaEducativaListNewExperienciaEducativaToAttach.getNrc());
                attachedExperienciaEducativaListNew.add(experienciaEducativaListNewExperienciaEducativaToAttach);
            }
            experienciaEducativaListNew = attachedExperienciaEducativaListNew;
            catalogoEE.setExperienciaEducativaList(experienciaEducativaListNew);
            List<Seccion> attachedSeccionListNew = new ArrayList<Seccion>();
            for (Seccion seccionListNewSeccionToAttach : seccionListNew) {
                seccionListNewSeccionToAttach = em.getReference(seccionListNewSeccionToAttach.getClass(), seccionListNewSeccionToAttach.getNumSeccion());
                attachedSeccionListNew.add(seccionListNewSeccionToAttach);
            }
            seccionListNew = attachedSeccionListNew;
            catalogoEE.setSeccionList(seccionListNew);
            catalogoEE = em.merge(catalogoEE);
            for (ExperienciaEducativa experienciaEducativaListNewExperienciaEducativa : experienciaEducativaListNew) {
                if (!experienciaEducativaListOld.contains(experienciaEducativaListNewExperienciaEducativa)) {
                    CatalogoEE oldCodigoOfExperienciaEducativaListNewExperienciaEducativa = experienciaEducativaListNewExperienciaEducativa.getCodigo();
                    experienciaEducativaListNewExperienciaEducativa.setCodigo(catalogoEE);
                    experienciaEducativaListNewExperienciaEducativa = em.merge(experienciaEducativaListNewExperienciaEducativa);
                    if (oldCodigoOfExperienciaEducativaListNewExperienciaEducativa != null && !oldCodigoOfExperienciaEducativaListNewExperienciaEducativa.equals(catalogoEE)) {
                        oldCodigoOfExperienciaEducativaListNewExperienciaEducativa.getExperienciaEducativaList().remove(experienciaEducativaListNewExperienciaEducativa);
                        oldCodigoOfExperienciaEducativaListNewExperienciaEducativa = em.merge(oldCodigoOfExperienciaEducativaListNewExperienciaEducativa);
                    }
                }
            }
            for (Seccion seccionListNewSeccion : seccionListNew) {
                if (!seccionListOld.contains(seccionListNewSeccion)) {
                    CatalogoEE oldCodigoOfSeccionListNewSeccion = seccionListNewSeccion.getCodigo();
                    seccionListNewSeccion.setCodigo(catalogoEE);
                    seccionListNewSeccion = em.merge(seccionListNewSeccion);
                    if (oldCodigoOfSeccionListNewSeccion != null && !oldCodigoOfSeccionListNewSeccion.equals(catalogoEE)) {
                        oldCodigoOfSeccionListNewSeccion.getSeccionList().remove(seccionListNewSeccion);
                        oldCodigoOfSeccionListNewSeccion = em.merge(oldCodigoOfSeccionListNewSeccion);
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
            List<ExperienciaEducativa> experienciaEducativaListOrphanCheck = catalogoEE.getExperienciaEducativaList();
            for (ExperienciaEducativa experienciaEducativaListOrphanCheckExperienciaEducativa : experienciaEducativaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CatalogoEE (" + catalogoEE + ") cannot be destroyed since the ExperienciaEducativa " + experienciaEducativaListOrphanCheckExperienciaEducativa + " in its experienciaEducativaList field has a non-nullable codigo field.");
            }
            List<Seccion> seccionListOrphanCheck = catalogoEE.getSeccionList();
            for (Seccion seccionListOrphanCheckSeccion : seccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CatalogoEE (" + catalogoEE + ") cannot be destroyed since the Seccion " + seccionListOrphanCheckSeccion + " in its seccionList field has a non-nullable codigo field.");
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
