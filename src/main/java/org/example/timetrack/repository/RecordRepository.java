package org.example.timetrack.repository;

import org.example.timetrack.entity.Record;
import org.example.timetrack.entity.User;
import org.example.timetrack.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    // Получить последнюю запись по пользователю и проекту
    Optional<Record> findTopByUserAndProjectOrderByStartTimeDesc(User user, Project project);

    // Получить все записи по пользователю
    List<Record> findByUser(User user);

    // Получить все записи по проекту
    List<Record> findByProject(Project project);

    // Получить записи, начатые в определенный период времени
    List<Record> findByStartTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Удалить все записи пользователя
    void deleteByUser(User user);
}
