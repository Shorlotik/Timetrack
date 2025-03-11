package org.example.timetrack.controller;

import lombok.RequiredArgsConstructor;
import org.example.timetrack.dto.AuthDTO;
import org.example.timetrack.dto.UserDTO;
import org.example.timetrack.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        String token = authService.authenticate(authDTO);
        return ResponseEntity.ok("\"" + token + "\"");
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = authService.register(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(name = "Authorization", required = false) String authHeader) {
        authService.logout(authHeader);
        return ResponseEntity.ok("Logged out successfully");
    }
}
