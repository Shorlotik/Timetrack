package org.example.timetrack.controller;

import org.example.timetrack.entity.Record;
import org.example.timetrack.entity.User;
import org.example.timetrack.repository.RecordRepository;
import org.example.timetrack.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/time")
public class TimeTrackController {

    private final RecordRepository timeRecordRepository;
    private final UserRepository userRepository;

    public TimeTrackController(RecordRepository timeRecordRepository, UserRepository userRepository) {
        this.timeRecordRepository = timeRecordRepository;
        this.userRepository = userRepository;
    }

    // без передачи userId
    @PostMapping("/start")
    public ResponseEntity<?> startWork(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Record> activeRecord = timeRecordRepository.findByUserAndEndTimeIsNull(user);
        if (activeRecord.isPresent()) {
            return ResponseEntity.badRequest().body("You already have an active work session.");
        }

        Record record = new Record();
        record.setUser(user);
        record.setStartTime(LocalDateTime.now());

        timeRecordRepository.save(record);
        return ResponseEntity.ok("Work started at: " + record.getStartTime());
    }

    //Завершить работу без передачи userId
    @PostMapping("/finish")
    public ResponseEntity<?> finishWork(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Находим активную запись работы
        Record record = timeRecordRepository.findByUserAndEndTimeIsNull(user)
                .orElseThrow(() -> new RuntimeException("No active work session found."));

        record.setEndTime(LocalDateTime.now());
        timeRecordRepository.save(record);

        return ResponseEntity.ok("Work finished at: " + record.getEndTime());
    }
}
