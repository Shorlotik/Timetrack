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

    public Record startTracking(Long projectId) {
        User user = userRepository.findByUsername("username")
                .orElseThrow(() -> new RuntimeException("User not found")); // Обрабатываем Optional

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Record record = new Record();
        record.setStartTime(LocalDateTime.now());
        record.setUser(user);
        record.setProject(project);
        return recordRepository.save(record);
    }

    public Record finishTracking(Long projectId) {
        User user = userRepository.findByUsername("username")
                .orElseThrow(() -> new RuntimeException("User not found")); // Обрабатываем Optional

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Record record = recordRepository.findTopByUserAndProjectOrderByStartTimeDesc(user, project)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        LocalDateTime finishTime = LocalDateTime.now();
        record.setFinishTime(finishTime);

        // Правильное вычисление Duration
        Duration duration = Duration.between(record.getStartTime(), finishTime);
        record.setDuration(duration);

        return recordRepository.save(record);
    }

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }
}
