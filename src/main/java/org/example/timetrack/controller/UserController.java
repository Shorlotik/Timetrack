package org.example.timetrack.controller;

import lombok.RequiredArgsConstructor;
import org.example.timetrack.dto.UserDTO;
import org.example.timetrack.service.AuthService;
import org.example.timetrack.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    // CREATE (Регистрация пользователя)
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(createdUser);
    }

    // READ ONE (Получить пользователя по ID)
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    // READ ALL (Получить всех пользователей)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // UPDATE (Обновить пользователя)
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE (Удалить пользователя)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // LOGIN (Аутентификация пользователя)
    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestParam String username, @RequestParam String password) {
        String token = authService.authenticate(username, password);
        return ResponseEntity.ok(token);
    }

    // FIND BY USERNAME (Поиск пользователя по имени)
    @GetMapping("/find")
    public ResponseEntity<UserDTO> findUserByUsername(@RequestParam String username) {
        Optional<UserDTO> userDTO = userService.findByUsername(username);
        return userDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
