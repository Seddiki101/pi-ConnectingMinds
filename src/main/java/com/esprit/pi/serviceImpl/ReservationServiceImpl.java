package com.esprit.pi.serviceImpl;

import com.esprit.pi.daos.ReservationDaos;
import com.esprit.pi.daos.WorkshopDaos;
import com.esprit.pi.entities.Reservation;
import com.esprit.pi.entities.Workshop;
import com.esprit.pi.service.ReservationService;
import jakarta.annotation.Resource;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.Message;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * Implementation class of Reservation services.
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    /**
     * Internal Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ReservationServiceImpl.class);
    private static final String ERROR_NULL_ID = "Posted ID is NULL";
    private static final String ERROR_NON_PRESENT_ID = "Cannot find a reservation with id : %s";

    private static final String ERROR_UPDATE = "Error occured while updating";


    @Resource
    private ReservationDaos reservationDaos;
    private WorkshopDaos workshopDaos;
    public ReservationServiceImpl(WorkshopDaos workshopDaos) {
        this.workshopDaos = workshopDaos;
    }


    @Override
    public List<Reservation> findAll() {
        return reservationDaos.findAll();
    }

    @Override
    public Reservation findById(Long id) {
        Reservation reservation= null;
        if ( id!= null) {
            final Optional<Reservation> optionalReservation = this.reservationDaos.findById(id);
            if (optionalReservation.isPresent()) {
                reservation = optionalReservation.get();
            } else {
                LOG.info(String.format(ERROR_NON_PRESENT_ID, id));
            }
        }
        else {
            LOG.error(ERROR_NULL_ID);
        }
        return reservation;
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        Reservation updateReservation=null;
        if (reservation!= null){
            updateReservation = this.reservationDaos.save(reservation);
        } else {
            LOG.error(ERROR_UPDATE);
        }
        return updateReservation;
    }

    @Override
    public void deleteReservation(Long id) {
        reservationDaos.deleteById(id);
    }

    @Override
    public Reservation ajouterReservation(Reservation reservation, Long idWorkshop) {
        // Trouver l'atelier par son ID. Si non trouvé, retourne null.
        Workshop workshop = workshopDaos.findById(idWorkshop).orElse(null);

        if (workshop == null) {
            // Si l'atelier n'est pas trouvé, loggez une erreur et retournez null.
            LOG.error(String.format("Atelier avec l'ID %d non présent.", idWorkshop));
            return null;
        }

        // Créer une nouvelle réservation et configurer ses propriétés.
        Reservation newReservation = new Reservation();

        if(reservation.getUser_id() != null ) {  newReservation.setUser_id(reservation.getUser_id() );}

        newReservation.setWorkshop(workshop);  // Associer l'atelier à la réservation.
        newReservation.setDate(workshop.getDateDeb());  // Définir la date de la réservation à la date de début de l'atelier.
        newReservation.setNomParticipant(reservation.getNomParticipant());  // Définir le nom du participant.
        newReservation.setPrenomParticipant(reservation.getPrenomParticipant());  // Définir le prénom du participant.
        String to = "bhenda.amira@gmail.com";
        String subject = "Confirmation réservation";
        String body = "Votre réservation est passée avec succés";

        try {
            sendEmail(to, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        // Sauvegarder la nouvelle réservation dans la base de données.
        return reservationDaos.save(newReservation);
    }

    @Override
    public List<Reservation> getReservationsForWorkshop(Long idWorkshop) {
        return reservationDaos.findByWorkshop_IdWorkshop(idWorkshop);
    }

    private void sendEmail(String to, String subject, String body) throws  MessagingException {

        String from = "techwork414@gmail.com";
        String password = "pacrvzlvscatwwkb";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        //   message.setText(body);
        message.setContent(body, "text/html");

        Transport.send(message);
    }


}

