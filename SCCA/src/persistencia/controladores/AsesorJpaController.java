/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.Asesoria;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.Asesor;
import persistencia.ExperienciaEducativa;
import persistencia.controladores.exceptions.IllegalOrphanException;
import persistencia.controladores.exceptions.NonexistentEntityException;
import persistencia.controladores.exceptions.PreexistingEntityException;

/**
 *
 * @author marianacro
 */
public class AsesorJpaController implements Serializable {

    public AsesorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asesor asesor) throws PreexistingEntityException, Exception {
        if (asesor.getAsesoriaCollection() == null) {
            asesor.setAsesoriaCollection(new ArrayList<Asesoria>());
        }
        if (asesor.getExperienciaEducativaCollection() == null) {
            asesor.setExperienciaEducativaCollection(new ArrayList<ExperienciaEducativa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Asesoria> attachedAsesoriaCollection = new ArrayList<Asesoria>();
            for (Asesoria asesoriaCollectionAsesoriaToAttach : asesor.getAsesoriaCollection()) {
                asesoriaCollectionAsesoriaToAttach = em.getReference(asesoriaCollectionAsesoriaToAttach.getClass(), asesoriaCollectionAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaCollection.add(asesoriaCollectionAsesoriaToAttach);
            }
            asesor.setAsesoriaCollection(attachedAsesoriaCollection);
            Collection<ExperienciaEducativa> attachedExperienciaEducativaCollection = new ArrayList<ExperienciaEducativa>();
            for (ExperienciaEducativa experienciaEducativaCollectionExperienciaEducativaToAttach : asesor.getExperienciaEducativaCollection()) {
                experienciaEducativaCollectionExperienciaEducativaToAttach = em.getReference(experienciaEducativaCollectionExperienciaEducativaToAttach.getClass(), experienciaEducativaCollectionExperienciaEducativaToAttach.getNrc());
                attachedExperienciaEducativaCollection.add(experienciaEducativaCollectionExperienciaEducativaToAttach);
            }
            asesor.setExperienciaEducativaCollection(attachedExperienciaEducativaCollection);
            em.persist(asesor);
            for (Asesoria asesoriaCollectionAsesoria : asesor.getAsesoriaCollection()) {
                Asesor oldNumPersonalOfAsesoriaCollectionAsesoria = asesoriaCollectionAsesoria.getNumPersonal();
                asesoriaCollectionAsesoria.setNumPersonal(asesor);
                asesoriaCollectionAsesoria = em.merge(asesoriaCollectionAsesoria);
                if (oldNumPersonalOfAsesoriaCollectionAsesoria != null) {
                    oldNumPersonalOfAsesoriaCollectionAsesoria.getAsesoriaCollection().remove(asesoriaCollectionAsesoria);
                    oldNumPersonalOfAsesoriaCollectionAsesoria = em.merge(oldNumPersonalOfAsesoriaCollectionAsesoria);
                }
            }
            for (ExperienciaEducativa experienciaEducativaCollectionExperienciaEducativa : asesor.getExperienciaEducativaCollection()) {
                Asesor oldNumPersonalOfExperienciaEducativaCollectionExperienciaEducativa = experienciaEducativaCollectionExperienciaEducativa.getNumPersonal();
                experienciaEducativaCollectionExperienciaEducativa.setNumPersonal(asesor);
                experienciaEducativaCollectionExperienciaEducativa = em.merge(experienciaEducativaCollectionExperienciaEducativa);
                if (oldNumPersonalOfExperienciaEducativaCollectionExperienciaEducativa != null) {
                    oldNumPersonalOfExperienciaEducativaCollectionExperienciaEducativa.getExperienciaEducativaCollection().remove(experienciaEducativaCollectionExperienciaEducativa);
                    oldNumPersonalOfExperienciaEducativaCollectionExperienciaEducativa = em.merge(oldNumPersonalOfExperienciaEducativaCollectionExperienciaEducativa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsesor(asesor.getNumPersonal()) != null) {
                throw new PreexistingEntityException("Asesor " + asesor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asesor asesor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asesor persistentAsesor = em.find(Asesor.class, asesor.getNumPersonal());
            Collection<Asesoria> asesoriaCollectionOld = persistentAsesor.getAsesoriaCollection();
            Collection<Asesoria> asesoriaCollectionNew = asesor.getAsesoriaCollection();
            Collection<ExperienciaEducativa> experienciaEducativaCollectionOld = persistentAsesor.getExperienciaEducativaCollection();
            Collection<ExperienciaEducativa> experienciaEducativaCollectionNew = asesor.getExperienciaEducativaCollection();
            List<String> illegalOrphanMessages = null;
            for (Asesoria asesoriaCollectionOldAsesoria : asesoriaCollectionOld) {
                if (!asesoriaCollectionNew.contains(asesoriaCollectionOldAsesoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asesoria " + asesoriaCollectionOldAsesoria + " since its numPersonal field is not nullable.");
                }
            }
            for (ExperienciaEducativa experienciaEducativaCollectionOldExperienciaEducativa : experienciaEducativaCollectionOld) {
                if (!experienciaEducativaCollectionNew.contains(experienciaEducativaCollectionOldExperienciaEducativa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ExperienciaEducativa " + experienciaEducativaCollectionOldExperienciaEducativa + " since its numPersonal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Asesoria> attachedAsesoriaCollectionNew = new ArrayList<Asesoria>();
            for (Asesoria asesoriaCollectionNewAsesoriaToAttach : asesoriaCollectionNew) {
                asesoriaCollectionNewAsesoriaToAttach = em.getReference(asesoriaCollectionNewAsesoriaToAttach.getClass(), asesoriaCollectionNewAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaCollectionNew.add(asesoriaCollectionNewAsesoriaToAttach);
            }
            asesoriaCollectionNew = attachedAsesoriaCollectionNew;
            asesor.setAsesoriaCollection(asesoriaCollectionNew);
            Collection<ExperienciaEducativa> attachedExperienciaEducativaCollectionNew = new ArrayList<ExperienciaEducativa>();
            for (ExperienciaEducativa experienciaEducativaCollectionNewExperienciaEducativaToAttach : experienciaEducativaCollectionNew) {
                experienciaEducativaCollectionNewExperienciaEducativaToAttach = em.getReference(experienciaEducativaCollectionNewExperienciaEducativaToAttach.getClass(), experienciaEducativaCollectionNewExperienciaEducativaToAttach.getNrc());
                attachedExperienciaEducativaCollectionNew.add(experienciaEducativaCollectionNewExperienciaEducativaToAttach);
            }
            experienciaEducativaCollectionNew = attachedExperienciaEducativaCollectionNew;
            asesor.setExperienciaEducativaCollection(experienciaEducativaCollectionNew);
            asesor = em.merge(asesor);
            for (Asesoria asesoriaCollectionNewAsesoria : asesoriaCollectionNew) {
                if (!asesoriaCollectionOld.contains(asesoriaCollectionNewAsesoria)) {
                    Asesor oldNumPersonalOfAsesoriaCollectionNewAsesoria = asesoriaCollectionNewAsesoria.getNumPersonal();
                    asesoriaCollectionNewAsesoria.setNumPersonal(asesor);
                    asesoriaCollectionNewAsesoria = em.merge(asesoriaCollectionNewAsesoria);
                    if (oldNumPersonalOfAsesoriaCollectionNewAsesoria != null && !oldNumPersonalOfAsesoriaCollectionNewAsesoria.equals(asesor)) {
                        oldNumPersonalOfAsesoriaCollectionNewAsesoria.getAsesoriaCollection().remove(asesoriaCollectionNewAsesoria);
                        oldNumPersonalOfAsesoriaCollectionNewAsesoria = em.merge(oldNumPersonalOfAsesoriaCollectionNewAsesoria);
                    }
                }
            }
            for (ExperienciaEducativa experienciaEducativaCollectionNewExperienciaEducativa : experienciaEducativaCollectionNew) {
                if (!experienciaEducativaCollectionOld.contains(experienciaEducativaCollectionNewExperienciaEducativa)) {
                    Asesor oldNumPersonalOfExperienciaEducativaCollectionNewExperienciaEducativa = experienciaEducativaCollectionNewExperienciaEducativa.getNumPersonal();
                    experienciaEducativaCollectionNewExperienciaEducativa.setNumPersonal(asesor);
                    experienciaEducativaCollectionNewExperienciaEducativa = em.merge(experienciaEducativaCollectionNewExperienciaEducativa);
                    if (oldNumPersonalOfExperienciaEducativaCollectionNewExperienciaEducativa != null && !oldNumPersonalOfExperienciaEducativaCollectionNewExperienciaEducativa.equals(asesor)) {
                        oldNumPersonalOfExperienciaEducativaCollectionNewExperienciaEducativa.getExperienciaEducativaCollection().remove(experienciaEducativaCollectionNewExperienciaEducativa);
                        oldNumPersonalOfExperienciaEducativaCollectionNewExperienciaEducativa = em.merge(oldNumPersonalOfExperienciaEducativaCollectionNewExperienciaEducativa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = asesor.getNumPersonal();
                if (findAsesor(id) == null) {
                    throw new NonexistentEntityException("The asesor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asesor asesor;
            try {
                asesor = em.getReference(Asesor.class, id);
                asesor.getNumPersonal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asesor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Asesoria> asesoriaCollectionOrphanCheck = asesor.getAsesoriaCollection();
            for (Asesoria asesoriaCollectionOrphanCheckAsesoria : asesoriaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asesor (" + asesor + ") cannot be destroyed since the Asesoria " + asesoriaCollectionOrphanCheckAsesoria + " in its asesoriaCollection field has a non-nullable numPersonal field.");
            }
            Collection<ExperienciaEducativa> experienciaEducativaCollectionOrphanCheck = asesor.getExperienciaEducativaCollection();
            for (ExperienciaEducativa experienciaEducativaCollectionOrphanCheckExperienciaEducativa : experienciaEducativaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asesor (" + asesor + ") cannot be destroyed since the ExperienciaEducativa " + experienciaEducativaCollectionOrphanCheckExperienciaEducativa + " in its experienciaEducativaCollection field has a non-nullable numPersonal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(asesor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asesor> findAsesorEntities() {
        return findAsesorEntities(true, -1, -1);
    }

    public List<Asesor> findAsesorEntities(int maxResults, int firstResult) {
        return findAsesorEntities(false, maxResults, firstResult);
    }

    private List<Asesor> findAsesorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asesor.class));
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

    public Asesor findAsesor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asesor.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsesorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asesor> rt = cq.from(Asesor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
