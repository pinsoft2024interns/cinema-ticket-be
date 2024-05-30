package com.pinsoft.interns.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HallUpdateRequest {
    private String name;
    private int capacity;
    private String screenType;
}
