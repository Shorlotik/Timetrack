package org.example.timetrack.controller;

import org.example.timetrack.entity.User;
import org.example.timetrack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {  // Определение контроллера для работы с пользователями

    private final UserService userService;  // Сервис для управления пользователями (инициализируется через конструктор)

    @PostMapping("/register")  // Обработчик POST-запросов на "/users/register" (регистрация нового пользователя)
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.createUser(user.getUsername(), user.getPassword(), user.getRole());  // Вызывает сервис для создания пользователя
        return ResponseEntity.ok("User registered successfully");  // Возвращает сообщение об успешной регистрации с HTTP-статусом 200 (OK)
    }
}
