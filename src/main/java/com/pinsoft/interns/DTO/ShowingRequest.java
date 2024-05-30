package com.pinsoft.interns.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShowingRequest {

    private LocalDateTime showtime;

    private double ticketPrice;

    private Long movieId;

    private Long hallId;

}
