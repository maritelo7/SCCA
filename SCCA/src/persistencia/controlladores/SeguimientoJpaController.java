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
import persistencia.Inscripcion;
import persistencia.Asesoria;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.ObservacionGeneral;
import persistencia.HojaSeguimiento;
import persistencia.Reservacion;
import persistencia.Bitacora;
import persistencia.Seguimiento;
import persistencia.controlladores.exceptions.IllegalOrphanException;
import persistencia.controlladores.exceptions.NonexistentEntityException;

/**
 *
 * @author marianacro
 */
public class SeguimientoJpaController implements Serializable {

    public SeguimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Seguimiento seguimiento) {
        if (seguimiento.getAsesoriaCollection() == null) {
            seguimiento.setAsesoriaCollection(new ArrayList<Asesoria>());
        }
        if (seguimiento.getObservacionGeneralCollection() == null) {
            seguimiento.setObservacionGeneralCollection(new ArrayList<ObservacionGeneral>());
        }
        if (seguimiento.getHojaSeguimientoCollection() == null) {
            seguimiento.setHojaSeguimientoCollection(new ArrayList<HojaSeguimiento>());
        }
        if (seguimiento.getReservacionCollection() == null) {
            seguimiento.setReservacionCollection(new ArrayList<Reservacion>());
        }
        if (seguimiento.getBitacoraCollection() == null) {
            seguimiento.setBitacoraCollection(new ArrayList<Bitacora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Inscripcion numInscripcion = seguimiento.getNumInscripcion();
            if (numInscripcion != null) {
                numInscripcion = em.getReference(numInscripcion.getClass(), numInscripcion.getNumInscripcion());
                seguimiento.setNumInscripcion(numInscripcion);
            }
            Collection<Asesoria> attachedAsesoriaCollection = new ArrayList<Asesoria>();
            for (Asesoria asesoriaCollectionAsesoriaToAttach : seguimiento.getAsesoriaCollection()) {
                asesoriaCollectionAsesoriaToAttach = em.getReference(asesoriaCollectionAsesoriaToAttach.getClass(), asesoriaCollectionAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaCollection.add(asesoriaCollectionAsesoriaToAttach);
            }
            seguimiento.setAsesoriaCollection(attachedAsesoriaCollection);
            Collection<ObservacionGeneral> attachedObservacionGeneralCollection = new ArrayList<ObservacionGeneral>();
            for (ObservacionGeneral observacionGeneralCollectionObservacionGeneralToAttach : seguimiento.getObservacionGeneralCollection()) {
                observacionGeneralCollectionObservacionGeneralToAttach = em.getReference(observacionGeneralCollectionObservacionGeneralToAttach.getClass(), observacionGeneralCollectionObservacionGeneralToAttach.getIdObservacionGral());
                attachedObservacionGeneralCollection.add(observacionGeneralCollectionObservacionGeneralToAttach);
            }
            seguimiento.setObservacionGeneralCollection(attachedObservacionGeneralCollection);
            Collection<HojaSeguimiento> attachedHojaSeguimientoCollection = new ArrayList<HojaSeguimiento>();
            for (HojaSeguimiento hojaSeguimientoCollectionHojaSeguimientoToAttach : seguimiento.getHojaSeguimientoCollection()) {
                hojaSeguimientoCollectionHojaSeguimientoToAttach = em.getReference(hojaSeguimientoCollectionHojaSeguimientoToAttach.getClass(), hojaSeguimientoCollectionHojaSeguimientoToAttach.getIdHojaSeguimiento());
                attachedHojaSeguimientoCollection.add(hojaSeguimientoCollectionHojaSeguimientoToAttach);
            }
            seguimiento.setHojaSeguimientoCollection(attachedHojaSeguimientoCollection);
            Collection<Reservacion> attachedReservacionCollection = new ArrayList<Reservacion>();
            for (Reservacion reservacionCollectionReservacionToAttach : seguimiento.getReservacionCollection()) {
                reservacionCollectionReservacionToAttach = em.getReference(reservacionCollectionReservacionToAttach.getClass(), reservacionCollectionReservacionToAttach.getIdReservacion());
                attachedReservacionCollection.add(reservacionCollectionReservacionToAttach);
            }
            seguimiento.setReservacionCollection(attachedReservacionCollection);
            Collection<Bitacora> attachedBitacoraCollection = new ArrayList<Bitacora>();
            for (Bitacora bitacoraCollectionBitacoraToAttach : seguimiento.getBitacoraCollection()) {
                bitacoraCollectionBitacoraToAttach = em.getReference(bitacoraCollectionBitacoraToAttach.getClass(), bitacoraCollectionBitacoraToAttach.getIdBitacora());
                attachedBitacoraCollection.add(bitacoraCollectionBitacoraToAttach);
            }
            seguimiento.setBitacoraCollection(attachedBitacoraCollection);
            em.persist(seguimiento);
            if (numInscripcion != null) {
                numInscripcion.getSeguimientoCollection().add(seguimiento);
                numInscripcion = em.merge(numInscripcion);
            }
            for (Asesoria asesoriaCollectionAsesoria : seguimiento.getAsesoriaCollection()) {
                Seguimiento oldIdSeguimientoOfAsesoriaCollectionAsesoria = asesoriaCollectionAsesoria.getIdSeguimiento();
                asesoriaCollectionAsesoria.setIdSeguimiento(seguimiento);
                asesoriaCollectionAsesoria = em.merge(asesoriaCollectionAsesoria);
                if (oldIdSeguimientoOfAsesoriaCollectionAsesoria != null) {
                    oldIdSeguimientoOfAsesoriaCollectionAsesoria.getAsesoriaCollection().remove(asesoriaCollectionAsesoria);
                    oldIdSeguimientoOfAsesoriaCollectionAsesoria = em.merge(oldIdSeguimientoOfAsesoriaCollectionAsesoria);
                }
            }
            for (ObservacionGeneral observacionGeneralCollectionObservacionGeneral : seguimiento.getObservacionGeneralCollection()) {
                Seguimiento oldIdSeguimientoOfObservacionGeneralCollectionObservacionGeneral = observacionGeneralCollectionObservacionGeneral.getIdSeguimiento();
                observacionGeneralCollectionObservacionGeneral.setIdSeguimiento(seguimiento);
                observacionGeneralCollectionObservacionGeneral = em.merge(observacionGeneralCollectionObservacionGeneral);
                if (oldIdSeguimientoOfObservacionGeneralCollectionObservacionGeneral != null) {
                    oldIdSeguimientoOfObservacionGeneralCollectionObservacionGeneral.getObservacionGeneralCollection().remove(observacionGeneralCollectionObservacionGeneral);
                    oldIdSeguimientoOfObservacionGeneralCollectionObservacionGeneral = em.merge(oldIdSeguimientoOfObservacionGeneralCollectionObservacionGeneral);
                }
            }
            for (HojaSeguimiento hojaSeguimientoCollectionHojaSeguimiento : seguimiento.getHojaSeguimientoCollection()) {
                Seguimiento oldIdSeguimientoOfHojaSeguimientoCollectionHojaSeguimiento = hojaSeguimientoCollectionHojaSeguimiento.getIdSeguimiento();
                hojaSeguimientoCollectionHojaSeguimiento.setIdSeguimiento(seguimiento);
                hojaSeguimientoCollectionHojaSeguimiento = em.merge(hojaSeguimientoCollectionHojaSeguimiento);
                if (oldIdSeguimientoOfHojaSeguimientoCollectionHojaSeguimiento != null) {
                    oldIdSeguimientoOfHojaSeguimientoCollectionHojaSeguimiento.getHojaSeguimientoCollection().remove(hojaSeguimientoCollectionHojaSeguimiento);
                    oldIdSeguimientoOfHojaSeguimientoCollectionHojaSeguimiento = em.merge(oldIdSeguimientoOfHojaSeguimientoCollectionHojaSeguimiento);
                }
            }
            for (Reservacion reservacionCollectionReservacion : seguimiento.getReservacionCollection()) {
                Seguimiento oldIdSeguimientoOfReservacionCollectionReservacion = reservacionCollectionReservacion.getIdSeguimiento();
                reservacionCollectionReservacion.setIdSeguimiento(seguimiento);
                reservacionCollectionReservacion = em.merge(reservacionCollectionReservacion);
                if (oldIdSeguimientoOfReservacionCollectionReservacion != null) {
                    oldIdSeguimientoOfReservacionCollectionReservacion.getReservacionCollection().remove(reservacionCollectionReservacion);
                    oldIdSeguimientoOfReservacionCollectionReservacion = em.merge(oldIdSeguimientoOfReservacionCollectionReservacion);
                }
            }
            for (Bitacora bitacoraCollectionBitacora : seguimiento.getBitacoraCollection()) {
                Seguimiento oldIdSeguimientoOfBitacoraCollectionBitacora = bitacoraCollectionBitacora.getIdSeguimiento();
                bitacoraCollectionBitacora.setIdSeguimiento(seguimiento);
                bitacoraCollectionBitacora = em.merge(bitacoraCollectionBitacora);
                if (oldIdSeguimientoOfBitacoraCollectionBitacora != null) {
                    oldIdSeguimientoOfBitacoraCollectionBitacora.getBitacoraCollection().remove(bitacoraCollectionBitacora);
                    oldIdSeguimientoOfBitacoraCollectionBitacora = em.merge(oldIdSeguimientoOfBitacoraCollectionBitacora);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Seguimiento seguimiento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Seguimiento persistentSeguimiento = em.find(Seguimiento.class, seguimiento.getIdSeguimiento());
            Inscripcion numInscripcionOld = persistentSeguimiento.getNumInscripcion();
            Inscripcion numInscripcionNew = seguimiento.getNumInscripcion();
            Collection<Asesoria> asesoriaCollectionOld = persistentSeguimiento.getAsesoriaCollection();
            Collection<Asesoria> asesoriaCollectionNew = seguimiento.getAsesoriaCollection();
            Collection<ObservacionGeneral> observacionGeneralCollectionOld = persistentSeguimiento.getObservacionGeneralCollection();
            Collection<ObservacionGeneral> observacionGeneralCollectionNew = seguimiento.getObservacionGeneralCollection();
            Collection<HojaSeguimiento> hojaSeguimientoCollectionOld = persistentSeguimiento.getHojaSeguimientoCollection();
            Collection<HojaSeguimiento> hojaSeguimientoCollectionNew = seguimiento.getHojaSeguimientoCollection();
            Collection<Reservacion> reservacionCollectionOld = persistentSeguimiento.getReservacionCollection();
            Collection<Reservacion> reservacionCollectionNew = seguimiento.getReservacionCollection();
            Collection<Bitacora> bitacoraCollectionOld = persistentSeguimiento.getBitacoraCollection();
            Collection<Bitacora> bitacoraCollectionNew = seguimiento.getBitacoraCollection();
            List<String> illegalOrphanMessages = null;
            for (Asesoria asesoriaCollectionOldAsesoria : asesoriaCollectionOld) {
                if (!asesoriaCollectionNew.contains(asesoriaCollectionOldAsesoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asesoria " + asesoriaCollectionOldAsesoria + " since its idSeguimiento field is not nullable.");
                }
            }
            for (ObservacionGeneral observacionGeneralCollectionOldObservacionGeneral : observacionGeneralCollectionOld) {
                if (!observacionGeneralCollectionNew.contains(observacionGeneralCollectionOldObservacionGeneral)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ObservacionGeneral " + observacionGeneralCollectionOldObservacionGeneral + " since its idSeguimiento field is not nullable.");
                }
            }
            for (HojaSeguimiento hojaSeguimientoCollectionOldHojaSeguimiento : hojaSeguimientoCollectionOld) {
                if (!hojaSeguimientoCollectionNew.contains(hojaSeguimientoCollectionOldHojaSeguimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HojaSeguimiento " + hojaSeguimientoCollectionOldHojaSeguimiento + " since its idSeguimiento field is not nullable.");
                }
            }
            for (Reservacion reservacionCollectionOldReservacion : reservacionCollectionOld) {
                if (!reservacionCollectionNew.contains(reservacionCollectionOldReservacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservacion " + reservacionCollectionOldReservacion + " since its idSeguimiento field is not nullable.");
                }
            }
            for (Bitacora bitacoraCollectionOldBitacora : bitacoraCollectionOld) {
                if (!bitacoraCollectionNew.contains(bitacoraCollectionOldBitacora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bitacora " + bitacoraCollectionOldBitacora + " since its idSeguimiento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (numInscripcionNew != null) {
                numInscripcionNew = em.getReference(numInscripcionNew.getClass(), numInscripcionNew.getNumInscripcion());
                seguimiento.setNumInscripcion(numInscripcionNew);
            }
            Collection<Asesoria> attachedAsesoriaCollectionNew = new ArrayList<Asesoria>();
            for (Asesoria asesoriaCollectionNewAsesoriaToAttach : asesoriaCollectionNew) {
                asesoriaCollectionNewAsesoriaToAttach = em.getReference(asesoriaCollectionNewAsesoriaToAttach.getClass(), asesoriaCollectionNewAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaCollectionNew.add(asesoriaCollectionNewAsesoriaToAttach);
            }
            asesoriaCollectionNew = attachedAsesoriaCollectionNew;
            seguimiento.setAsesoriaCollection(asesoriaCollectionNew);
            Collection<ObservacionGeneral> attachedObservacionGeneralCollectionNew = new ArrayList<ObservacionGeneral>();
            for (ObservacionGeneral observacionGeneralCollectionNewObservacionGeneralToAttach : observacionGeneralCollectionNew) {
                observacionGeneralCollectionNewObservacionGeneralToAttach = em.getReference(observacionGeneralCollectionNewObservacionGeneralToAttach.getClass(), observacionGeneralCollectionNewObservacionGeneralToAttach.getIdObservacionGral());
                attachedObservacionGeneralCollectionNew.add(observacionGeneralCollectionNewObservacionGeneralToAttach);
            }
            observacionGeneralCollectionNew = attachedObservacionGeneralCollectionNew;
            seguimiento.setObservacionGeneralCollection(observacionGeneralCollectionNew);
            Collection<HojaSeguimiento> attachedHojaSeguimientoCollectionNew = new ArrayList<HojaSeguimiento>();
            for (HojaSeguimiento hojaSeguimientoCollectionNewHojaSeguimientoToAttach : hojaSeguimientoCollectionNew) {
                hojaSeguimientoCollectionNewHojaSeguimientoToAttach = em.getReference(hojaSeguimientoCollectionNewHojaSeguimientoToAttach.getClass(), hojaSeguimientoCollectionNewHojaSeguimientoToAttach.getIdHojaSeguimiento());
                attachedHojaSeguimientoCollectionNew.add(hojaSeguimientoCollectionNewHojaSeguimientoToAttach);
            }
            hojaSeguimientoCollectionNew = attachedHojaSeguimientoCollectionNew;
            seguimiento.setHojaSeguimientoCollection(hojaSeguimientoCollectionNew);
            Collection<Reservacion> attachedReservacionCollectionNew = new ArrayList<Reservacion>();
            for (Reservacion reservacionCollectionNewReservacionToAttach : reservacionCollectionNew) {
                reservacionCollectionNewReservacionToAttach = em.getReference(reservacionCollectionNewReservacionToAttach.getClass(), reservacionCollectionNewReservacionToAttach.getIdReservacion());
                attachedReservacionCollectionNew.add(reservacionCollectionNewReservacionToAttach);
            }
            reservacionCollectionNew = attachedReservacionCollectionNew;
            seguimiento.setReservacionCollection(reservacionCollectionNew);
            Collection<Bitacora> attachedBitacoraCollectionNew = new ArrayList<Bitacora>();
            for (Bitacora bitacoraCollectionNewBitacoraToAttach : bitacoraCollectionNew) {
                bitacoraCollectionNewBitacoraToAttach = em.getReference(bitacoraCollectionNewBitacoraToAttach.getClass(), bitacoraCollectionNewBitacoraToAttach.getIdBitacora());
                attachedBitacoraCollectionNew.add(bitacoraCollectionNewBitacoraToAttach);
            }
            bitacoraCollectionNew = attachedBitacoraCollectionNew;
            seguimiento.setBitacoraCollection(bitacoraCollectionNew);
            seguimiento = em.merge(seguimiento);
            if (numInscripcionOld != null && !numInscripcionOld.equals(numInscripcionNew)) {
                numInscripcionOld.getSeguimientoCollection().remove(seguimiento);
                numInscripcionOld = em.merge(numInscripcionOld);
            }
            if (numInscripcionNew != null && !numInscripcionNew.equals(numInscripcionOld)) {
                numInscripcionNew.getSeguimientoCollection().add(seguimiento);
                numInscripcionNew = em.merge(numInscripcionNew);
            }
            for (Asesoria asesoriaCollectionNewAsesoria : asesoriaCollectionNew) {
                if (!asesoriaCollectionOld.contains(asesoriaCollectionNewAsesoria)) {
                    Seguimiento oldIdSeguimientoOfAsesoriaCollectionNewAsesoria = asesoriaCollectionNewAsesoria.getIdSeguimiento();
                    asesoriaCollectionNewAsesoria.setIdSeguimiento(seguimiento);
                    asesoriaCollectionNewAsesoria = em.merge(asesoriaCollectionNewAsesoria);
                    if (oldIdSeguimientoOfAsesoriaCollectionNewAsesoria != null && !oldIdSeguimientoOfAsesoriaCollectionNewAsesoria.equals(seguimiento)) {
                        oldIdSeguimientoOfAsesoriaCollectionNewAsesoria.getAsesoriaCollection().remove(asesoriaCollectionNewAsesoria);
                        oldIdSeguimientoOfAsesoriaCollectionNewAsesoria = em.merge(oldIdSeguimientoOfAsesoriaCollectionNewAsesoria);
                    }
                }
            }
            for (ObservacionGeneral observacionGeneralCollectionNewObservacionGeneral : observacionGeneralCollectionNew) {
                if (!observacionGeneralCollectionOld.contains(observacionGeneralCollectionNewObservacionGeneral)) {
                    Seguimiento oldIdSeguimientoOfObservacionGeneralCollectionNewObservacionGeneral = observacionGeneralCollectionNewObservacionGeneral.getIdSeguimiento();
                    observacionGeneralCollectionNewObservacionGeneral.setIdSeguimiento(seguimiento);
                    observacionGeneralCollectionNewObservacionGeneral = em.merge(observacionGeneralCollectionNewObservacionGeneral);
                    if (oldIdSeguimientoOfObservacionGeneralCollectionNewObservacionGeneral != null && !oldIdSeguimientoOfObservacionGeneralCollectionNewObservacionGeneral.equals(seguimiento)) {
                        oldIdSeguimientoOfObservacionGeneralCollectionNewObservacionGeneral.getObservacionGeneralCollection().remove(observacionGeneralCollectionNewObservacionGeneral);
                        oldIdSeguimientoOfObservacionGeneralCollectionNewObservacionGeneral = em.merge(oldIdSeguimientoOfObservacionGeneralCollectionNewObservacionGeneral);
                    }
                }
            }
            for (HojaSeguimiento hojaSeguimientoCollectionNewHojaSeguimiento : hojaSeguimientoCollectionNew) {
                if (!hojaSeguimientoCollectionOld.contains(hojaSeguimientoCollectionNewHojaSeguimiento)) {
                    Seguimiento oldIdSeguimientoOfHojaSeguimientoCollectionNewHojaSeguimiento = hojaSeguimientoCollectionNewHojaSeguimiento.getIdSeguimiento();
                    hojaSeguimientoCollectionNewHojaSeguimiento.setIdSeguimiento(seguimiento);
                    hojaSeguimientoCollectionNewHojaSeguimiento = em.merge(hojaSeguimientoCollectionNewHojaSeguimiento);
                    if (oldIdSeguimientoOfHojaSeguimientoCollectionNewHojaSeguimiento != null && !oldIdSeguimientoOfHojaSeguimientoCollectionNewHojaSeguimiento.equals(seguimiento)) {
                        oldIdSeguimientoOfHojaSeguimientoCollectionNewHojaSeguimiento.getHojaSeguimientoCollection().remove(hojaSeguimientoCollectionNewHojaSeguimiento);
                        oldIdSeguimientoOfHojaSeguimientoCollectionNewHojaSeguimiento = em.merge(oldIdSeguimientoOfHojaSeguimientoCollectionNewHojaSeguimiento);
                    }
                }
            }
            for (Reservacion reservacionCollectionNewReservacion : reservacionCollectionNew) {
                if (!reservacionCollectionOld.contains(reservacionCollectionNewReservacion)) {
                    Seguimiento oldIdSeguimientoOfReservacionCollectionNewReservacion = reservacionCollectionNewReservacion.getIdSeguimiento();
                    reservacionCollectionNewReservacion.setIdSeguimiento(seguimiento);
                    reservacionCollectionNewReservacion = em.merge(reservacionCollectionNewReservacion);
                    if (oldIdSeguimientoOfReservacionCollectionNewReservacion != null && !oldIdSeguimientoOfReservacionCollectionNewReservacion.equals(seguimiento)) {
                        oldIdSeguimientoOfReservacionCollectionNewReservacion.getReservacionCollection().remove(reservacionCollectionNewReservacion);
                        oldIdSeguimientoOfReservacionCollectionNewReservacion = em.merge(oldIdSeguimientoOfReservacionCollectionNewReservacion);
                    }
                }
            }
            for (Bitacora bitacoraCollectionNewBitacora : bitacoraCollectionNew) {
                if (!bitacoraCollectionOld.contains(bitacoraCollectionNewBitacora)) {
                    Seguimiento oldIdSeguimientoOfBitacoraCollectionNewBitacora = bitacoraCollectionNewBitacora.getIdSeguimiento();
                    bitacoraCollectionNewBitacora.setIdSeguimiento(seguimiento);
                    bitacoraCollectionNewBitacora = em.merge(bitacoraCollectionNewBitacora);
                    if (oldIdSeguimientoOfBitacoraCollectionNewBitacora != null && !oldIdSeguimientoOfBitacoraCollectionNewBitacora.equals(seguimiento)) {
                        oldIdSeguimientoOfBitacoraCollectionNewBitacora.getBitacoraCollection().remove(bitacoraCollectionNewBitacora);
                        oldIdSeguimientoOfBitacoraCollectionNewBitacora = em.merge(oldIdSeguimientoOfBitacoraCollectionNewBitacora);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = seguimiento.getIdSeguimiento();
                if (findSeguimiento(id) == null) {
                    throw new NonexistentEntityException("The seguimiento with id " + id + " no longer exists.");
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
            Seguimiento seguimiento;
            try {
                seguimiento = em.getReference(Seguimiento.class, id);
                seguimiento.getIdSeguimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The seguimiento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Asesoria> asesoriaCollectionOrphanCheck = seguimiento.getAsesoriaCollection();
            for (Asesoria asesoriaCollectionOrphanCheckAsesoria : asesoriaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the Asesoria " + asesoriaCollectionOrphanCheckAsesoria + " in its asesoriaCollection field has a non-nullable idSeguimiento field.");
            }
            Collection<ObservacionGeneral> observacionGeneralCollectionOrphanCheck = seguimiento.getObservacionGeneralCollection();
            for (ObservacionGeneral observacionGeneralCollectionOrphanCheckObservacionGeneral : observacionGeneralCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the ObservacionGeneral " + observacionGeneralCollectionOrphanCheckObservacionGeneral + " in its observacionGeneralCollection field has a non-nullable idSeguimiento field.");
            }
            Collection<HojaSeguimiento> hojaSeguimientoCollectionOrphanCheck = seguimiento.getHojaSeguimientoCollection();
            for (HojaSeguimiento hojaSeguimientoCollectionOrphanCheckHojaSeguimiento : hojaSeguimientoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the HojaSeguimiento " + hojaSeguimientoCollectionOrphanCheckHojaSeguimiento + " in its hojaSeguimientoCollection field has a non-nullable idSeguimiento field.");
            }
            Collection<Reservacion> reservacionCollectionOrphanCheck = seguimiento.getReservacionCollection();
            for (Reservacion reservacionCollectionOrphanCheckReservacion : reservacionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the Reservacion " + reservacionCollectionOrphanCheckReservacion + " in its reservacionCollection field has a non-nullable idSeguimiento field.");
            }
            Collection<Bitacora> bitacoraCollectionOrphanCheck = seguimiento.getBitacoraCollection();
            for (Bitacora bitacoraCollectionOrphanCheckBitacora : bitacoraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the Bitacora " + bitacoraCollectionOrphanCheckBitacora + " in its bitacoraCollection field has a non-nullable idSeguimiento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Inscripcion numInscripcion = seguimiento.getNumInscripcion();
            if (numInscripcion != null) {
                numInscripcion.getSeguimientoCollection().remove(seguimiento);
                numInscripcion = em.merge(numInscripcion);
            }
            em.remove(seguimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Seguimiento> findSeguimientoEntities() {
        return findSeguimientoEntities(true, -1, -1);
    }

    public List<Seguimiento> findSeguimientoEntities(int maxResults, int firstResult) {
        return findSeguimientoEntities(false, maxResults, firstResult);
    }

    private List<Seguimiento> findSeguimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Seguimiento.class));
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

    public Seguimiento findSeguimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Seguimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeguimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Seguimiento> rt = cq.from(Seguimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
