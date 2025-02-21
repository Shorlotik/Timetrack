package org.example.timetrack.repository;

import org.example.timetrack.entity.Record;
import org.example.timetrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findByUserAndEndTimeIsNull(User user);
}
