package com.pinsoft.interns.Controller;

import com.pinsoft.interns.DTO.CinemaRequest;
import com.pinsoft.interns.Entity.Cinema;
import com.pinsoft.interns.Entity.Hall;
import com.pinsoft.interns.Service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping("/cinemas")
    public List<Cinema> getCinemas() {
        return cinemaService.getAll();
    }

    @PostMapping("/cinema")
    public Cinema createCinema(@RequestBody CinemaRequest cinemaRequest)  {
        return cinemaService.addCinema(cinemaRequest);
    }

    @GetMapping("/cinema/{id}")
    public Cinema getCinema(@PathVariable long id) {
        return cinemaService.findCinema(id);
    }

    @PutMapping("cinema/{id}")
    public Cinema updateCinema(@PathVariable long id,@RequestBody CinemaRequest cinemaRequest) {
        return cinemaService.updateCinema(cinemaRequest , id);
    }
    @DeleteMapping("cinema/{id}")
    public void deleteCinema(@PathVariable long id)  {
        cinemaService.deleteCinema(id);
    }
    @GetMapping("/cinema/{id}/halls")
    public List<Hall> getHalls(@PathVariable long id) {
        return cinemaService.findSelectedHalls(id);
    }

    @GetMapping("/cinema/{id}/hall/{number}")
    public Hall getHall(@PathVariable long id , @PathVariable long number) {
        return cinemaService.findSelectedHall(id, number);
    }

}
