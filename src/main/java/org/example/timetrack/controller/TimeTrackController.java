package org.example.timetrack.controller;

import lombok.RequiredArgsConstructor;
import org.example.timetrack.entity.TimeRecord;
import org.example.timetrack.service.TimeTrackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-tracks")  // Базовый URL для всех эндпоинтов этого контроллера
@RequiredArgsConstructor
public class TimeTrackController {

    private final TimeTrackService timeTrackService;  // Сервис для обработки логики

    @GetMapping
    public ResponseEntity<List<TimeRecord>> getAllTimeRecords() {
        return ResponseEntity.ok(timeTrackService.getAllTimeRecords());
    }

    @PostMapping
    public ResponseEntity<TimeRecord> createTimeRecord(@RequestBody TimeRecord timeRecord) {
        return ResponseEntity.ok(timeTrackService.createTimeRecord(timeRecord));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeRecord> updateTimeRecord(@PathVariable Long id, @RequestBody TimeRecord timeRecord) {
        return ResponseEntity.ok(timeTrackService.updateTimeRecord(id, timeRecord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeRecord(@PathVariable Long id) {
        timeTrackService.deleteTimeRecord(id);
        return ResponseEntity.noContent().build();
    }
}
