package com.pinsoft.interns.Controller;

import com.pinsoft.interns.DTO.ReservationCancelRequest;
import com.pinsoft.interns.DTO.ReservationRequest;
import com.pinsoft.interns.DTO.ReservationUpdateRequest;
import com.pinsoft.interns.Entity.Reservation;
import com.pinsoft.interns.Exception.ReservationCancellationException;
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

    @PutMapping("reservation/cancel")
    public Reservation cancelReservation(@RequestBody ReservationUpdateRequest reservationUpdateRequest) throws Exception {
        return reservationService.cancelReservation(reservationUpdateRequest);
    }

    @DeleteMapping("reservation/cancel/{id}")
    public void cancelReservation(@RequestBody ReservationCancelRequest reservationCancelRequest, @PathVariable long id) throws Exception {
        reservationService.deleteReservation(reservationCancelRequest,id);
    }

}
