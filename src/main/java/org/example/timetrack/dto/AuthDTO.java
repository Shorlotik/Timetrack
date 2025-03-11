package org.example.timetrack.dto;

import lombok.Data;

@Data
public class AuthDTO {
    private String username;
    private String password;
    private String token;
}