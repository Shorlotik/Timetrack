package org.example.timetrack.controller;

import org.example.timetrack.dto.AuthRequestDTO;
import org.example.timetrack.dto.AuthResponseDTO;
import org.example.timetrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        String token = userService.authenticate(authRequestDTO.getUsername(), authRequestDTO.getPassword());
        return ResponseEntity.ok(AuthResponseDTO.builder().token(token).build());
    }
}
