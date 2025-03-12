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
import java.util.Map;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startTracking(@RequestParam Long projectId, @RequestParam String username) {
        try {
            Record record = recordService.startTracking(projectId, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(record);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/finish")
    public ResponseEntity<Record> finishTracking(@RequestParam Long projectId, @RequestParam String username) {
        try {
            Record record = recordService.finishTracking(projectId, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(record);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Record) Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords() {
        List<Record> records = recordService.getAllRecords();
        return ResponseEntity.ok(records.isEmpty() ? List.of() : records); // Пустой список если нет записей
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Record>> getRecordsByUser(@PathVariable String username) {
        List<Record> records = recordService.getRecordsByUser(username);
        return ResponseEntity.ok(records.isEmpty() ? List.of() : records); // Пустой список если нет записей
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Record>> getRecordsByProject(@PathVariable Long projectId) {
        List<Record> records = recordService.getRecordsByProject(projectId);
        return ResponseEntity.ok(records.isEmpty() ? List.of() : records); // Пустой список если нет записей
    }

    @PatchMapping("/{recordId}")
    public ResponseEntity<Record> updateRecord(@PathVariable Long recordId, @RequestBody RecordDTO updatedRecordDTO) {
        Record updatedRecord = recordService.updateRecord(recordId, updatedRecordDTO);
        return updatedRecord != null ? ResponseEntity.ok(updatedRecord) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecord(recordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<Record> getRecordById(@PathVariable Long recordId) {
        Record record = recordService.getRecordById(recordId);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Record>> getRecordsBetweenDates(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        List<Record> records = recordService.getRecordsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(records.isEmpty() ? List.of() : records); // Пустой список если нет записей
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Void> deleteRecordsByUser(@PathVariable String username) {
        recordService.deleteRecordsByUser(username);
        return ResponseEntity.noContent().build();
    }
}
