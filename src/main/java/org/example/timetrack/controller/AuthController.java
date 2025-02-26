package org.example.timetrack.controller;

import org.example.timetrack.dto.UserDTO;
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
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        String token = userService.authenticate(userDTO.getUsername(), userDTO.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);  // Изменено с register на createUser
        return ResponseEntity.ok(createdUser);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        if (token != null && !token.isEmpty()) {
            userService.logout(token);  // Blacklist the token
            return ResponseEntity.ok("Successfully logged out");
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }
}
