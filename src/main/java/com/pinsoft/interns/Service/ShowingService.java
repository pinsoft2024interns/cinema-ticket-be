package com.pinsoft.interns.Service;

import com.pinsoft.interns.DTO.ShowingRequest;
import com.pinsoft.interns.Entity.Hall;
import com.pinsoft.interns.Entity.Movie;
import com.pinsoft.interns.Entity.Showing;
import com.pinsoft.interns.Repository.ShowingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowingService {

    private final ShowingRepository showingRepository;
    private final HallService hallService;
    private final MovieService movieService;

    public List<Showing> findAll() {
        return showingRepository.findAll();
    }

    public Showing addShowing(ShowingRequest showingRequest) {
        Showing showing = new Showing();
        return getShowing(showingRequest, showing);

    }

    public Showing findShowing(long id) {
        Showing showing =  showingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Showing not found"));
        return showing;
    }

    public Showing updateShowing(ShowingRequest showingRequest , long id) {
        Showing showing = findShowing(id);
        return getShowing(showingRequest, showing);
    }

    private Showing getShowing(ShowingRequest showingRequest, Showing showing) {
        Movie movie = movieService.getById(showingRequest.getMovieId());
        Hall hall = hallService.findHall(showingRequest.getHallId());

        showing.setMovie(movie);
        showing.setHall(hall);
        showing.setShowtime(showingRequest.getShowtime());
        showing.setTicketPrice(showingRequest.getTicketPrice());

        return showingRepository.save( showing );
    }

    public void deleteShowing(long id) {
        Showing showing = findShowing(id);
        showingRepository.delete( showing );
    }

    public List<Showing> findShowingByMovie(long id) {
        Movie movie = movieService.getById(id);
        return showingRepository.findShowingByMovie(movie);
    }

    public List<Showing> findShowingByHall(long id) {
        Hall hall = hallService.findHall(id);
        return showingRepository.findShowingByHall(hall);
    }

    public List<Showing> findShowingByHallAndMovie( long movieId,long hallId) {

        List<Showing> showings =  findShowingByMovie(movieId);

        List<Showing> foundShowings = showings.stream().filter(showing -> showing.getHall().getId()== hallId).toList();

        return foundShowings;
    }

}
