package org.example.timetrack.service;

import org.example.timetrack.entity.Project;
import org.example.timetrack.entity.Record;
import org.example.timetrack.entity.User;
import org.example.timetrack.repository.ProjectRepository;
import org.example.timetrack.repository.RecordRepository;
import org.example.timetrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    // Старт трекинга
    public Record startTracking(Long projectId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Record record = new Record();
        record.setStartTime(LocalDateTime.now());
        record.setUser(user);
        record.setProject(project);
        return recordRepository.save(record);
    }

    // Завершение трекинга
    public Record finishTracking(Long projectId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Получаем последнюю запись по пользователю и проекту
        Record record = recordRepository.findTopByUserAndProjectOrderByStartTimeDesc(user, project)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        LocalDateTime finishTime = LocalDateTime.now();
        record.setFinishTime(finishTime);

        // Рассчитываем продолжительность
        Duration duration = Duration.between(record.getStartTime(), finishTime);
        record.setDuration(duration);

        return recordRepository.save(record);
    }

    // Получить все записи
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    // Получить запись по ID
    public Record getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    // Получить записи определенного пользователя
    public List<Record> getRecordsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return recordRepository.findByUser(user);
    }

    // Получить записи по проекту
    public List<Record> getRecordsByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return recordRepository.findByProject(project);
    }

    // Получить записи за определенный период времени
    public List<Record> getRecordsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return recordRepository.findByStartTimeBetween(startDate, endDate);
    }

    // Обновить запись
    public Record updateRecord(Long id, Record updatedRecord) {
        Record existingRecord = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        existingRecord.setStartTime(updatedRecord.getStartTime());
        existingRecord.setFinishTime(updatedRecord.getFinishTime());
        existingRecord.setDuration(updatedRecord.getDuration());
        existingRecord.setProject(updatedRecord.getProject());

        return recordRepository.save(existingRecord);
    }

    // Удалить запись
    public void deleteRecord(Long id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        recordRepository.delete(record);
    }

    // Удалить все записи пользователя (например, при удалении аккаунта)
    public void deleteRecordsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        recordRepository.deleteByUser(user); // Используем deleteByUser напрямую
    }
}
