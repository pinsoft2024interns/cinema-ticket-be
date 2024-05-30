package com.pinsoft.interns.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hall", schema = "public")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int capacity;

    private String screenType;

    @ManyToOne
    @JoinColumn(name="cinema_id")
    @JsonBackReference
    private Cinema cinema;

    @OneToMany(mappedBy = "hall")
    @JsonManagedReference
    private List<Showing> showings;

}
