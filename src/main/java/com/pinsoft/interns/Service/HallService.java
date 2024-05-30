package com.pinsoft.interns.Service;

import com.pinsoft.interns.DTO.HallRequest;
import com.pinsoft.interns.DTO.HallUpdateRequest;
import com.pinsoft.interns.Entity.Cinema;
import com.pinsoft.interns.Entity.Hall;
import com.pinsoft.interns.Repository.HallRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallService {
    private final HallRepository hallRepository;
    private final CinemaService cinemaService;

    public List<Hall> getAll() {
        return hallRepository.findAll();
    }

    public Hall addHall(HallRequest hallRequest) {
        Hall hall = new Hall();
        Cinema cinema = cinemaService.findCinema(hallRequest.getCinemaId());

        hall.setName(hallRequest.getName());
        hall.setCapacity(hallRequest.getCapacity());
        hall.setScreenType(hallRequest.getScreenType());
        hall.setCinema(cinema);

        return hallRepository.save( hall );

    }

    public Hall findHall(long id) {
        Hall hall =  hallRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hall not found"));
        return hall;
    }

    public Hall updateHall(HallUpdateRequest hallUpdateRequest , long id) {
        Hall hall = findHall(id);

        hall.setName(hallUpdateRequest.getName());
        hall.setCapacity(hallUpdateRequest.getCapacity());
        hall.setScreenType(hallUpdateRequest.getScreenType());
        return hallRepository.save( hall );
    }

    public void deleteHall(long id) {
        Hall hall = findHall(id);
        hallRepository.delete( hall );
    }
}
