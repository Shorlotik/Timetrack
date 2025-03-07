package org.example.timetrack;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.Base64;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Генерация JWT_SECRET
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jwtSecret = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("Сгенерированный JWT_SECRET: " + jwtSecret);

        // Хеширование пароля
        String rawPassword = "password456";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Оригинальный пароль: " + rawPassword);
        System.out.println("Зашифрованный пароль: " + encodedPassword);

        // Проверка совпадения пароля
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("Совпадает ли пароль: " + matches);
    }
}
