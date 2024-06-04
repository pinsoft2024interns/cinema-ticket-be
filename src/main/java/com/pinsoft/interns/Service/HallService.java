package com.pinsoft.interns.Service;

import com.pinsoft.interns.DTO.HallRequest;
import com.pinsoft.interns.DTO.HallUpdateRequest;
import com.pinsoft.interns.Entity.Cinema;
import com.pinsoft.interns.Entity.Hall;
import com.pinsoft.interns.Entity.Showing;
import com.pinsoft.interns.Repository.HallRepository;
import com.pinsoft.interns.Repository.ShowingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallService {
    private final HallRepository hallRepository;
    private final CinemaService cinemaService;
    private final ShowingRepository showingRepository;

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

    public Hall findHallByShowing(long id) {

        Showing showing = showingRepository.findById(id).orElseThrow(null);

        if(showing == null) {
            throw new EntityNotFoundException("Showing not found");
        }

        Hall hall = showing.getHall();

        return hall;

    }
}
