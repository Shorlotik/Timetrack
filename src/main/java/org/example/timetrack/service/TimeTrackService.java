//package org.example.timetrack.service;
//
//import lombok.RequiredArgsConstructor;
//import org.example.timetrack.entity.TimeRecord;
//import org.example.timetrack.repository.TimeTrackRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class TimeTrackService {
//
//    private final TimeTrackRepository timeTrackRepository;
//
//    // Получить все записи времени
//    public List<TimeRecord> getAllTimeRecords() {
//        return timeTrackRepository.findAll();
//    }
//
//    // Начать новую запись времени
//    public TimeRecord startTracking(Long userId, Long projectId) {
//        TimeRecord timeRecord = TimeRecord.builder()
//                .userId(userId)
//                .projectId(projectId)
//                .startTime(LocalDateTime.now()) // Фиксирует время старта
//                .build();
//        return timeTrackRepository.save(timeRecord);
//    }
//
//    // Завершить запись времени
//    public TimeRecord finishTracking(Long id) {
//        TimeRecord timeRecord = timeTrackRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Time record not found"));
//
//        if (timeRecord.getEndTime() != null) {
//            throw new RuntimeException("Time tracking already finished");
//        }
//
//        timeRecord.setEndTime(LocalDateTime.now()); // Фиксирует время окончания
//        return timeTrackRepository.save(timeRecord);
//    }
//}
