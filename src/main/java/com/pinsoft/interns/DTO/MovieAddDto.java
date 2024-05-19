package com.pinsoft.interns.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieAddDto {
    private Long userId;
    private String name;
    private String posterUrl;
    private String trailerUrl;
    private String description;
}
