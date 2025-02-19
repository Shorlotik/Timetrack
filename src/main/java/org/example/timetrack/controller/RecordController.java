package org.example.timetrack.controller;

import org.example.timetrack.entity.Record;
import org.example.timetrack.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {  // Определение контроллера для работы с записями

    private final RecordService recordService;  // Сервис для работы с записями (инициализируется через конструктор)

    @GetMapping  // Обработчик GET-запросов на "/records" (получение всех записей)
    public ResponseEntity<List<Record>> getAllRecords() {
        return ResponseEntity.ok(recordService.getAllRecords());  // Возвращает список всех записей с HTTP-статусом 200 (OK)
    }

    @PostMapping  // Обработчик POST-запросов на "/records" (создание новой записи)
    public ResponseEntity<Record> saveRecord(@RequestBody Record record) {
        return ResponseEntity.ok(recordService.saveRecord(record));  // Сохраняет запись и возвращает её с HTTP-статусом 200 (OK)
    }
}
