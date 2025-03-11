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

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final Set<String> invalidTokens = ConcurrentHashMap.newKeySet();

    // Регулярное выражение для проверки email
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    public String authenticate(AuthDTO authDTO) {
        Optional<User> userOpt = userRepository.findByUsername(authDTO.getUsername());

        if (userOpt.isPresent() && passwordEncoder.matches(authDTO.getPassword(), userOpt.get().getPassword())) {
            return jwtUtils.generateToken(userOpt.get().getUsername());
        }

        throw new BadCredentialsException("Invalid username or password");
    }

    public UserDTO register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Проверяем email
        if (!isValidEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role userRole = roleRepository.findByName(userDTO.getRole())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(userDTO.getRole());
                    return roleRepository.save(newRole);
                });

        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), userRole.getName());
    }

    public void logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        invalidTokens.add(token);
        SecurityContextHolder.clearContext();
    }

    public boolean isTokenInvalid(String token) {
        return invalidTokens.contains(token);
    }

    // Метод проверки email
    private boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
