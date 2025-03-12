package org.example.timetrack.controller;

import org.example.timetrack.dto.RecordDTO;
import org.example.timetrack.entity.Record;
import org.example.timetrack.service.RecordService;
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
    public ResponseEntity<?> finishTracking(@RequestParam Long projectId, @RequestParam String username) {
        try {
            Record record = recordService.finishTracking(projectId, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(record);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords() {
        List<Record> records = recordService.getAllRecords();
        return ResponseEntity.ok(records.isEmpty() ? List.of() : records);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Record>> getRecordsByUser(@PathVariable String username) {
        List<Record> records = recordService.getRecordsByUser(username);
        return ResponseEntity.ok(records.isEmpty() ? List.of() : records);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Record>> getRecordsByProject(@PathVariable Long projectId) {
        List<Record> records = recordService.getRecordsByProject(projectId);
        return ResponseEntity.ok(records.isEmpty() ? List.of() : records);
    }

    @PatchMapping("/{recordId}")
    public ResponseEntity<?> updateRecord(@PathVariable Long recordId, @RequestBody RecordDTO updatedRecordDTO) {
        try {
            Record updatedRecord = recordService.updateRecord(recordId, updatedRecordDTO);
            return ResponseEntity.ok(updatedRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<?> deleteRecord(@PathVariable Long recordId) {
        try {
            recordService.deleteRecord(recordId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<?> getRecordById(@PathVariable Long recordId) {
        try {
            Record record = recordService.getRecordById(recordId);
            return ResponseEntity.ok(record);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/between")
    public ResponseEntity<List<Record>> getRecordsBetweenDates(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        List<Record> records = recordService.getRecordsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(records.isEmpty() ? List.of() : records);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<?> deleteRecordsByUser(@PathVariable String username) {
        try {
            recordService.deleteRecordsByUser(username);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}