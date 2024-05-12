package com.pinsoft.interns.Controller;

import com.pinsoft.interns.DTO.MovieAddDto;
import com.pinsoft.interns.DTO.MovieUpdateDto;
import com.pinsoft.interns.Entity.Movie;
import com.pinsoft.interns.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/movies")
    public boolean add(@RequestBody MovieAddDto movieAddDto){
        return movieService.add(movieAddDto);
    }

    @GetMapping("/movies")
    public List<Movie> getAll(){
        return movieService.getAll();
    }

    @PutMapping("/movies/{id}")
    public boolean update(@RequestBody MovieUpdateDto movieUpdateDto, @PathVariable Long id){
        return movieService.update(movieUpdateDto,id);
    }

    @DeleteMapping("/movies/{id}")
    public boolean delete(@PathVariable Long id){
        return movieService.delete(id);
    }

    @GetMapping("/movies/{id}")
    public Movie geById(@PathVariable Long id){
        return movieService.getById(id);
    }

}
