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
import Persistencia.Inscripcion;
import Persistencia.ObservacionGeneral;
import java.util.ArrayList;
import java.util.List;
import Persistencia.HojaSeguimiento;
import Persistencia.Reservacion;
import Persistencia.ConversacionHasSeguimiento;
import Persistencia.TallerHasSeguimiento;
import Persistencia.Asesoria;
import Persistencia.Bitacora;
import Persistencia.Seguimiento;
import Persistencia.controladores.exceptions.IllegalOrphanException;
import Persistencia.controladores.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (seguimiento.getObservacionGeneralList() == null) {
            seguimiento.setObservacionGeneralList(new ArrayList<ObservacionGeneral>());
        }
        if (seguimiento.getHojaSeguimientoList() == null) {
            seguimiento.setHojaSeguimientoList(new ArrayList<HojaSeguimiento>());
        }
        if (seguimiento.getReservacionList() == null) {
            seguimiento.setReservacionList(new ArrayList<Reservacion>());
        }
        if (seguimiento.getConversacionHasSeguimientoList() == null) {
            seguimiento.setConversacionHasSeguimientoList(new ArrayList<ConversacionHasSeguimiento>());
        }
        if (seguimiento.getTallerHasSeguimientoList() == null) {
            seguimiento.setTallerHasSeguimientoList(new ArrayList<TallerHasSeguimiento>());
        }
        if (seguimiento.getAsesoriaList() == null) {
            seguimiento.setAsesoriaList(new ArrayList<Asesoria>());
        }
        if (seguimiento.getBitacoraList() == null) {
            seguimiento.setBitacoraList(new ArrayList<Bitacora>());
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
            List<ObservacionGeneral> attachedObservacionGeneralList = new ArrayList<ObservacionGeneral>();
            for (ObservacionGeneral observacionGeneralListObservacionGeneralToAttach : seguimiento.getObservacionGeneralList()) {
                observacionGeneralListObservacionGeneralToAttach = em.getReference(observacionGeneralListObservacionGeneralToAttach.getClass(), observacionGeneralListObservacionGeneralToAttach.getIdObservacionGral());
                attachedObservacionGeneralList.add(observacionGeneralListObservacionGeneralToAttach);
            }
            seguimiento.setObservacionGeneralList(attachedObservacionGeneralList);
            List<HojaSeguimiento> attachedHojaSeguimientoList = new ArrayList<HojaSeguimiento>();
            for (HojaSeguimiento hojaSeguimientoListHojaSeguimientoToAttach : seguimiento.getHojaSeguimientoList()) {
                hojaSeguimientoListHojaSeguimientoToAttach = em.getReference(hojaSeguimientoListHojaSeguimientoToAttach.getClass(), hojaSeguimientoListHojaSeguimientoToAttach.getIdHojaSeguimiento());
                attachedHojaSeguimientoList.add(hojaSeguimientoListHojaSeguimientoToAttach);
            }
            seguimiento.setHojaSeguimientoList(attachedHojaSeguimientoList);
            List<Reservacion> attachedReservacionList = new ArrayList<Reservacion>();
            for (Reservacion reservacionListReservacionToAttach : seguimiento.getReservacionList()) {
                reservacionListReservacionToAttach = em.getReference(reservacionListReservacionToAttach.getClass(), reservacionListReservacionToAttach.getIdReservacion());
                attachedReservacionList.add(reservacionListReservacionToAttach);
            }
            seguimiento.setReservacionList(attachedReservacionList);
            List<ConversacionHasSeguimiento> attachedConversacionHasSeguimientoList = new ArrayList<ConversacionHasSeguimiento>();
            for (ConversacionHasSeguimiento conversacionHasSeguimientoListConversacionHasSeguimientoToAttach : seguimiento.getConversacionHasSeguimientoList()) {
                conversacionHasSeguimientoListConversacionHasSeguimientoToAttach = em.getReference(conversacionHasSeguimientoListConversacionHasSeguimientoToAttach.getClass(), conversacionHasSeguimientoListConversacionHasSeguimientoToAttach.getIdConversacionSeguimiento());
                attachedConversacionHasSeguimientoList.add(conversacionHasSeguimientoListConversacionHasSeguimientoToAttach);
            }
            seguimiento.setConversacionHasSeguimientoList(attachedConversacionHasSeguimientoList);
            List<TallerHasSeguimiento> attachedTallerHasSeguimientoList = new ArrayList<TallerHasSeguimiento>();
            for (TallerHasSeguimiento tallerHasSeguimientoListTallerHasSeguimientoToAttach : seguimiento.getTallerHasSeguimientoList()) {
                tallerHasSeguimientoListTallerHasSeguimientoToAttach = em.getReference(tallerHasSeguimientoListTallerHasSeguimientoToAttach.getClass(), tallerHasSeguimientoListTallerHasSeguimientoToAttach.getIdTallerSeguimiento());
                attachedTallerHasSeguimientoList.add(tallerHasSeguimientoListTallerHasSeguimientoToAttach);
            }
            seguimiento.setTallerHasSeguimientoList(attachedTallerHasSeguimientoList);
            List<Asesoria> attachedAsesoriaList = new ArrayList<Asesoria>();
            for (Asesoria asesoriaListAsesoriaToAttach : seguimiento.getAsesoriaList()) {
                asesoriaListAsesoriaToAttach = em.getReference(asesoriaListAsesoriaToAttach.getClass(), asesoriaListAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaList.add(asesoriaListAsesoriaToAttach);
            }
            seguimiento.setAsesoriaList(attachedAsesoriaList);
            List<Bitacora> attachedBitacoraList = new ArrayList<Bitacora>();
            for (Bitacora bitacoraListBitacoraToAttach : seguimiento.getBitacoraList()) {
                bitacoraListBitacoraToAttach = em.getReference(bitacoraListBitacoraToAttach.getClass(), bitacoraListBitacoraToAttach.getIdBitacora());
                attachedBitacoraList.add(bitacoraListBitacoraToAttach);
            }
            seguimiento.setBitacoraList(attachedBitacoraList);
            em.persist(seguimiento);
            if (numInscripcion != null) {
                numInscripcion.getSeguimientoList().add(seguimiento);
                numInscripcion = em.merge(numInscripcion);
            }
            for (ObservacionGeneral observacionGeneralListObservacionGeneral : seguimiento.getObservacionGeneralList()) {
                Seguimiento oldIdSeguimientoOfObservacionGeneralListObservacionGeneral = observacionGeneralListObservacionGeneral.getIdSeguimiento();
                observacionGeneralListObservacionGeneral.setIdSeguimiento(seguimiento);
                observacionGeneralListObservacionGeneral = em.merge(observacionGeneralListObservacionGeneral);
                if (oldIdSeguimientoOfObservacionGeneralListObservacionGeneral != null) {
                    oldIdSeguimientoOfObservacionGeneralListObservacionGeneral.getObservacionGeneralList().remove(observacionGeneralListObservacionGeneral);
                    oldIdSeguimientoOfObservacionGeneralListObservacionGeneral = em.merge(oldIdSeguimientoOfObservacionGeneralListObservacionGeneral);
                }
            }
            for (HojaSeguimiento hojaSeguimientoListHojaSeguimiento : seguimiento.getHojaSeguimientoList()) {
                Seguimiento oldIdSeguimientoOfHojaSeguimientoListHojaSeguimiento = hojaSeguimientoListHojaSeguimiento.getIdSeguimiento();
                hojaSeguimientoListHojaSeguimiento.setIdSeguimiento(seguimiento);
                hojaSeguimientoListHojaSeguimiento = em.merge(hojaSeguimientoListHojaSeguimiento);
                if (oldIdSeguimientoOfHojaSeguimientoListHojaSeguimiento != null) {
                    oldIdSeguimientoOfHojaSeguimientoListHojaSeguimiento.getHojaSeguimientoList().remove(hojaSeguimientoListHojaSeguimiento);
                    oldIdSeguimientoOfHojaSeguimientoListHojaSeguimiento = em.merge(oldIdSeguimientoOfHojaSeguimientoListHojaSeguimiento);
                }
            }
            for (Reservacion reservacionListReservacion : seguimiento.getReservacionList()) {
                Seguimiento oldIdSeguimientoOfReservacionListReservacion = reservacionListReservacion.getIdSeguimiento();
                reservacionListReservacion.setIdSeguimiento(seguimiento);
                reservacionListReservacion = em.merge(reservacionListReservacion);
                if (oldIdSeguimientoOfReservacionListReservacion != null) {
                    oldIdSeguimientoOfReservacionListReservacion.getReservacionList().remove(reservacionListReservacion);
                    oldIdSeguimientoOfReservacionListReservacion = em.merge(oldIdSeguimientoOfReservacionListReservacion);
                }
            }
            for (ConversacionHasSeguimiento conversacionHasSeguimientoListConversacionHasSeguimiento : seguimiento.getConversacionHasSeguimientoList()) {
                Seguimiento oldIdSeguimientoOfConversacionHasSeguimientoListConversacionHasSeguimiento = conversacionHasSeguimientoListConversacionHasSeguimiento.getIdSeguimiento();
                conversacionHasSeguimientoListConversacionHasSeguimiento.setIdSeguimiento(seguimiento);
                conversacionHasSeguimientoListConversacionHasSeguimiento = em.merge(conversacionHasSeguimientoListConversacionHasSeguimiento);
                if (oldIdSeguimientoOfConversacionHasSeguimientoListConversacionHasSeguimiento != null) {
                    oldIdSeguimientoOfConversacionHasSeguimientoListConversacionHasSeguimiento.getConversacionHasSeguimientoList().remove(conversacionHasSeguimientoListConversacionHasSeguimiento);
                    oldIdSeguimientoOfConversacionHasSeguimientoListConversacionHasSeguimiento = em.merge(oldIdSeguimientoOfConversacionHasSeguimientoListConversacionHasSeguimiento);
                }
            }
            for (TallerHasSeguimiento tallerHasSeguimientoListTallerHasSeguimiento : seguimiento.getTallerHasSeguimientoList()) {
                Seguimiento oldIdSeguimientoOfTallerHasSeguimientoListTallerHasSeguimiento = tallerHasSeguimientoListTallerHasSeguimiento.getIdSeguimiento();
                tallerHasSeguimientoListTallerHasSeguimiento.setIdSeguimiento(seguimiento);
                tallerHasSeguimientoListTallerHasSeguimiento = em.merge(tallerHasSeguimientoListTallerHasSeguimiento);
                if (oldIdSeguimientoOfTallerHasSeguimientoListTallerHasSeguimiento != null) {
                    oldIdSeguimientoOfTallerHasSeguimientoListTallerHasSeguimiento.getTallerHasSeguimientoList().remove(tallerHasSeguimientoListTallerHasSeguimiento);
                    oldIdSeguimientoOfTallerHasSeguimientoListTallerHasSeguimiento = em.merge(oldIdSeguimientoOfTallerHasSeguimientoListTallerHasSeguimiento);
                }
            }
            for (Asesoria asesoriaListAsesoria : seguimiento.getAsesoriaList()) {
                Seguimiento oldIdSeguimientoOfAsesoriaListAsesoria = asesoriaListAsesoria.getIdSeguimiento();
                asesoriaListAsesoria.setIdSeguimiento(seguimiento);
                asesoriaListAsesoria = em.merge(asesoriaListAsesoria);
                if (oldIdSeguimientoOfAsesoriaListAsesoria != null) {
                    oldIdSeguimientoOfAsesoriaListAsesoria.getAsesoriaList().remove(asesoriaListAsesoria);
                    oldIdSeguimientoOfAsesoriaListAsesoria = em.merge(oldIdSeguimientoOfAsesoriaListAsesoria);
                }
            }
            for (Bitacora bitacoraListBitacora : seguimiento.getBitacoraList()) {
                Seguimiento oldIdSeguimientoOfBitacoraListBitacora = bitacoraListBitacora.getIdSeguimiento();
                bitacoraListBitacora.setIdSeguimiento(seguimiento);
                bitacoraListBitacora = em.merge(bitacoraListBitacora);
                if (oldIdSeguimientoOfBitacoraListBitacora != null) {
                    oldIdSeguimientoOfBitacoraListBitacora.getBitacoraList().remove(bitacoraListBitacora);
                    oldIdSeguimientoOfBitacoraListBitacora = em.merge(oldIdSeguimientoOfBitacoraListBitacora);
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
            List<ObservacionGeneral> observacionGeneralListOld = persistentSeguimiento.getObservacionGeneralList();
            List<ObservacionGeneral> observacionGeneralListNew = seguimiento.getObservacionGeneralList();
            List<HojaSeguimiento> hojaSeguimientoListOld = persistentSeguimiento.getHojaSeguimientoList();
            List<HojaSeguimiento> hojaSeguimientoListNew = seguimiento.getHojaSeguimientoList();
            List<Reservacion> reservacionListOld = persistentSeguimiento.getReservacionList();
            List<Reservacion> reservacionListNew = seguimiento.getReservacionList();
            List<ConversacionHasSeguimiento> conversacionHasSeguimientoListOld = persistentSeguimiento.getConversacionHasSeguimientoList();
            List<ConversacionHasSeguimiento> conversacionHasSeguimientoListNew = seguimiento.getConversacionHasSeguimientoList();
            List<TallerHasSeguimiento> tallerHasSeguimientoListOld = persistentSeguimiento.getTallerHasSeguimientoList();
            List<TallerHasSeguimiento> tallerHasSeguimientoListNew = seguimiento.getTallerHasSeguimientoList();
            List<Asesoria> asesoriaListOld = persistentSeguimiento.getAsesoriaList();
            List<Asesoria> asesoriaListNew = seguimiento.getAsesoriaList();
            List<Bitacora> bitacoraListOld = persistentSeguimiento.getBitacoraList();
            List<Bitacora> bitacoraListNew = seguimiento.getBitacoraList();
            List<String> illegalOrphanMessages = null;
            for (ObservacionGeneral observacionGeneralListOldObservacionGeneral : observacionGeneralListOld) {
                if (!observacionGeneralListNew.contains(observacionGeneralListOldObservacionGeneral)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ObservacionGeneral " + observacionGeneralListOldObservacionGeneral + " since its idSeguimiento field is not nullable.");
                }
            }
            for (HojaSeguimiento hojaSeguimientoListOldHojaSeguimiento : hojaSeguimientoListOld) {
                if (!hojaSeguimientoListNew.contains(hojaSeguimientoListOldHojaSeguimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HojaSeguimiento " + hojaSeguimientoListOldHojaSeguimiento + " since its idSeguimiento field is not nullable.");
                }
            }
            for (Reservacion reservacionListOldReservacion : reservacionListOld) {
                if (!reservacionListNew.contains(reservacionListOldReservacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservacion " + reservacionListOldReservacion + " since its idSeguimiento field is not nullable.");
                }
            }
            for (ConversacionHasSeguimiento conversacionHasSeguimientoListOldConversacionHasSeguimiento : conversacionHasSeguimientoListOld) {
                if (!conversacionHasSeguimientoListNew.contains(conversacionHasSeguimientoListOldConversacionHasSeguimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ConversacionHasSeguimiento " + conversacionHasSeguimientoListOldConversacionHasSeguimiento + " since its idSeguimiento field is not nullable.");
                }
            }
            for (TallerHasSeguimiento tallerHasSeguimientoListOldTallerHasSeguimiento : tallerHasSeguimientoListOld) {
                if (!tallerHasSeguimientoListNew.contains(tallerHasSeguimientoListOldTallerHasSeguimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TallerHasSeguimiento " + tallerHasSeguimientoListOldTallerHasSeguimiento + " since its idSeguimiento field is not nullable.");
                }
            }
            for (Asesoria asesoriaListOldAsesoria : asesoriaListOld) {
                if (!asesoriaListNew.contains(asesoriaListOldAsesoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asesoria " + asesoriaListOldAsesoria + " since its idSeguimiento field is not nullable.");
                }
            }
            for (Bitacora bitacoraListOldBitacora : bitacoraListOld) {
                if (!bitacoraListNew.contains(bitacoraListOldBitacora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bitacora " + bitacoraListOldBitacora + " since its idSeguimiento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (numInscripcionNew != null) {
                numInscripcionNew = em.getReference(numInscripcionNew.getClass(), numInscripcionNew.getNumInscripcion());
                seguimiento.setNumInscripcion(numInscripcionNew);
            }
            List<ObservacionGeneral> attachedObservacionGeneralListNew = new ArrayList<ObservacionGeneral>();
            for (ObservacionGeneral observacionGeneralListNewObservacionGeneralToAttach : observacionGeneralListNew) {
                observacionGeneralListNewObservacionGeneralToAttach = em.getReference(observacionGeneralListNewObservacionGeneralToAttach.getClass(), observacionGeneralListNewObservacionGeneralToAttach.getIdObservacionGral());
                attachedObservacionGeneralListNew.add(observacionGeneralListNewObservacionGeneralToAttach);
            }
            observacionGeneralListNew = attachedObservacionGeneralListNew;
            seguimiento.setObservacionGeneralList(observacionGeneralListNew);
            List<HojaSeguimiento> attachedHojaSeguimientoListNew = new ArrayList<HojaSeguimiento>();
            for (HojaSeguimiento hojaSeguimientoListNewHojaSeguimientoToAttach : hojaSeguimientoListNew) {
                hojaSeguimientoListNewHojaSeguimientoToAttach = em.getReference(hojaSeguimientoListNewHojaSeguimientoToAttach.getClass(), hojaSeguimientoListNewHojaSeguimientoToAttach.getIdHojaSeguimiento());
                attachedHojaSeguimientoListNew.add(hojaSeguimientoListNewHojaSeguimientoToAttach);
            }
            hojaSeguimientoListNew = attachedHojaSeguimientoListNew;
            seguimiento.setHojaSeguimientoList(hojaSeguimientoListNew);
            List<Reservacion> attachedReservacionListNew = new ArrayList<Reservacion>();
            for (Reservacion reservacionListNewReservacionToAttach : reservacionListNew) {
                reservacionListNewReservacionToAttach = em.getReference(reservacionListNewReservacionToAttach.getClass(), reservacionListNewReservacionToAttach.getIdReservacion());
                attachedReservacionListNew.add(reservacionListNewReservacionToAttach);
            }
            reservacionListNew = attachedReservacionListNew;
            seguimiento.setReservacionList(reservacionListNew);
            List<ConversacionHasSeguimiento> attachedConversacionHasSeguimientoListNew = new ArrayList<ConversacionHasSeguimiento>();
            for (ConversacionHasSeguimiento conversacionHasSeguimientoListNewConversacionHasSeguimientoToAttach : conversacionHasSeguimientoListNew) {
                conversacionHasSeguimientoListNewConversacionHasSeguimientoToAttach = em.getReference(conversacionHasSeguimientoListNewConversacionHasSeguimientoToAttach.getClass(), conversacionHasSeguimientoListNewConversacionHasSeguimientoToAttach.getIdConversacionSeguimiento());
                attachedConversacionHasSeguimientoListNew.add(conversacionHasSeguimientoListNewConversacionHasSeguimientoToAttach);
            }
            conversacionHasSeguimientoListNew = attachedConversacionHasSeguimientoListNew;
            seguimiento.setConversacionHasSeguimientoList(conversacionHasSeguimientoListNew);
            List<TallerHasSeguimiento> attachedTallerHasSeguimientoListNew = new ArrayList<TallerHasSeguimiento>();
            for (TallerHasSeguimiento tallerHasSeguimientoListNewTallerHasSeguimientoToAttach : tallerHasSeguimientoListNew) {
                tallerHasSeguimientoListNewTallerHasSeguimientoToAttach = em.getReference(tallerHasSeguimientoListNewTallerHasSeguimientoToAttach.getClass(), tallerHasSeguimientoListNewTallerHasSeguimientoToAttach.getIdTallerSeguimiento());
                attachedTallerHasSeguimientoListNew.add(tallerHasSeguimientoListNewTallerHasSeguimientoToAttach);
            }
            tallerHasSeguimientoListNew = attachedTallerHasSeguimientoListNew;
            seguimiento.setTallerHasSeguimientoList(tallerHasSeguimientoListNew);
            List<Asesoria> attachedAsesoriaListNew = new ArrayList<Asesoria>();
            for (Asesoria asesoriaListNewAsesoriaToAttach : asesoriaListNew) {
                asesoriaListNewAsesoriaToAttach = em.getReference(asesoriaListNewAsesoriaToAttach.getClass(), asesoriaListNewAsesoriaToAttach.getIdAsesoria());
                attachedAsesoriaListNew.add(asesoriaListNewAsesoriaToAttach);
            }
            asesoriaListNew = attachedAsesoriaListNew;
            seguimiento.setAsesoriaList(asesoriaListNew);
            List<Bitacora> attachedBitacoraListNew = new ArrayList<Bitacora>();
            for (Bitacora bitacoraListNewBitacoraToAttach : bitacoraListNew) {
                bitacoraListNewBitacoraToAttach = em.getReference(bitacoraListNewBitacoraToAttach.getClass(), bitacoraListNewBitacoraToAttach.getIdBitacora());
                attachedBitacoraListNew.add(bitacoraListNewBitacoraToAttach);
            }
            bitacoraListNew = attachedBitacoraListNew;
            seguimiento.setBitacoraList(bitacoraListNew);
            seguimiento = em.merge(seguimiento);
            if (numInscripcionOld != null && !numInscripcionOld.equals(numInscripcionNew)) {
                numInscripcionOld.getSeguimientoList().remove(seguimiento);
                numInscripcionOld = em.merge(numInscripcionOld);
            }
            if (numInscripcionNew != null && !numInscripcionNew.equals(numInscripcionOld)) {
                numInscripcionNew.getSeguimientoList().add(seguimiento);
                numInscripcionNew = em.merge(numInscripcionNew);
            }
            for (ObservacionGeneral observacionGeneralListNewObservacionGeneral : observacionGeneralListNew) {
                if (!observacionGeneralListOld.contains(observacionGeneralListNewObservacionGeneral)) {
                    Seguimiento oldIdSeguimientoOfObservacionGeneralListNewObservacionGeneral = observacionGeneralListNewObservacionGeneral.getIdSeguimiento();
                    observacionGeneralListNewObservacionGeneral.setIdSeguimiento(seguimiento);
                    observacionGeneralListNewObservacionGeneral = em.merge(observacionGeneralListNewObservacionGeneral);
                    if (oldIdSeguimientoOfObservacionGeneralListNewObservacionGeneral != null && !oldIdSeguimientoOfObservacionGeneralListNewObservacionGeneral.equals(seguimiento)) {
                        oldIdSeguimientoOfObservacionGeneralListNewObservacionGeneral.getObservacionGeneralList().remove(observacionGeneralListNewObservacionGeneral);
                        oldIdSeguimientoOfObservacionGeneralListNewObservacionGeneral = em.merge(oldIdSeguimientoOfObservacionGeneralListNewObservacionGeneral);
                    }
                }
            }
            for (HojaSeguimiento hojaSeguimientoListNewHojaSeguimiento : hojaSeguimientoListNew) {
                if (!hojaSeguimientoListOld.contains(hojaSeguimientoListNewHojaSeguimiento)) {
                    Seguimiento oldIdSeguimientoOfHojaSeguimientoListNewHojaSeguimiento = hojaSeguimientoListNewHojaSeguimiento.getIdSeguimiento();
                    hojaSeguimientoListNewHojaSeguimiento.setIdSeguimiento(seguimiento);
                    hojaSeguimientoListNewHojaSeguimiento = em.merge(hojaSeguimientoListNewHojaSeguimiento);
                    if (oldIdSeguimientoOfHojaSeguimientoListNewHojaSeguimiento != null && !oldIdSeguimientoOfHojaSeguimientoListNewHojaSeguimiento.equals(seguimiento)) {
                        oldIdSeguimientoOfHojaSeguimientoListNewHojaSeguimiento.getHojaSeguimientoList().remove(hojaSeguimientoListNewHojaSeguimiento);
                        oldIdSeguimientoOfHojaSeguimientoListNewHojaSeguimiento = em.merge(oldIdSeguimientoOfHojaSeguimientoListNewHojaSeguimiento);
                    }
                }
            }
            for (Reservacion reservacionListNewReservacion : reservacionListNew) {
                if (!reservacionListOld.contains(reservacionListNewReservacion)) {
                    Seguimiento oldIdSeguimientoOfReservacionListNewReservacion = reservacionListNewReservacion.getIdSeguimiento();
                    reservacionListNewReservacion.setIdSeguimiento(seguimiento);
                    reservacionListNewReservacion = em.merge(reservacionListNewReservacion);
                    if (oldIdSeguimientoOfReservacionListNewReservacion != null && !oldIdSeguimientoOfReservacionListNewReservacion.equals(seguimiento)) {
                        oldIdSeguimientoOfReservacionListNewReservacion.getReservacionList().remove(reservacionListNewReservacion);
                        oldIdSeguimientoOfReservacionListNewReservacion = em.merge(oldIdSeguimientoOfReservacionListNewReservacion);
                    }
                }
            }
            for (ConversacionHasSeguimiento conversacionHasSeguimientoListNewConversacionHasSeguimiento : conversacionHasSeguimientoListNew) {
                if (!conversacionHasSeguimientoListOld.contains(conversacionHasSeguimientoListNewConversacionHasSeguimiento)) {
                    Seguimiento oldIdSeguimientoOfConversacionHasSeguimientoListNewConversacionHasSeguimiento = conversacionHasSeguimientoListNewConversacionHasSeguimiento.getIdSeguimiento();
                    conversacionHasSeguimientoListNewConversacionHasSeguimiento.setIdSeguimiento(seguimiento);
                    conversacionHasSeguimientoListNewConversacionHasSeguimiento = em.merge(conversacionHasSeguimientoListNewConversacionHasSeguimiento);
                    if (oldIdSeguimientoOfConversacionHasSeguimientoListNewConversacionHasSeguimiento != null && !oldIdSeguimientoOfConversacionHasSeguimientoListNewConversacionHasSeguimiento.equals(seguimiento)) {
                        oldIdSeguimientoOfConversacionHasSeguimientoListNewConversacionHasSeguimiento.getConversacionHasSeguimientoList().remove(conversacionHasSeguimientoListNewConversacionHasSeguimiento);
                        oldIdSeguimientoOfConversacionHasSeguimientoListNewConversacionHasSeguimiento = em.merge(oldIdSeguimientoOfConversacionHasSeguimientoListNewConversacionHasSeguimiento);
                    }
                }
            }
            for (TallerHasSeguimiento tallerHasSeguimientoListNewTallerHasSeguimiento : tallerHasSeguimientoListNew) {
                if (!tallerHasSeguimientoListOld.contains(tallerHasSeguimientoListNewTallerHasSeguimiento)) {
                    Seguimiento oldIdSeguimientoOfTallerHasSeguimientoListNewTallerHasSeguimiento = tallerHasSeguimientoListNewTallerHasSeguimiento.getIdSeguimiento();
                    tallerHasSeguimientoListNewTallerHasSeguimiento.setIdSeguimiento(seguimiento);
                    tallerHasSeguimientoListNewTallerHasSeguimiento = em.merge(tallerHasSeguimientoListNewTallerHasSeguimiento);
                    if (oldIdSeguimientoOfTallerHasSeguimientoListNewTallerHasSeguimiento != null && !oldIdSeguimientoOfTallerHasSeguimientoListNewTallerHasSeguimiento.equals(seguimiento)) {
                        oldIdSeguimientoOfTallerHasSeguimientoListNewTallerHasSeguimiento.getTallerHasSeguimientoList().remove(tallerHasSeguimientoListNewTallerHasSeguimiento);
                        oldIdSeguimientoOfTallerHasSeguimientoListNewTallerHasSeguimiento = em.merge(oldIdSeguimientoOfTallerHasSeguimientoListNewTallerHasSeguimiento);
                    }
                }
            }
            for (Asesoria asesoriaListNewAsesoria : asesoriaListNew) {
                if (!asesoriaListOld.contains(asesoriaListNewAsesoria)) {
                    Seguimiento oldIdSeguimientoOfAsesoriaListNewAsesoria = asesoriaListNewAsesoria.getIdSeguimiento();
                    asesoriaListNewAsesoria.setIdSeguimiento(seguimiento);
                    asesoriaListNewAsesoria = em.merge(asesoriaListNewAsesoria);
                    if (oldIdSeguimientoOfAsesoriaListNewAsesoria != null && !oldIdSeguimientoOfAsesoriaListNewAsesoria.equals(seguimiento)) {
                        oldIdSeguimientoOfAsesoriaListNewAsesoria.getAsesoriaList().remove(asesoriaListNewAsesoria);
                        oldIdSeguimientoOfAsesoriaListNewAsesoria = em.merge(oldIdSeguimientoOfAsesoriaListNewAsesoria);
                    }
                }
            }
            for (Bitacora bitacoraListNewBitacora : bitacoraListNew) {
                if (!bitacoraListOld.contains(bitacoraListNewBitacora)) {
                    Seguimiento oldIdSeguimientoOfBitacoraListNewBitacora = bitacoraListNewBitacora.getIdSeguimiento();
                    bitacoraListNewBitacora.setIdSeguimiento(seguimiento);
                    bitacoraListNewBitacora = em.merge(bitacoraListNewBitacora);
                    if (oldIdSeguimientoOfBitacoraListNewBitacora != null && !oldIdSeguimientoOfBitacoraListNewBitacora.equals(seguimiento)) {
                        oldIdSeguimientoOfBitacoraListNewBitacora.getBitacoraList().remove(bitacoraListNewBitacora);
                        oldIdSeguimientoOfBitacoraListNewBitacora = em.merge(oldIdSeguimientoOfBitacoraListNewBitacora);
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
            List<ObservacionGeneral> observacionGeneralListOrphanCheck = seguimiento.getObservacionGeneralList();
            for (ObservacionGeneral observacionGeneralListOrphanCheckObservacionGeneral : observacionGeneralListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the ObservacionGeneral " + observacionGeneralListOrphanCheckObservacionGeneral + " in its observacionGeneralList field has a non-nullable idSeguimiento field.");
            }
            List<HojaSeguimiento> hojaSeguimientoListOrphanCheck = seguimiento.getHojaSeguimientoList();
            for (HojaSeguimiento hojaSeguimientoListOrphanCheckHojaSeguimiento : hojaSeguimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the HojaSeguimiento " + hojaSeguimientoListOrphanCheckHojaSeguimiento + " in its hojaSeguimientoList field has a non-nullable idSeguimiento field.");
            }
            List<Reservacion> reservacionListOrphanCheck = seguimiento.getReservacionList();
            for (Reservacion reservacionListOrphanCheckReservacion : reservacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the Reservacion " + reservacionListOrphanCheckReservacion + " in its reservacionList field has a non-nullable idSeguimiento field.");
            }
            List<ConversacionHasSeguimiento> conversacionHasSeguimientoListOrphanCheck = seguimiento.getConversacionHasSeguimientoList();
            for (ConversacionHasSeguimiento conversacionHasSeguimientoListOrphanCheckConversacionHasSeguimiento : conversacionHasSeguimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the ConversacionHasSeguimiento " + conversacionHasSeguimientoListOrphanCheckConversacionHasSeguimiento + " in its conversacionHasSeguimientoList field has a non-nullable idSeguimiento field.");
            }
            List<TallerHasSeguimiento> tallerHasSeguimientoListOrphanCheck = seguimiento.getTallerHasSeguimientoList();
            for (TallerHasSeguimiento tallerHasSeguimientoListOrphanCheckTallerHasSeguimiento : tallerHasSeguimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the TallerHasSeguimiento " + tallerHasSeguimientoListOrphanCheckTallerHasSeguimiento + " in its tallerHasSeguimientoList field has a non-nullable idSeguimiento field.");
            }
            List<Asesoria> asesoriaListOrphanCheck = seguimiento.getAsesoriaList();
            for (Asesoria asesoriaListOrphanCheckAsesoria : asesoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the Asesoria " + asesoriaListOrphanCheckAsesoria + " in its asesoriaList field has a non-nullable idSeguimiento field.");
            }
            List<Bitacora> bitacoraListOrphanCheck = seguimiento.getBitacoraList();
            for (Bitacora bitacoraListOrphanCheckBitacora : bitacoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Seguimiento (" + seguimiento + ") cannot be destroyed since the Bitacora " + bitacoraListOrphanCheckBitacora + " in its bitacoraList field has a non-nullable idSeguimiento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Inscripcion numInscripcion = seguimiento.getNumInscripcion();
            if (numInscripcion != null) {
                numInscripcion.getSeguimientoList().remove(seguimiento);
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
