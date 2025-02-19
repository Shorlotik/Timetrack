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
public class RecordController {
    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords() {
        return ResponseEntity.ok(recordService.getAllRecords());
    }

    @PostMapping
    public ResponseEntity<Record> saveRecord(@RequestBody Record record) {
        return ResponseEntity.ok(recordService.saveRecord(record));
    }
}
