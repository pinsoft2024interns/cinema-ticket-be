package com.pinsoft.interns.Service;

import com.pinsoft.interns.DTO.ReservationCancelRequest;
import com.pinsoft.interns.DTO.ReservationRequest;
import com.pinsoft.interns.DTO.ReservationUpdateRequest;
import com.pinsoft.interns.Entity.Movie;
import com.pinsoft.interns.Entity.Reservation;
import com.pinsoft.interns.Entity.User;
import com.pinsoft.interns.Exception.MatchException;
import com.pinsoft.interns.Exception.ReservationCancellationException;
import com.pinsoft.interns.Exception.SeatFullException;
import com.pinsoft.interns.Repository.MovieRepository;
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
    private final MovieRepository movieRepository;
    private final EmailService emailService;

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation findReservation( long id) {
        Reservation reservation =  reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        return reservation;
    }

    public Reservation addReservation(ReservationRequest reservationRequest) throws Exception {

        User user = userRepository.findById(reservationRequest.getUserId()).orElseThrow(()-> new  EntityNotFoundException("User not found"));
        Movie movie = movieRepository.findById(reservationRequest.getMovieId()).orElseThrow(()-> new  EntityNotFoundException("Movie not found"));

        if (reservationRequest.getSeatInfo().size() != reservationRequest.getNumberOfPeople()) {
            throw new MatchException("Number of people and number of seats do not match!");
        }

        List<Reservation> foundReservations = reservationRepository.findAll().stream().filter(
                element -> element.getMovie().equals(movie) && element.getReleaseDate().equals(reservationRequest.getReleaseDate()) &&
                            element.getHallNumber() == reservationRequest.getHallNumber()).toList();

        if (!foundReservations.isEmpty()) {
            for(int seatNumber:reservationRequest.getSeatInfo()) {
                Reservation foundReservation = foundReservations.stream().filter(element -> element.getSeatInfo().contains(seatNumber)).findFirst().orElse(null);
                if (foundReservation != null) {
                    throw new SeatFullException("One of the seats you selected is occupied:" + seatNumber);
                }
            }
        }

            Reservation reservation = new Reservation();
            reservation.setPrice(reservationRequest.getPrice());
            reservation.setNumberOfPeople(reservationRequest.getNumberOfPeople());
            reservation.setSeatInfo(reservationRequest.getSeatInfo());
            reservation.setReleaseDate(reservationRequest.getReleaseDate());
            reservation.setHallNumber(reservationRequest.getHallNumber());
            reservation.setUser(user);
            reservation.setMovie(movie);

            return reservationRepository.save(reservation);
        }


    public Reservation cancelReservation(ReservationUpdateRequest reservationUpdateRequest) throws Exception {

        Reservation reservation  = reservationRepository.findById(reservationUpdateRequest.getId()).orElseThrow(()-> new EntityNotFoundException("Reservation not found!"));
        User user = userRepository.findById(reservationUpdateRequest.getUserId()).orElseThrow(()-> new  EntityNotFoundException("User not found"));
        Movie movie = movieRepository.findById(reservationUpdateRequest.getMovieId()).orElseThrow(()-> new  EntityNotFoundException("Movie not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime releaseDate = reservation.getReleaseDate();
        Duration duration = Duration.between(now,releaseDate );
        long remainingMinutes = duration.toMinutes();


        if (user.getId() != reservation.getUser().getId() || movie.getId() != reservation.getMovie().getId()) {
            throw new MatchException("User or movie id does not match!");
        }
        else if (remainingMinutes < 60){
            throw new ReservationCancellationException("You must cancel your ticket at least one hour in advance. Your ticket cannot be cancelled!");
        }
        reservation.setCancel(true);

        User admin = userRepository.findAll().stream().filter(element -> element.getRole().getId() == 1).findFirst().orElseThrow(()-> new  EntityNotFoundException("Admin not found"));

        emailService.sendSimpleMessage(admin.getEmail(), "Reservation Cancellation", "The user canceled the reservation.Reservation id: " + reservation.getId());

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(ReservationCancelRequest reservationCancelRequest,long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(()-> new  EntityNotFoundException("Reservation not found"));

        if (reservationCancelRequest.isApproved() == false) {
            emailService.sendSimpleMessage(reservation.getUser().getEmail(), "Reservation Cancellation",
                    "Your cancellation request for your reservation has not been approved.");
        }
        else{
            reservationRepository.delete(reservation);
            emailService.sendSimpleMessage(reservation.getUser().getEmail(), "Reservation Cancellation",
                    "Your cancellation request for your reservation has been approved.");
        }

    }
}
