package com.pinsoft.interns.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReservationRequest {

    private int numberOfPeople;
    private List<Integer> seatInfo;
    private float price;
    private LocalDateTime releaseDate;
    private Long userId;
    private Long movieId;
    private int hallNumber;
}
