package org.example.timetrack.service;

import lombok.RequiredArgsConstructor;
import org.example.timetrack.dto.AuthDTO;
import org.example.timetrack.dto.UserDTO;
import org.example.timetrack.entity.Role;
import org.example.timetrack.entity.User;
import org.example.timetrack.repository.RoleRepository;
import org.example.timetrack.repository.UserRepository;
import org.example.timetrack.security.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final Set<String> invalidTokens = Collections.synchronizedSet(new HashSet<>());
    private static final String TOKEN_KEY = "token";

    // Аутентификация (возвращает Map с токеном)
    public Map<String, String> authenticate(AuthDTO authDTO) {
        Optional<User> userOpt = userRepository.findByUsername(authDTO.getUsername());

        if (userOpt.isEmpty() || !passwordEncoder.matches(authDTO.getPassword(), userOpt.get().getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtUtils.generateToken(userOpt.get().getUsername());
        return Collections.singletonMap(TOKEN_KEY, token);
    }

    // Регистрация пользователя
    public UserDTO register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (!isValidEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role userRole = roleRepository.findByName(userDTO.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(userRole);
        userRepository.save(user);

        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), userRole.getName(), user.getPassword());
    }

    // Логаут пользователя
    public UserDTO logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token is missing or invalid.");
        }

        String token = authHeader.substring(7);
        if (isTokenInvalid(token)) {
            throw new IllegalArgumentException("This token is already invalidated");
        }

        invalidTokens.add(token); // Добавляем токен в список недействительных
        SecurityContextHolder.clearContext();

        User user = userRepository.findByUsername(jwtUtils.extractUsername(token))
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRoles().iterator().next().getName(), user.getPassword());
    }

    // Проверка на недействительный токен
    public boolean isTokenInvalid(String token) {
        return invalidTokens.contains(token);
    }

    // Проверка формата email
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    }
}
