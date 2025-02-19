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
    private Long userId;  // ID пользователя, который трекал время

    @Column(nullable = false)
    private Long projectId;  // ID проекта, к которому относится запись

    @Column(nullable = false)
    private LocalDateTime startTime;  // Время начала работы

    @Column(nullable = false)
    private LocalDateTime endTime;  // Время окончания работы
}
