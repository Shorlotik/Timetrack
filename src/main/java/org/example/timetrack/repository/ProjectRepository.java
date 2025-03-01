package org.example.timetrack.repository;

import org.example.timetrack.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserId(Long userId);
    List<Project> findByNameContainingIgnoreCase(String name);
}
