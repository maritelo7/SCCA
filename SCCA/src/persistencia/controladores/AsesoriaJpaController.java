/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Persistencia.Asesor;
import Persistencia.Asesoria;
import Persistencia.Reservacion;
import Persistencia.Seguimiento;
import Persistencia.controladores.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author marianacro
 */
public class AsesoriaJpaController implements Serializable {

    public AsesoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asesoria asesoria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asesor numPersonal = asesoria.getNumPersonal();
            if (numPersonal != null) {
                numPersonal = em.getReference(numPersonal.getClass(), numPersonal.getNumPersonal());
                asesoria.setNumPersonal(numPersonal);
            }
            Reservacion idReservacion = asesoria.getIdReservacion();
            if (idReservacion != null) {
                idReservacion = em.getReference(idReservacion.getClass(), idReservacion.getIdReservacion());
                asesoria.setIdReservacion(idReservacion);
            }
            Seguimiento idSeguimiento = asesoria.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento = em.getReference(idSeguimiento.getClass(), idSeguimiento.getIdSeguimiento());
                asesoria.setIdSeguimiento(idSeguimiento);
            }
            em.persist(asesoria);
            if (numPersonal != null) {
                numPersonal.getAsesoriaList().add(asesoria);
                numPersonal = em.merge(numPersonal);
            }
            if (idReservacion != null) {
                idReservacion.getAsesoriaList().add(asesoria);
                idReservacion = em.merge(idReservacion);
            }
            if (idSeguimiento != null) {
                idSeguimiento.getAsesoriaList().add(asesoria);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asesoria asesoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asesoria persistentAsesoria = em.find(Asesoria.class, asesoria.getIdAsesoria());
            Asesor numPersonalOld = persistentAsesoria.getNumPersonal();
            Asesor numPersonalNew = asesoria.getNumPersonal();
            Reservacion idReservacionOld = persistentAsesoria.getIdReservacion();
            Reservacion idReservacionNew = asesoria.getIdReservacion();
            Seguimiento idSeguimientoOld = persistentAsesoria.getIdSeguimiento();
            Seguimiento idSeguimientoNew = asesoria.getIdSeguimiento();
            if (numPersonalNew != null) {
                numPersonalNew = em.getReference(numPersonalNew.getClass(), numPersonalNew.getNumPersonal());
                asesoria.setNumPersonal(numPersonalNew);
            }
            if (idReservacionNew != null) {
                idReservacionNew = em.getReference(idReservacionNew.getClass(), idReservacionNew.getIdReservacion());
                asesoria.setIdReservacion(idReservacionNew);
            }
            if (idSeguimientoNew != null) {
                idSeguimientoNew = em.getReference(idSeguimientoNew.getClass(), idSeguimientoNew.getIdSeguimiento());
                asesoria.setIdSeguimiento(idSeguimientoNew);
            }
            asesoria = em.merge(asesoria);
            if (numPersonalOld != null && !numPersonalOld.equals(numPersonalNew)) {
                numPersonalOld.getAsesoriaList().remove(asesoria);
                numPersonalOld = em.merge(numPersonalOld);
            }
            if (numPersonalNew != null && !numPersonalNew.equals(numPersonalOld)) {
                numPersonalNew.getAsesoriaList().add(asesoria);
                numPersonalNew = em.merge(numPersonalNew);
            }
            if (idReservacionOld != null && !idReservacionOld.equals(idReservacionNew)) {
                idReservacionOld.getAsesoriaList().remove(asesoria);
                idReservacionOld = em.merge(idReservacionOld);
            }
            if (idReservacionNew != null && !idReservacionNew.equals(idReservacionOld)) {
                idReservacionNew.getAsesoriaList().add(asesoria);
                idReservacionNew = em.merge(idReservacionNew);
            }
            if (idSeguimientoOld != null && !idSeguimientoOld.equals(idSeguimientoNew)) {
                idSeguimientoOld.getAsesoriaList().remove(asesoria);
                idSeguimientoOld = em.merge(idSeguimientoOld);
            }
            if (idSeguimientoNew != null && !idSeguimientoNew.equals(idSeguimientoOld)) {
                idSeguimientoNew.getAsesoriaList().add(asesoria);
                idSeguimientoNew = em.merge(idSeguimientoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asesoria.getIdAsesoria();
                if (findAsesoria(id) == null) {
                    throw new NonexistentEntityException("The asesoria with id " + id + " no longer exists.");
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
            Asesoria asesoria;
            try {
                asesoria = em.getReference(Asesoria.class, id);
                asesoria.getIdAsesoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asesoria with id " + id + " no longer exists.", enfe);
            }
            Asesor numPersonal = asesoria.getNumPersonal();
            if (numPersonal != null) {
                numPersonal.getAsesoriaList().remove(asesoria);
                numPersonal = em.merge(numPersonal);
            }
            Reservacion idReservacion = asesoria.getIdReservacion();
            if (idReservacion != null) {
                idReservacion.getAsesoriaList().remove(asesoria);
                idReservacion = em.merge(idReservacion);
            }
            Seguimiento idSeguimiento = asesoria.getIdSeguimiento();
            if (idSeguimiento != null) {
                idSeguimiento.getAsesoriaList().remove(asesoria);
                idSeguimiento = em.merge(idSeguimiento);
            }
            em.remove(asesoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asesoria> findAsesoriaEntities() {
        return findAsesoriaEntities(true, -1, -1);
    }

    public List<Asesoria> findAsesoriaEntities(int maxResults, int firstResult) {
        return findAsesoriaEntities(false, maxResults, firstResult);
    }

    private List<Asesoria> findAsesoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asesoria.class));
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

    public Asesoria findAsesoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asesoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsesoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asesoria> rt = cq.from(Asesoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
