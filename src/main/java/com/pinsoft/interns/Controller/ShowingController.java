package com.pinsoft.interns.Controller;

import com.pinsoft.interns.DTO.ShowingRequest;
import com.pinsoft.interns.Entity.Showing;
import com.pinsoft.interns.Service.ShowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShowingController {
    private final ShowingService showingService;


    @GetMapping("/showings")
    public List<Showing> getAllShowings() {
        return showingService.findAll();
    }

    @PostMapping("/showing")
    public Showing createShowing(@RequestBody ShowingRequest showingRequest) {
        return showingService.addShowing(showingRequest);
    }

    @GetMapping("/showing/{id}")
    public Showing getShowing(@PathVariable long id) {
        return showingService.findShowing(id);
    }

    @PutMapping("/showing/{id}")
    public Showing updateShowing(@PathVariable long id, @RequestBody ShowingRequest showingRequest) {
        return showingService.updateShowing(showingRequest , id);
    }

    @DeleteMapping("/showing/{id}")
    public void deleteShowing(@PathVariable long id) {
        showingService.deleteShowing(id);
    }

    @GetMapping("/showing/movie/{id}")
    public List<Showing> getMovieShowing(@PathVariable long id) {
        return showingService.findShowingByMovie(id);
    }
    @GetMapping("/showing/hall/{id}")
    public List<Showing> getHallShowing(@PathVariable long id) {
        return showingService.findShowingByHall(id);
    }
    @GetMapping("/showing/movie/{id}/hall{number}")
    public List<Showing> getMovieShowing(@PathVariable long id, @PathVariable long number) {
        return showingService.findShowingByHallAndMovie(id,number);
    }
}
