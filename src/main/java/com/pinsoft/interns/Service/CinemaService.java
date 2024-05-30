package com.pinsoft.interns.Service;

import com.pinsoft.interns.DTO.CinemaRequest;
import com.pinsoft.interns.Entity.Cinema;
import com.pinsoft.interns.Entity.Hall;
import com.pinsoft.interns.Repository.CinemaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;

    public List<Cinema> getAll() {
        return cinemaRepository.findAll();
    }

    public Cinema addCinema(CinemaRequest cinemaRequest) {
        Cinema cinema = new Cinema();
        cinema.setCinemaName( cinemaRequest.getCinemaName() );
        cinema.setCinemaAddress( cinemaRequest.getCinemaAddress() );
        cinema.setCinemaCity( cinemaRequest.getCinemaCity() );
        cinema.setCinemaPhone(cinemaRequest.getCinemaPhone() );
        cinema.setCinemaEmail( cinemaRequest.getCinemaEmail() );

        return cinemaRepository.save( cinema );

    }

    public Cinema findCinema(long id) {
        Cinema cinema =  cinemaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cinema not found"));
        return cinema;
    }

    public Cinema updateCinema(CinemaRequest cinemaRequest , long id) {
        Cinema cinema = findCinema(id);
        cinema.setCinemaName( cinemaRequest.getCinemaName() );
        cinema.setCinemaAddress( cinemaRequest.getCinemaAddress() );
        cinema.setCinemaCity( cinemaRequest.getCinemaCity() );
        cinema.setCinemaPhone( cinemaRequest.getCinemaPhone() );
        cinema.setCinemaEmail( cinemaRequest.getCinemaEmail() );
        return cinemaRepository.save( cinema );
    }

    public void deleteCinema(long id) {
        Cinema cinema = findCinema(id);
        cinemaRepository.delete( cinema );
    }

    public List<Hall> findSelectedHalls(long id) {
        Cinema cinema = findCinema(id);
        List<Hall> halls = cinema.getHalls();
        return halls;

    }

    public Hall findSelectedHall(long id , long hallId) {
        List<Hall> halls = findSelectedHalls(id);
        Hall hall = halls.stream().filter(h -> h.getId() == hallId).findFirst().orElseThrow(() -> new EntityNotFoundException("Hall not found"));
        return hall;
    }
}
