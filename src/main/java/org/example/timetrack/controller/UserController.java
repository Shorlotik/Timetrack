package org.example.timetrack.controller;

import org.example.timetrack.entity.Role;
import org.example.timetrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password) {
        userService.createUser(username, password, Role.USER);
        return ResponseEntity.ok("User created");
    }
}
