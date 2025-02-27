package org.example.timetrack.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.timetrack.security.JwtUtils;
import org.example.timetrack.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    @Getter
    private final BlacklistTokenService blacklistTokenService;

    public String authenticate(String username, String password) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtils.generateToken(user.getUsername());
    }

}
