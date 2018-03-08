/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.controladores;

import Persistencia.Asesor;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Persistencia.ExperienciaEducativa;
import java.util.ArrayList;
import java.util.List;
import Persistencia.Asesoria;
import Persistencia.controladores.exceptions.IllegalOrphanException;
import Persistencia.controladores.exceptions.NonexistentEntityException;
import Persistencia.controladores.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (asesor.getExperienciaEducativaList() == null) {
            asesor.setExperienciaEducativaList(new ArrayList<ExperienciaEducativa>());
        }
        if (asesor.getAsesoriaList() == null) {
            asesor.setAsesoriaList(new ArrayList<Asesoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ExperienciaEducativa> attachedExperienciaEducativaList = new ArrayList<ExperienciaEducativa>();
            for (ExperienciaEducativa experienciaEducativaListExperienciaEducativaToAttach : asesor.getExperienciaEducativaList()) {
                experienciaEducativaListExperienciaEducativaToAttach = em.getReference(experienciaEducativaListExperienciaEducativaToAttach.getClass(), experienciaEducativaListExperienciaEducativaToAttach.getNrc());
                attachedExperienciaEducativaList.add(experienciaEducativaListExperienciaEducativaToAttach);
            }
            asesor.setExperienciaEducativaList(attachedExperienciaEducativaList);
            List<Asesoria> attachedAsesoriaList = new ArrayList<Asesoria>();
            for (Asesoria asesoriaListAsesoriaToAttach : asesor.getAsesoriaList()) {
                asesoriaListAsesoriaToAttach = em.getReference(asesoriaListAsesoriaToAttach.getClass(), asesoriaListAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaList.add(asesoriaListAsesoriaToAttach);
            }
            asesor.setAsesoriaList(attachedAsesoriaList);
            em.persist(asesor);
            for (ExperienciaEducativa experienciaEducativaListExperienciaEducativa : asesor.getExperienciaEducativaList()) {
                Asesor oldNumPersonalOfExperienciaEducativaListExperienciaEducativa = experienciaEducativaListExperienciaEducativa.getNumPersonal();
                experienciaEducativaListExperienciaEducativa.setNumPersonal(asesor);
                experienciaEducativaListExperienciaEducativa = em.merge(experienciaEducativaListExperienciaEducativa);
                if (oldNumPersonalOfExperienciaEducativaListExperienciaEducativa != null) {
                    oldNumPersonalOfExperienciaEducativaListExperienciaEducativa.getExperienciaEducativaList().remove(experienciaEducativaListExperienciaEducativa);
                    oldNumPersonalOfExperienciaEducativaListExperienciaEducativa = em.merge(oldNumPersonalOfExperienciaEducativaListExperienciaEducativa);
                }
            }
            for (Asesoria asesoriaListAsesoria : asesor.getAsesoriaList()) {
                Asesor oldNumPersonalOfAsesoriaListAsesoria = asesoriaListAsesoria.getNumPersonal();
                asesoriaListAsesoria.setNumPersonal(asesor);
                asesoriaListAsesoria = em.merge(asesoriaListAsesoria);
                if (oldNumPersonalOfAsesoriaListAsesoria != null) {
                    oldNumPersonalOfAsesoriaListAsesoria.getAsesoriaList().remove(asesoriaListAsesoria);
                    oldNumPersonalOfAsesoriaListAsesoria = em.merge(oldNumPersonalOfAsesoriaListAsesoria);
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
            List<ExperienciaEducativa> experienciaEducativaListOld = persistentAsesor.getExperienciaEducativaList();
            List<ExperienciaEducativa> experienciaEducativaListNew = asesor.getExperienciaEducativaList();
            List<Asesoria> asesoriaListOld = persistentAsesor.getAsesoriaList();
            List<Asesoria> asesoriaListNew = asesor.getAsesoriaList();
            List<String> illegalOrphanMessages = null;
            for (ExperienciaEducativa experienciaEducativaListOldExperienciaEducativa : experienciaEducativaListOld) {
                if (!experienciaEducativaListNew.contains(experienciaEducativaListOldExperienciaEducativa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ExperienciaEducativa " + experienciaEducativaListOldExperienciaEducativa + " since its numPersonal field is not nullable.");
                }
            }
            for (Asesoria asesoriaListOldAsesoria : asesoriaListOld) {
                if (!asesoriaListNew.contains(asesoriaListOldAsesoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asesoria " + asesoriaListOldAsesoria + " since its numPersonal field is not nullable.");
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
            asesor.setExperienciaEducativaList(experienciaEducativaListNew);
            List<Asesoria> attachedAsesoriaListNew = new ArrayList<Asesoria>();
            for (Asesoria asesoriaListNewAsesoriaToAttach : asesoriaListNew) {
                asesoriaListNewAsesoriaToAttach = em.getReference(asesoriaListNewAsesoriaToAttach.getClass(), asesoriaListNewAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaListNew.add(asesoriaListNewAsesoriaToAttach);
            }
            asesoriaListNew = attachedAsesoriaListNew;
            asesor.setAsesoriaList(asesoriaListNew);
            asesor = em.merge(asesor);
            for (ExperienciaEducativa experienciaEducativaListNewExperienciaEducativa : experienciaEducativaListNew) {
                if (!experienciaEducativaListOld.contains(experienciaEducativaListNewExperienciaEducativa)) {
                    Asesor oldNumPersonalOfExperienciaEducativaListNewExperienciaEducativa = experienciaEducativaListNewExperienciaEducativa.getNumPersonal();
                    experienciaEducativaListNewExperienciaEducativa.setNumPersonal(asesor);
                    experienciaEducativaListNewExperienciaEducativa = em.merge(experienciaEducativaListNewExperienciaEducativa);
                    if (oldNumPersonalOfExperienciaEducativaListNewExperienciaEducativa != null && !oldNumPersonalOfExperienciaEducativaListNewExperienciaEducativa.equals(asesor)) {
                        oldNumPersonalOfExperienciaEducativaListNewExperienciaEducativa.getExperienciaEducativaList().remove(experienciaEducativaListNewExperienciaEducativa);
                        oldNumPersonalOfExperienciaEducativaListNewExperienciaEducativa = em.merge(oldNumPersonalOfExperienciaEducativaListNewExperienciaEducativa);
                    }
                }
            }
            for (Asesoria asesoriaListNewAsesoria : asesoriaListNew) {
                if (!asesoriaListOld.contains(asesoriaListNewAsesoria)) {
                    Asesor oldNumPersonalOfAsesoriaListNewAsesoria = asesoriaListNewAsesoria.getNumPersonal();
                    asesoriaListNewAsesoria.setNumPersonal(asesor);
                    asesoriaListNewAsesoria = em.merge(asesoriaListNewAsesoria);
                    if (oldNumPersonalOfAsesoriaListNewAsesoria != null && !oldNumPersonalOfAsesoriaListNewAsesoria.equals(asesor)) {
                        oldNumPersonalOfAsesoriaListNewAsesoria.getAsesoriaList().remove(asesoriaListNewAsesoria);
                        oldNumPersonalOfAsesoriaListNewAsesoria = em.merge(oldNumPersonalOfAsesoriaListNewAsesoria);
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
            List<ExperienciaEducativa> experienciaEducativaListOrphanCheck = asesor.getExperienciaEducativaList();
            for (ExperienciaEducativa experienciaEducativaListOrphanCheckExperienciaEducativa : experienciaEducativaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asesor (" + asesor + ") cannot be destroyed since the ExperienciaEducativa " + experienciaEducativaListOrphanCheckExperienciaEducativa + " in its experienciaEducativaList field has a non-nullable numPersonal field.");
            }
            List<Asesoria> asesoriaListOrphanCheck = asesor.getAsesoriaList();
            for (Asesoria asesoriaListOrphanCheckAsesoria : asesoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Asesor (" + asesor + ") cannot be destroyed since the Asesoria " + asesoriaListOrphanCheckAsesoria + " in its asesoriaList field has a non-nullable numPersonal field.");
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
