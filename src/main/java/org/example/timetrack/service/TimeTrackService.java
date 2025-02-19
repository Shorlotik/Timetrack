package org.example.timetrack.service;

import lombok.RequiredArgsConstructor;
import org.example.timetrack.entity.TimeRecord;
import org.example.timetrack.repository.TimeTrackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTrackService {

    private final TimeTrackRepository timeTrackRepository;

    public List<TimeRecord> getAllTimeRecords() {
        return timeTrackRepository.findAll();
    }

    public TimeRecord createTimeRecord(TimeRecord timeRecord) {
        return timeTrackRepository.save(timeRecord);
    }

    public TimeRecord updateTimeRecord(Long id, TimeRecord updatedRecord) {
        TimeRecord existingRecord = timeTrackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time record not found"));
        existingRecord.setStartTime(updatedRecord.getStartTime());
        existingRecord.setEndTime(updatedRecord.getEndTime());
        return timeTrackRepository.save(existingRecord);
    }

    public void deleteTimeRecord(Long id) {
        timeTrackRepository.deleteById(id);
    }
}
