package org.example.timetrack.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.timetrack.dto.AuthDTO;
import org.example.timetrack.dto.UserDTO;
import org.example.timetrack.entity.User;
import org.example.timetrack.repository.UserRepository;
import org.example.timetrack.security.JwtUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public String authenticate(AuthDTO authDTO) {
        Optional<User> userOpt = userRepository.findByUsername(authDTO.getUsername());

        if (userOpt.isPresent() && passwordEncoder.matches(authDTO.getPassword(), userOpt.get().getPassword())) {
            return jwtUtils.generateToken(userOpt.get().getUsername());
        }
        throw new RuntimeException("Invalid username or password");
    }

    public UserDTO register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode("default_password"));
        user.setRole("USER");

        userRepository.save(user);
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

    public void logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext(); // Очищаем сессию пользователя
    }
}
