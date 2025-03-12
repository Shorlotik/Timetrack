package org.example.timetrack.controller;

import lombok.RequiredArgsConstructor;
import org.example.timetrack.dto.ProjectDTO;
import org.example.timetrack.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // Создание проекта
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO) {
        try {
            ProjectDTO createdProject = projectService.createProject(projectDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // Получение всех проектов
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects.isEmpty() ? List.of() : projects); // Пустой список если нет проектов
    }

    // Получение проекта по ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id) // Обрабатываем Optional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Обновление проекта
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        return projectService.updateProject(id, projectDTO) // Обрабатываем Optional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Удаление проекта
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        boolean deleted = projectService.deleteProject(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Поиск проектов по названию
    @GetMapping("/search")
    public ResponseEntity<List<ProjectDTO>> searchProjectsByName(@RequestParam String name) {
        List<ProjectDTO> projects = projectService.searchProjectsByName(name);
        return ResponseEntity.ok(projects.isEmpty() ? List.of() : projects); // Пустой список если нет проектов
    }

    // Получение проектов пользователя
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByUser(@PathVariable Long userId) {
        List<ProjectDTO> projects = projectService.getProjectsByUser(userId);
        return ResponseEntity.ok(projects.isEmpty() ? List.of() : projects);
    }
}
