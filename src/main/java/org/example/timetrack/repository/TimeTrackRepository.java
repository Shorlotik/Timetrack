package org.example.timetrack.repository;

import org.example.timetrack.entity.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTrackRepository extends JpaRepository<TimeRecord, Long> {
}
