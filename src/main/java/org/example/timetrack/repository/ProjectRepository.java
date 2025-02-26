package org.example.timetrack.repository;

import org.example.timetrack.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Найти проекты, связанные с пользователем
    List<Project> findByUserId(Long userId);

    // Найти проекты по части названия (регистронезависимый поиск)
    List<Project> findByNameContainingIgnoreCase(String name);
}
