package org.example.timetrack.controller;

import lombok.RequiredArgsConstructor;
import org.example.timetrack.dto.AuthDTO;
import org.example.timetrack.dto.UserDTO;
import org.example.timetrack.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Логин (возвращает объект с токеном)
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(authService.authenticate(authDTO));
    }

    // Регистрация
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        try {
            UserDTO registeredUser = authService.register(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Логаут
    @PostMapping("/logout")
    public ResponseEntity<UserDTO> logout(@RequestHeader(name = "Authorization", required = false) String authHeader) {
        try {
            UserDTO userDTO = authService.logout(authHeader);
            return ResponseEntity.ok(userDTO);  // Возвращаем объект пользователя
        } catch (IllegalArgumentException e) {
            // Обработка ошибки, если токен некорректен
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
