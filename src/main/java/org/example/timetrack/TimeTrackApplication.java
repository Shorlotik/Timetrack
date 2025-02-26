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
        System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));

        SpringApplication.run(TimeTrackApplication.class, args);
    }
}
