package com.pinsoft.interns.Repository;

import com.pinsoft.interns.Entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema,Long> {
}
