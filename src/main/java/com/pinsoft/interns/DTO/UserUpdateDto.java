package com.pinsoft.interns.DTO;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Long id;
    private String email;
    private String username;
    private String password;
}