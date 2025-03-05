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
    private String role;
    private String password;


    public UserDTO(Long id, String username, String email, String s) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = s;
    }

    public UserDTO(Long id, String username, String email, Role next) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = next.getName();
    }
}

