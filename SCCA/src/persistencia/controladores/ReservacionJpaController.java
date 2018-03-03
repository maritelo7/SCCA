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
import persistencia.Seguimiento;
import persistencia.Asesoria;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.Conversacion;
import persistencia.Reservacion;
import persistencia.controladores.exceptions.IllegalOrphanException;
import persistencia.controladores.exceptions.NonexistentEntityException;

/**
 *
 * @author marianacro
 */
public class ReservacionJpaController implements Serializable {

    public ReservacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservacion reservacion) {
        if (reservacion.getAsesoriaCollection() == null) {
            reservacion.setAsesoriaCollection(new ArrayList<Asesoria>());
        }
        if (reservacion.getConversacionCollection() == null) {
            reservacion.setConversacionCollection(new ArrayList<Conversacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seguimiento idSeguimiento = reservacion.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento = em.getReference(idSeguimiento.getClass(), idSeguimiento.getIdSeguimiento());
                reservacion.setIdSeguimiento(idSeguimiento);
            }
            Collection<Asesoria> attachedAsesoriaCollection = new ArrayList<Asesoria>();
            for (Asesoria asesoriaCollectionAsesoriaToAttach : reservacion.getAsesoriaCollection()) {
                asesoriaCollectionAsesoriaToAttach = em.getReference(asesoriaCollectionAsesoriaToAttach.getClass(), asesoriaCollectionAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaCollection.add(asesoriaCollectionAsesoriaToAttach);
            }
            reservacion.setAsesoriaCollection(attachedAsesoriaCollection);
            Collection<Conversacion> attachedConversacionCollection = new ArrayList<Conversacion>();
            for (Conversacion conversacionCollectionConversacionToAttach : reservacion.getConversacionCollection()) {
                conversacionCollectionConversacionToAttach = em.getReference(conversacionCollectionConversacionToAttach.getClass(), conversacionCollectionConversacionToAttach.getIdConversacion());
                attachedConversacionCollection.add(conversacionCollectionConversacionToAttach);
            }
            reservacion.setConversacionCollection(attachedConversacionCollection);
            em.persist(reservacion);
            if (idSeguimiento != null) {
                idSeguimiento.getReservacionCollection().add(reservacion);
                idSeguimiento = em.merge(idSeguimiento);
            }
            for (Asesoria asesoriaCollectionAsesoria : reservacion.getAsesoriaCollection()) {
                Reservacion oldIdReservacionOfAsesoriaCollectionAsesoria = asesoriaCollectionAsesoria.getIdReservacion();
                asesoriaCollectionAsesoria.setIdReservacion(reservacion);
                asesoriaCollectionAsesoria = em.merge(asesoriaCollectionAsesoria);
                if (oldIdReservacionOfAsesoriaCollectionAsesoria != null) {
                    oldIdReservacionOfAsesoriaCollectionAsesoria.getAsesoriaCollection().remove(asesoriaCollectionAsesoria);
                    oldIdReservacionOfAsesoriaCollectionAsesoria = em.merge(oldIdReservacionOfAsesoriaCollectionAsesoria);
                }
            }
            for (Conversacion conversacionCollectionConversacion : reservacion.getConversacionCollection()) {
                Reservacion oldIdReservacionOfConversacionCollectionConversacion = conversacionCollectionConversacion.getIdReservacion();
                conversacionCollectionConversacion.setIdReservacion(reservacion);
                conversacionCollectionConversacion = em.merge(conversacionCollectionConversacion);
                if (oldIdReservacionOfConversacionCollectionConversacion != null) {
                    oldIdReservacionOfConversacionCollectionConversacion.getConversacionCollection().remove(conversacionCollectionConversacion);
                    oldIdReservacionOfConversacionCollectionConversacion = em.merge(oldIdReservacionOfConversacionCollectionConversacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservacion reservacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservacion persistentReservacion = em.find(Reservacion.class, reservacion.getIdReservacion());
            Seguimiento idSeguimientoOld = persistentReservacion.getIdSeguimiento();
            Seguimiento idSeguimientoNew = reservacion.getIdSeguimiento();
            Collection<Asesoria> asesoriaCollectionOld = persistentReservacion.getAsesoriaCollection();
            Collection<Asesoria> asesoriaCollectionNew = reservacion.getAsesoriaCollection();
            Collection<Conversacion> conversacionCollectionOld = persistentReservacion.getConversacionCollection();
            Collection<Conversacion> conversacionCollectionNew = reservacion.getConversacionCollection();
            List<String> illegalOrphanMessages = null;
            for (Asesoria asesoriaCollectionOldAsesoria : asesoriaCollectionOld) {
                if (!asesoriaCollectionNew.contains(asesoriaCollectionOldAsesoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asesoria " + asesoriaCollectionOldAsesoria + " since its idReservacion field is not nullable.");
                }
            }
            for (Conversacion conversacionCollectionOldConversacion : conversacionCollectionOld) {
                if (!conversacionCollectionNew.contains(conversacionCollectionOldConversacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Conversacion " + conversacionCollectionOldConversacion + " since its idReservacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSeguimientoNew != null) {
                idSeguimientoNew = em.getReference(idSeguimientoNew.getClass(), idSeguimientoNew.getIdSeguimiento());
                reservacion.setIdSeguimiento(idSeguimientoNew);
            }
            Collection<Asesoria> attachedAsesoriaCollectionNew = new ArrayList<Asesoria>();
            for (Asesoria asesoriaCollectionNewAsesoriaToAttach : asesoriaCollectionNew) {
                asesoriaCollectionNewAsesoriaToAttach = em.getReference(asesoriaCollectionNewAsesoriaToAttach.getClass(), asesoriaCollectionNewAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaCollectionNew.add(asesoriaCollectionNewAsesoriaToAttach);
            }
            asesoriaCollectionNew = attachedAsesoriaCollectionNew;
            reservacion.setAsesoriaCollection(asesoriaCollectionNew);
            Collection<Conversacion> attachedConversacionCollectionNew = new ArrayList<Conversacion>();
            for (Conversacion conversacionCollectionNewConversacionToAttach : conversacionCollectionNew) {
                conversacionCollectionNewConversacionToAttach = em.getReference(conversacionCollectionNewConversacionToAttach.getClass(), conversacionCollectionNewConversacionToAttach.getIdConversacion());
                attachedConversacionCollectionNew.add(conversacionCollectionNewConversacionToAttach);
            }
            conversacionCollectionNew = attachedConversacionCollectionNew;
            reservacion.setConversacionCollection(conversacionCollectionNew);
            reservacion = em.merge(reservacion);
            if (idSeguimientoOld != null && !idSeguimientoOld.equals(idSeguimientoNew)) {
                idSeguimientoOld.getReservacionCollection().remove(reservacion);
                idSeguimientoOld = em.merge(idSeguimientoOld);
            }
            if (idSeguimientoNew != null && !idSeguimientoNew.equals(idSeguimientoOld)) {
                idSeguimientoNew.getReservacionCollection().add(reservacion);
                idSeguimientoNew = em.merge(idSeguimientoNew);
            }
            for (Asesoria asesoriaCollectionNewAsesoria : asesoriaCollectionNew) {
                if (!asesoriaCollectionOld.contains(asesoriaCollectionNewAsesoria)) {
                    Reservacion oldIdReservacionOfAsesoriaCollectionNewAsesoria = asesoriaCollectionNewAsesoria.getIdReservacion();
                    asesoriaCollectionNewAsesoria.setIdReservacion(reservacion);
                    asesoriaCollectionNewAsesoria = em.merge(asesoriaCollectionNewAsesoria);
                    if (oldIdReservacionOfAsesoriaCollectionNewAsesoria != null && !oldIdReservacionOfAsesoriaCollectionNewAsesoria.equals(reservacion)) {
                        oldIdReservacionOfAsesoriaCollectionNewAsesoria.getAsesoriaCollection().remove(asesoriaCollectionNewAsesoria);
                        oldIdReservacionOfAsesoriaCollectionNewAsesoria = em.merge(oldIdReservacionOfAsesoriaCollectionNewAsesoria);
                    }
                }
            }
            for (Conversacion conversacionCollectionNewConversacion : conversacionCollectionNew) {
                if (!conversacionCollectionOld.contains(conversacionCollectionNewConversacion)) {
                    Reservacion oldIdReservacionOfConversacionCollectionNewConversacion = conversacionCollectionNewConversacion.getIdReservacion();
                    conversacionCollectionNewConversacion.setIdReservacion(reservacion);
                    conversacionCollectionNewConversacion = em.merge(conversacionCollectionNewConversacion);
                    if (oldIdReservacionOfConversacionCollectionNewConversacion != null && !oldIdReservacionOfConversacionCollectionNewConversacion.equals(reservacion)) {
                        oldIdReservacionOfConversacionCollectionNewConversacion.getConversacionCollection().remove(conversacionCollectionNewConversacion);
                        oldIdReservacionOfConversacionCollectionNewConversacion = em.merge(oldIdReservacionOfConversacionCollectionNewConversacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservacion.getIdReservacion();
                if (findReservacion(id) == null) {
                    throw new NonexistentEntityException("The reservacion with id " + id + " no longer exists.");
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
            Reservacion reservacion;
            try {
                reservacion = em.getReference(Reservacion.class, id);
                reservacion.getIdReservacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Asesoria> asesoriaCollectionOrphanCheck = reservacion.getAsesoriaCollection();
            for (Asesoria asesoriaCollectionOrphanCheckAsesoria : asesoriaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reservacion (" + reservacion + ") cannot be destroyed since the Asesoria " + asesoriaCollectionOrphanCheckAsesoria + " in its asesoriaCollection field has a non-nullable idReservacion field.");
            }
            Collection<Conversacion> conversacionCollectionOrphanCheck = reservacion.getConversacionCollection();
            for (Conversacion conversacionCollectionOrphanCheckConversacion : conversacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reservacion (" + reservacion + ") cannot be destroyed since the Conversacion " + conversacionCollectionOrphanCheckConversacion + " in its conversacionCollection field has a non-nullable idReservacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Seguimiento idSeguimiento = reservacion.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento.getReservacionCollection().remove(reservacion);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.remove(reservacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reservacion> findReservacionEntities() {
        return findReservacionEntities(true, -1, -1);
    }

    public List<Reservacion> findReservacionEntities(int maxResults, int firstResult) {
        return findReservacionEntities(false, maxResults, firstResult);
    }

    private List<Reservacion> findReservacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservacion.class));
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

    public Reservacion findReservacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservacion> rt = cq.from(Reservacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
