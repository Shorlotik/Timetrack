package org.example.timetrack.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.timetrack.dto.AuthDTO;
import org.example.timetrack.dto.UserDTO;
import org.example.timetrack.entity.Role;
import org.example.timetrack.entity.User;
import org.example.timetrack.repository.RoleRepository;
import org.example.timetrack.repository.UserRepository;
import org.example.timetrack.security.JwtUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@CrossOrigin(maxAge = 3600)
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public String authenticate(AuthDTO authDTO) {
        // Ищем пользователя по имени пользователя
        Optional<User> userOpt = userRepository.findByUsername(authDTO.getUsername());

        // Если пользователь найден и пароль совпадает
        if (userOpt.isPresent() && passwordEncoder.matches(authDTO.getPassword(), userOpt.get().getPassword())) {
            return jwtUtils.generateToken(userOpt.get().getUsername());
        }

        // Если имя пользователя или пароль неверные
        throw new BadCredentialsException("Invalid username or password");
    }

    public UserDTO register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Получаем роль из репозитория по имени, переданному в DTO
        Optional<Role> optionalRole = roleRepository.findByName(userDTO.getRole());
        Role userRole;
        if (optionalRole.isPresent()) {
            userRole = optionalRole.get();
        } else {
            // Если роль не найдена, создаем новую роль
            userRole = new Role();
            userRole.setName(userDTO.getRole()); // Можно добавить логику для проверки валидных ролей
            roleRepository.save(userRole);
        }

        // Присваиваем пользователю роль
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), userRole.getName());
    }

    // Логика выхода пользователя
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
