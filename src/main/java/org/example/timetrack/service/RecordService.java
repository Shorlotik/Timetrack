package org.example.timetrack.service;

import org.example.timetrack.dto.RecordDTO;
import org.example.timetrack.entity.Project;
import org.example.timetrack.entity.Record;
import org.example.timetrack.entity.User;
import org.example.timetrack.repository.ProjectRepository;
import org.example.timetrack.repository.RecordRepository;
import org.example.timetrack.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public RecordService(RecordRepository recordRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

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

    public Record finishTracking(Long projectId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Record record = recordRepository.findTopByUserAndProjectOrderByStartTimeDesc(user, project)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        LocalDateTime finishTime = LocalDateTime.now();
        record.setFinishTime(finishTime);

        Duration duration = Duration.between(record.getStartTime(), finishTime);
        record.setDuration(duration);

        // Сохраняем обновленную запись
        return recordRepository.save(record);
    }

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public List<Record> getRecordsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return recordRepository.findByUser(user);
    }

    public List<Record> getRecordsByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return recordRepository.findByProject(project);
    }

    public List<Record> getRecordsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return recordRepository.findByStartTimeBetween(startDate, endDate);
    }

    public Record updateRecord(Long id, RecordDTO updatedRecordDTO) {
        Record existingRecord = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        existingRecord.setStartTime(updatedRecordDTO.getStartTime());
        existingRecord.setFinishTime(updatedRecordDTO.getFinishTime());
        existingRecord.setDuration(Duration.ofDays(updatedRecordDTO.getDuration()));

        Project project = projectRepository.findById(updatedRecordDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        existingRecord.setProject(project);

        // Сохраняем обновленную запись
        return recordRepository.save(existingRecord);
    }

    public void deleteRecord(Long id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        recordRepository.delete(record);
    }

    public void deleteRecordsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        recordRepository.deleteByUser(user);
    }
}
