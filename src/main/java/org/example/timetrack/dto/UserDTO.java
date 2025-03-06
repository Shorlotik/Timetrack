package org.example.timetrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.timetrack.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role; // Оставим строку, если используем только одну роль
    private String password;

    // Конструктор с одной ролью как строкой
    public UserDTO(Long id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Конструктор с Role объектом
    public UserDTO(Long id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role.getName(); // Получаем имя роли
    }
}
