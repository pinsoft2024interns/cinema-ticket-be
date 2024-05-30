package com.pinsoft.interns.Entity;

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
@Table(name = "cinema", schema = "public")
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaCity;
    private String cinemaPhone;
    private String cinemaEmail;

    @OneToMany(mappedBy = "cinema")
    @JsonManagedReference
    private List<Hall> halls;

}
