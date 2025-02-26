package org.example.timetrack.controller;

import org.example.timetrack.entity.Record;
import org.example.timetrack.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Record startTracking(@RequestParam Long projectId, @RequestParam String username) {
        return recordService.startTracking(projectId, username);
    }

    // Завершить запись времени
    @PostMapping("/finish")
    public Record finishTracking(@RequestParam Long projectId, @RequestParam String username) {
        return recordService.finishTracking(projectId, username);
    }

    // Получить все записи
    @GetMapping
    public List<Record> getAllRecords() {
        return recordService.getAllRecords();
    }

    // Получить записи определенного пользователя
    @GetMapping("/user/{username}")
    public List<Record> getRecordsByUser(@PathVariable String username) {
        return recordService.getRecordsByUser(username);
    }

    // Получить записи по проекту
    @GetMapping("/project/{projectId}")
    public List<Record> getRecordsByProject(@PathVariable Long projectId) {
        return recordService.getRecordsByProject(projectId);
    }

    // Обновить запись (например, изменить описание или время)
    @PatchMapping("/{recordId}")
    public Record updateRecord(@PathVariable Long recordId, @RequestBody Record updatedRecord) {
        return recordService.updateRecord(recordId, updatedRecord);
    }

    // Удалить запись
    @DeleteMapping("/{recordId}")
    public void deleteRecord(@PathVariable Long recordId) {
        recordService.deleteRecord(recordId);
    }
}
