package com.pinsoft.interns.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pinsoft.interns.Entity.Hall;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CinemaRequest {

    private String cinemaName;
    private String cinemaAddress;
    private String cinemaCity;
    private String cinemaPhone;
    private String cinemaEmail;

}
