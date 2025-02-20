package org.example.timetrack.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "time_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;  // ID пользователя

    @Column(nullable = false)
    private Long projectId;  // ID проекта

    @Column(nullable = false)
    private LocalDateTime startTime;  // Время старта работы

    private LocalDateTime endTime;  // Время завершения (может быть null, если работа не завершена)
}
