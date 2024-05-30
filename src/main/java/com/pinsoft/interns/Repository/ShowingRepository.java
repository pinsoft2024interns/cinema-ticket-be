package com.pinsoft.interns.Repository;

import com.pinsoft.interns.Entity.Hall;
import com.pinsoft.interns.Entity.Movie;
import com.pinsoft.interns.Entity.Role;
import com.pinsoft.interns.Entity.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowingRepository extends JpaRepository<Showing,Long> {

    List<Showing> findShowingByMovie(Movie movie);
    List<Showing> findShowingByHall(Hall hall);

}
