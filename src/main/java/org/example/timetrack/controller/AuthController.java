package org.example.timetrack.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.timetrack.dto.UserDTO;
import org.example.timetrack.service.AuthService;
import org.example.timetrack.service.BlacklistTokenService;
import org.example.timetrack.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final BlacklistTokenService blacklistTokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        String token = authService.authenticate(userDTO.getUsername(), userDTO.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Извлекаем токен из заголовка
            blacklistTokenService.blacklistToken(token); // Черный список токенов
            return ResponseEntity.ok("Logged out successfully");
        }

        return ResponseEntity.badRequest().body("No token found");
    }
}
