package org.example.timetrack.repository;

import org.example.timetrack.entity.Record;
import org.example.timetrack.entity.User;
import org.example.timetrack.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findTopByUserAndProjectOrderByStartTimeDesc(User user, Project project);
    List<Record> findByUser(User user);
}
