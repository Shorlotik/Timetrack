package org.example.timetrack;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "password456";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Оригинальный пароль: " + rawPassword);
        System.out.println("Зашифрованный пароль: " + encodedPassword);

        // Проверка совпадения пароля
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("Совпадает ли пароль: " + matches);
    }
}
