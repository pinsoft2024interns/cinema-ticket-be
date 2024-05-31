package com.pinsoft.interns.Repository;

import com.pinsoft.interns.Entity.Hall;
import com.pinsoft.interns.Entity.Payment;
import com.pinsoft.interns.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findPaymentByReservation(Reservation reservation);
}