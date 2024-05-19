package com.pinsoft.interns.Repository;

import com.pinsoft.interns.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
