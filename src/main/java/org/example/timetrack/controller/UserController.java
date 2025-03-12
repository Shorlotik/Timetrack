package org.example.timetrack.controller;

import lombok.RequiredArgsConstructor;
import org.example.timetrack.dto.UserDTO;
import org.example.timetrack.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // CREATE (Регистрация пользователя)
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO createdUser = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // READ ONE (Получить пользователя по ID)
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // READ ALL (Получить всех пользователей)
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users.isEmpty() ? List.of() : users); // Пустой список если нет пользователей
    }

    // UPDATE (Обновить пользователя)
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((UserDTO) Map.of("error", e.getMessage()));
        }
    }

    // DELETE (Удалить пользователя)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // FIND BY USERNAME (Поиск пользователя по имени)
    @GetMapping("/find")
    public ResponseEntity<UserDTO> findUserByUsername(@RequestParam String username) {
        Optional<UserDTO> userDTO = userService.findByUsername(username);
        return userDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
