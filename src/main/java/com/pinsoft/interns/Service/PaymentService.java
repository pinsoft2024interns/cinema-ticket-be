package com.pinsoft.interns.Service;

import com.pinsoft.interns.DTO.PaymentRequest;
import com.pinsoft.interns.Entity.CardInfo;
import com.pinsoft.interns.Entity.Payment;
import com.pinsoft.interns.Entity.Reservation;
import com.pinsoft.interns.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final CardInfoService cardInfoService;
    private final ReservationService reservationService;
    private final PaymentRepository paymentRepository;

    public void pay(PaymentRequest paymentRequest) throws Exception {

        CardInfo cardInfo = cardInfoService.findCard(paymentRequest.getCardInfoId());
        Reservation reservation = reservationService.findReservation(paymentRequest.getReservationId());
        double amount = reservation.getShowing().getTicketPrice() * reservation.getNumberOfPeople();
        LocalDateTime showtime = reservation.getShowing().getShowtime();


        if (findPaymentByReservation(reservation) != null) {
            throw new Exception("payment already present");
        }
        else if (showtime.isBefore(LocalDateTime.now())) {

            throw new Exception("The showtime has passed. Payment cannot be made.");
        }
        else {
            Payment payment = new Payment();
            payment.setReservation(reservation);
            payment.setCardInfo(cardInfo);
            payment.setAmount(amount);
            payment.setPaymentDate(LocalDateTime.now());
            paymentRepository.save(payment);
            System.out.println("payment successful");
        }
    }

    public Payment findPaymentByReservation(Reservation reservation) {

        Payment payment = paymentRepository.findPaymentByReservation(reservation).orElse(null);

        return payment;
    }


    public Payment findpay(long id) {
        Reservation reservation = reservationService.findReservation(id);
        Payment payment = findPaymentByReservation(reservation);
        return payment;

    }
}
