package org.example.timetrack.controller;

import lombok.RequiredArgsConstructor;
import org.example.timetrack.entity.TimeRecord;
import org.example.timetrack.service.TimeTrackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/time-tracks")  // Базовый URL
@RequiredArgsConstructor
public class TimeTrackController {

    private final TimeTrackService timeTrackService;

    // Получить все записи времени
    @GetMapping
    public ResponseEntity<List<TimeRecord>> getAllTimeRecords() {
        return ResponseEntity.ok(timeTrackService.getAllTimeRecords());
    }

    // Начать новую запись времени (пользователь начинает работу)
    @PostMapping("/start")
    public ResponseEntity<TimeRecord> startTracking(@RequestParam Long userId, @RequestParam Long projectId) {
        TimeRecord timeRecord = timeTrackService.startTracking(userId, projectId);
        return ResponseEntity.ok(timeRecord);
    }

    // Завершить запись времени (пользователь закончил работу)
    @PutMapping("/{id}/finish")
    public ResponseEntity<TimeRecord> finishTracking(@PathVariable Long id) {
        TimeRecord timeRecord = timeTrackService.finishTracking(id);
        return ResponseEntity.ok(timeRecord);
    }
}
