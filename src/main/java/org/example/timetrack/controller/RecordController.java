package org.example.timetrack.controller;

import org.example.timetrack.dto.RecordDTO;
import org.example.timetrack.entity.Record;
import org.example.timetrack.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    // Начать запись времени
    @PostMapping("/start")
    public ResponseEntity<Record> startTracking(@RequestParam Long projectId, @RequestParam String username) {
        Record record = recordService.startTracking(projectId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }

    // Завершить запись времени
    @PostMapping("/finish")
    public ResponseEntity<Record> finishTracking(@RequestParam Long projectId, @RequestParam String username) {
        Record record = recordService.finishTracking(projectId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(record);
    }

    // Получить все записи
    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords() {
        List<Record> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }

    // Получить записи определенного пользователя
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Record>> getRecordsByUser(@PathVariable String username) {
        List<Record> records = recordService.getRecordsByUser(username);
        return records.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(records);
    }

    // Получить записи по проекту
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Record>> getRecordsByProject(@PathVariable Long projectId) {
        List<Record> records = recordService.getRecordsByProject(projectId);
        return records.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(records);
    }

    // Обновить запись (используем RecordDTO)
    @PatchMapping("/{recordId}")
    public ResponseEntity<Record> updateRecord(@PathVariable Long recordId, @RequestBody RecordDTO updatedRecordDTO) {
        Record updatedRecord = recordService.updateRecord(recordId, updatedRecordDTO);
        return updatedRecord != null ? ResponseEntity.ok(updatedRecord) : ResponseEntity.notFound().build();
    }

    // Удалить запись
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecord(recordId);
        return ResponseEntity.noContent().build(); // Возвращаем статус 204 (No Content)
    }

    // Получить запись по ID
    @GetMapping("/{recordId}")
    public ResponseEntity<Record> getRecordById(@PathVariable Long recordId) {
        Record record = recordService.getRecordById(recordId);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.notFound().build(); // Если запись не найдена, возвращаем статус 404 (Not Found)
    }

    // Получить записи за определенный период времени
    @GetMapping("/between")
    public ResponseEntity<List<Record>> getRecordsBetweenDates(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        List<Record> records = recordService.getRecordsBetweenDates(startDate, endDate);
        return records.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(records);
    }

    // Удалить все записи пользователя
    @DeleteMapping("/user/{username}")
    public ResponseEntity<Void> deleteRecordsByUser(@PathVariable String username) {
        recordService.deleteRecordsByUser(username);
        return ResponseEntity.noContent().build();
    }
}
