package com.pinsoft.interns.Service;

import com.pinsoft.interns.DTO.ReservationRequest;
import com.pinsoft.interns.Entity.*;
import com.pinsoft.interns.Exception.MatchException;
import com.pinsoft.interns.Exception.ReservationCancellationException;
import com.pinsoft.interns.Exception.SeatFullException;
import com.pinsoft.interns.Repository.ReservationRepository;
import com.pinsoft.interns.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ShowingService showingService;

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation findReservation( long id) {
        Reservation reservation =  reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        return reservation;
    }
    public List<Reservation> findReservationsByShowing(long id) {
        Showing showing = showingService.findShowing(id);
        return showing.getReservations();
    }

    public Reservation addReservation(ReservationRequest reservationRequest) throws Exception {

        User user = userRepository.findById(reservationRequest.getUserId()).orElseThrow(()-> new  EntityNotFoundException("User not found"));
        Showing showing = showingService.findShowing(reservationRequest.getShowingId());

        if (reservationRequest.getSeatInfo().size() != reservationRequest.getNumberOfPeople()) {
            throw new MatchException("Number of people and number of seats do not match!");
        }

        List<Reservation> foundReservations = findReservationsByShowing(showing.getId());

        if (!foundReservations.isEmpty()) {
            for(int seatNumber:reservationRequest.getSeatInfo()) {
                Reservation foundReservation = foundReservations.stream().filter(element -> element.getSeatInfo().contains(seatNumber)).findFirst().orElse(null);
                if (foundReservation != null) {
                    throw new SeatFullException("One of the seats you selected is occupied:" + seatNumber);
                }
            }
        }

            Reservation reservation = new Reservation();
            reservation.setNumberOfPeople(reservationRequest.getNumberOfPeople());
            reservation.setSeatInfo(reservationRequest.getSeatInfo());
            reservation.setUser(user);
            reservation.setShowing(showing);

            return reservationRepository.save(reservation);
        }


    public Reservation cancelReservation(long id) throws Exception {

        Reservation reservation  = findReservation(id);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime releaseDate = reservation.getShowing().getShowtime();
        Duration duration = Duration.between(now,releaseDate );
        long remainingMinutes = duration.toMinutes();

        if (remainingMinutes < 60){
            throw new ReservationCancellationException("You must cancel your ticket at least one hour in advance. Your ticket cannot be cancelled!");
        }
        reservation.setCancel(true);

        User admin = userRepository.findAll().stream().filter(element -> element.getRole().getId() == 1).findFirst().orElseThrow(()-> new  EntityNotFoundException("Admin not found"));

        emailService.sendSimpleMessage(admin.getEmail(), "Reservation Cancellation", "The user canceled the reservation.Reservation id: " + reservation.getId());

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(long id) {
        Reservation reservation = findReservation(id);

        reservationRepository.delete(reservation);
    }

    public void reservationCancelConfirm(long id, boolean isApproved) {
        Reservation reservation = findReservation(id);

        if (!isApproved) {
            emailService.sendSimpleMessage(reservation.getUser().getEmail(), "Reservation Cancellation",
                    "Your cancellation request for your reservation has not been approved.");
        }
        else{
            deleteReservation(id);
            emailService.sendSimpleMessage(reservation.getUser().getEmail(), "Reservation Cancellation",
                    "Your cancellation request for your reservation has been approved.");
        }

    }

    public Movie findMovie(long id) {
        Movie movie = findReservation(id).getShowing().getMovie();
        return movie;
    }
    public Hall findHall(long id){
        Hall hall = findReservation(id).getShowing().getHall();
        return hall;
    }
    public Showing findShowing( long id) {
        Showing showing = findReservation(id).getShowing();
        return showing;
    }
}
