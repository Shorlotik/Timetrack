package org.example.timetrack.service;

import lombok.RequiredArgsConstructor;
import org.example.timetrack.dto.UserDTO;
import org.example.timetrack.entity.User;
import org.example.timetrack.entity.Role;
import org.example.timetrack.repository.UserRepository;
import org.example.timetrack.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("User with this username already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Устанавливаем роль (например, "USER")
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(userRole));

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);

        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), userRole); // Возвращаем роль как часть DTO
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRoles().iterator().next().getName()  // Получаем имя первой роли
                ))
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRoles().iterator().next().getName()  // Получаем имя первой роли
                ));
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().iterator().next().getName()  // Получаем имя первой роли
        );
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Обновляем роль
        Role userRole = roleRepository.findByName(userDTO.getRole()).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(userRole));

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(user);

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().iterator().next().getName()  // Получаем имя первой роли
        );
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}
