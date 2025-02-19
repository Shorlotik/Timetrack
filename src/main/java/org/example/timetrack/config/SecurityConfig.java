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
public class SecurityConfig { // Определение класса конфигурации безопасности приложения

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Отключение защиты от CSRF (стоит включить, если приложение использует сессии и браузерные запросы)
                .authorizeHttpRequests(auth -> auth  // Настройка авторизации для HTTP-запросов
                        .requestMatchers("/users/register").permitAll()  // Разрешение публичного доступа к регистрации пользователей
                        .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
                )
                .formLogin(login -> login.defaultSuccessUrl("/", true))  // Включение формы логина и перенаправление на главную страницу после входа
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"));  // Настройка выхода из системы: выход по `"/logout"` с последующим перенаправлением на `"/login"`

        return http.build();  // Возвращает готовую цепочку фильтров безопасности
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
