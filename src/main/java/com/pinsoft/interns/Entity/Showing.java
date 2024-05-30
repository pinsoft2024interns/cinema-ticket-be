package com.pinsoft.interns.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "showing", schema = "public")
public class Showing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime showtime;

    private double ticketPrice;

    @ManyToOne
    @JoinColumn(name="movie_id")
    @JsonBackReference
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="hall_id")
    @JsonBackReference
    private Hall hall;


    @OneToMany(mappedBy = "showing")
    @JsonManagedReference
    private List<Reservation> reservations;
}
