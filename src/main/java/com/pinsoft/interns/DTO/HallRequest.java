package com.pinsoft.interns.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HallRequest {

    private String name;

    private int capacity;

    private String screenType;

    private Long cinemaId;
}
