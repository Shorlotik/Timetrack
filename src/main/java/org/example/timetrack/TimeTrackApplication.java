package org.example.timetrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.timetrack.repository")
public class TimeTrackApplication {

    public static void main(String[] args) {
        // Загружаем переменные из .env файла
        Dotenv dotenv = Dotenv.load();

        // Устанавливаем переменные окружения
        String dbPassword = dotenv.get("SPRING_DATASOURCE_PASSWORD");
        String jwtSecret = dotenv.get("JWT_SECRET");

        if (dbPassword == null || jwtSecret == null) {
            throw new IllegalStateException("Required environment variables not found");
        }

        System.setProperty("SPRING_DATASOURCE_PASSWORD", dbPassword);
        System.setProperty("JWT_SECRET", jwtSecret);

        SpringApplication.run(TimeTrackApplication.class, args);
    }
}
