package com.pinsoft.interns.Controller;

import com.pinsoft.interns.DTO.ReservationRequest;
import com.pinsoft.interns.Entity.Hall;
import com.pinsoft.interns.Entity.Movie;
import com.pinsoft.interns.Entity.Reservation;
import com.pinsoft.interns.Entity.Showing;
import com.pinsoft.interns.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return reservationService.findAllReservations();
    }

    @GetMapping("/reservation/{id}")
    public Reservation getReservation(@PathVariable long id) {
    return reservationService.findReservation(id);
    }

    @PostMapping("/reservation")
    public Reservation createReservation(@RequestBody ReservationRequest reservationRequest) throws Exception {
        return reservationService.addReservation(reservationRequest);
    }

    @PutMapping("reservation/cancel/{id}")
    public Reservation cancelReservation(@PathVariable long id) throws Exception {
        return reservationService.cancelReservation(id);
    }

    @DeleteMapping("reservation/{id}")
    public void deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
    }


    @DeleteMapping("reservation/cancel/{id}/{isApproved}")
    public void reservationCancelConfirm( @PathVariable long id, @PathVariable boolean isApproved)  {
        reservationService.reservationCancelConfirm(id, isApproved);
    }

    @GetMapping("reservation/shwoing/{id}")
    public Showing getShowing(@PathVariable long id) {
        return reservationService.findShowing(id);
    }

    @GetMapping("reservation/{id}/movie")
    public Movie getMovie (@PathVariable long id) {
        return reservationService.findMovie(id);
    }

    @GetMapping("reservation/{id}/hall")
    public Hall getHall (@PathVariable long id) {
        return reservationService.findHall(id);
    }

}
