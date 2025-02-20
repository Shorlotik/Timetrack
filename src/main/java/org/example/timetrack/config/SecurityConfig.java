package org.example.timetrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF (нужно, если API будет RESTful)
                .cors(cors -> {}) // Разрешаем CORS (настраивается при необходимости)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/register", "/login").permitAll() // Разрешаем регистрацию и вход
                        .anyRequest().authenticated() // Остальные запросы требуют авторизации
                )
                .formLogin(login -> login
                        .loginPage("/login") // URL страницы логина (если фронтенд)
                        .defaultSuccessUrl("/", true) // Куда направлять после входа
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login") // Куда перенаправлять после выхода
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
