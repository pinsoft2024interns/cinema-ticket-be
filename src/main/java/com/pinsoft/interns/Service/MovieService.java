package com.pinsoft.interns.Service;

import com.pinsoft.interns.DTO.MovieAddDto;
import com.pinsoft.interns.DTO.MovieUpdateDto;
import com.pinsoft.interns.Entity.Movie;
import com.pinsoft.interns.Repository.MovieRepository;
import com.pinsoft.interns.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private UserRepository userRepository;

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public boolean add(MovieAddDto movieAddDto){
        try {
            if(userRepository.findById(movieAddDto.getUserId()).get().getAuthorities().toArray()[0].toString().equals("admin")){
                Movie movie = new Movie();
                movie.setName(movieAddDto.getName());
                movie.setDescription(movieAddDto.getDescription());
                movie.setBase64image(movieAddDto.getBase64image());
                movieRepository.save(movie);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public List<Movie> getAll(){
        return movieRepository.findAll();
    }

    public boolean update(MovieUpdateDto movieUpdateDto, Long id){
        try {
            if(userRepository.findById(movieUpdateDto.getUserId()).get().getAuthorities().toArray()[0].toString().equals("admin")){
                Movie movie = movieRepository.findById(id).get();
                movie.setName(movieUpdateDto.getName());
                movie.setDescription(movieUpdateDto.getDescription());
                movie.setBase64image(movieUpdateDto.getBase64image());
                movieRepository.save(movie);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean delete(Long id){
        try {
            movieRepository.delete(movieRepository.findById(id).get());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Movie getById(Long id){
        return movieRepository.findById(id).get();
    }
}
