/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.Controlladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Persistencia.Seguimiento;
import Persistencia.Conversacion;
import java.util.ArrayList;
import java.util.List;
import Persistencia.Asesoria;
import Persistencia.Controlladores.exceptions.IllegalOrphanException;
import Persistencia.Controlladores.exceptions.NonexistentEntityException;
import Persistencia.Reservacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (reservacion.getConversacionList() == null) {
            reservacion.setConversacionList(new ArrayList<Conversacion>());
        }
        if (reservacion.getAsesoriaList() == null) {
            reservacion.setAsesoriaList(new ArrayList<Asesoria>());
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
            List<Conversacion> attachedConversacionList = new ArrayList<Conversacion>();
            for (Conversacion conversacionListConversacionToAttach : reservacion.getConversacionList()) {
                conversacionListConversacionToAttach = em.getReference(conversacionListConversacionToAttach.getClass(), conversacionListConversacionToAttach.getIdConversacion());
                attachedConversacionList.add(conversacionListConversacionToAttach);
            }
            reservacion.setConversacionList(attachedConversacionList);
            List<Asesoria> attachedAsesoriaList = new ArrayList<Asesoria>();
            for (Asesoria asesoriaListAsesoriaToAttach : reservacion.getAsesoriaList()) {
                asesoriaListAsesoriaToAttach = em.getReference(asesoriaListAsesoriaToAttach.getClass(), asesoriaListAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaList.add(asesoriaListAsesoriaToAttach);
            }
            reservacion.setAsesoriaList(attachedAsesoriaList);
            em.persist(reservacion);
            if (idSeguimiento != null) {
                idSeguimiento.getReservacionList().add(reservacion);
                idSeguimiento = em.merge(idSeguimiento);
            }
            for (Conversacion conversacionListConversacion : reservacion.getConversacionList()) {
                Reservacion oldIdReservacionOfConversacionListConversacion = conversacionListConversacion.getIdReservacion();
                conversacionListConversacion.setIdReservacion(reservacion);
                conversacionListConversacion = em.merge(conversacionListConversacion);
                if (oldIdReservacionOfConversacionListConversacion != null) {
                    oldIdReservacionOfConversacionListConversacion.getConversacionList().remove(conversacionListConversacion);
                    oldIdReservacionOfConversacionListConversacion = em.merge(oldIdReservacionOfConversacionListConversacion);
                }
            }
            for (Asesoria asesoriaListAsesoria : reservacion.getAsesoriaList()) {
                Reservacion oldIdReservacionOfAsesoriaListAsesoria = asesoriaListAsesoria.getIdReservacion();
                asesoriaListAsesoria.setIdReservacion(reservacion);
                asesoriaListAsesoria = em.merge(asesoriaListAsesoria);
                if (oldIdReservacionOfAsesoriaListAsesoria != null) {
                    oldIdReservacionOfAsesoriaListAsesoria.getAsesoriaList().remove(asesoriaListAsesoria);
                    oldIdReservacionOfAsesoriaListAsesoria = em.merge(oldIdReservacionOfAsesoriaListAsesoria);
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
            List<Conversacion> conversacionListOld = persistentReservacion.getConversacionList();
            List<Conversacion> conversacionListNew = reservacion.getConversacionList();
            List<Asesoria> asesoriaListOld = persistentReservacion.getAsesoriaList();
            List<Asesoria> asesoriaListNew = reservacion.getAsesoriaList();
            List<String> illegalOrphanMessages = null;
            for (Conversacion conversacionListOldConversacion : conversacionListOld) {
                if (!conversacionListNew.contains(conversacionListOldConversacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Conversacion " + conversacionListOldConversacion + " since its idReservacion field is not nullable.");
                }
            }
            for (Asesoria asesoriaListOldAsesoria : asesoriaListOld) {
                if (!asesoriaListNew.contains(asesoriaListOldAsesoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asesoria " + asesoriaListOldAsesoria + " since its idReservacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSeguimientoNew != null) {
                idSeguimientoNew = em.getReference(idSeguimientoNew.getClass(), idSeguimientoNew.getIdSeguimiento());
                reservacion.setIdSeguimiento(idSeguimientoNew);
            }
            List<Conversacion> attachedConversacionListNew = new ArrayList<Conversacion>();
            for (Conversacion conversacionListNewConversacionToAttach : conversacionListNew) {
                conversacionListNewConversacionToAttach = em.getReference(conversacionListNewConversacionToAttach.getClass(), conversacionListNewConversacionToAttach.getIdConversacion());
                attachedConversacionListNew.add(conversacionListNewConversacionToAttach);
            }
            conversacionListNew = attachedConversacionListNew;
            reservacion.setConversacionList(conversacionListNew);
            List<Asesoria> attachedAsesoriaListNew = new ArrayList<Asesoria>();
            for (Asesoria asesoriaListNewAsesoriaToAttach : asesoriaListNew) {
                asesoriaListNewAsesoriaToAttach = em.getReference(asesoriaListNewAsesoriaToAttach.getClass(), asesoriaListNewAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaListNew.add(asesoriaListNewAsesoriaToAttach);
            }
            asesoriaListNew = attachedAsesoriaListNew;
            reservacion.setAsesoriaList(asesoriaListNew);
            reservacion = em.merge(reservacion);
            if (idSeguimientoOld != null && !idSeguimientoOld.equals(idSeguimientoNew)) {
                idSeguimientoOld.getReservacionList().remove(reservacion);
                idSeguimientoOld = em.merge(idSeguimientoOld);
            }
            if (idSeguimientoNew != null && !idSeguimientoNew.equals(idSeguimientoOld)) {
                idSeguimientoNew.getReservacionList().add(reservacion);
                idSeguimientoNew = em.merge(idSeguimientoNew);
            }
            for (Conversacion conversacionListNewConversacion : conversacionListNew) {
                if (!conversacionListOld.contains(conversacionListNewConversacion)) {
                    Reservacion oldIdReservacionOfConversacionListNewConversacion = conversacionListNewConversacion.getIdReservacion();
                    conversacionListNewConversacion.setIdReservacion(reservacion);
                    conversacionListNewConversacion = em.merge(conversacionListNewConversacion);
                    if (oldIdReservacionOfConversacionListNewConversacion != null && !oldIdReservacionOfConversacionListNewConversacion.equals(reservacion)) {
                        oldIdReservacionOfConversacionListNewConversacion.getConversacionList().remove(conversacionListNewConversacion);
                        oldIdReservacionOfConversacionListNewConversacion = em.merge(oldIdReservacionOfConversacionListNewConversacion);
                    }
                }
            }
            for (Asesoria asesoriaListNewAsesoria : asesoriaListNew) {
                if (!asesoriaListOld.contains(asesoriaListNewAsesoria)) {
                    Reservacion oldIdReservacionOfAsesoriaListNewAsesoria = asesoriaListNewAsesoria.getIdReservacion();
                    asesoriaListNewAsesoria.setIdReservacion(reservacion);
                    asesoriaListNewAsesoria = em.merge(asesoriaListNewAsesoria);
                    if (oldIdReservacionOfAsesoriaListNewAsesoria != null && !oldIdReservacionOfAsesoriaListNewAsesoria.equals(reservacion)) {
                        oldIdReservacionOfAsesoriaListNewAsesoria.getAsesoriaList().remove(asesoriaListNewAsesoria);
                        oldIdReservacionOfAsesoriaListNewAsesoria = em.merge(oldIdReservacionOfAsesoriaListNewAsesoria);
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
            List<Conversacion> conversacionListOrphanCheck = reservacion.getConversacionList();
            for (Conversacion conversacionListOrphanCheckConversacion : conversacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reservacion (" + reservacion + ") cannot be destroyed since the Conversacion " + conversacionListOrphanCheckConversacion + " in its conversacionList field has a non-nullable idReservacion field.");
            }
            List<Asesoria> asesoriaListOrphanCheck = reservacion.getAsesoriaList();
            for (Asesoria asesoriaListOrphanCheckAsesoria : asesoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reservacion (" + reservacion + ") cannot be destroyed since the Asesoria " + asesoriaListOrphanCheckAsesoria + " in its asesoriaList field has a non-nullable idReservacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Seguimiento idSeguimiento = reservacion.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento.getReservacionList().remove(reservacion);
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
