package org.example.timetrack.controller;

import org.example.timetrack.entity.Role;
import org.example.timetrack.entity.User;
import org.example.timetrack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.createUser(user.getUsername(), user.getPassword(), user.getRole());
        return ResponseEntity.ok("User registered successfully");
    }
}
