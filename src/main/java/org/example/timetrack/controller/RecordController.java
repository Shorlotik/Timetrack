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

    @PostMapping("/start")
    public Record startTracking(@RequestParam Long projectId) {
        return recordService.startTracking(projectId);
    }

    @PostMapping("/finish")
    public Record finishTracking(@RequestParam Long projectId) {
        return recordService.finishTracking(projectId);
    }

    @GetMapping
    public List<Record> getAllRecords() {
        return recordService.getAllRecords();
    }
}
