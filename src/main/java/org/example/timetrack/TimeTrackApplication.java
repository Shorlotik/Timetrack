package org.example.timetrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.timetrack.repository")
public class TimeTrackApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimeTrackApplication.class, args);
    }
}
