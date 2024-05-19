package com.pinsoft.interns.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movie-table", schema = "public")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "posterUrl")
    private String posterUrl;

    @Column(name = "trailerUrl")
    private String trailerUrl;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private List<Reservation> reservations;
}
