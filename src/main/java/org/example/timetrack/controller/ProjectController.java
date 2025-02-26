package org.example.timetrack.controller;

import org.example.timetrack.entity.Project;
import org.example.timetrack.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // CREATE - Создание проекта
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    // READ ALL - Получение всех проектов
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    // READ ONE - Получение проекта по ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();  // Возвращает 404, если проект не найден
        }
        return ResponseEntity.ok(project);  // Возвращает 200 и сам проект
    }

    // UPDATE - Обновление проекта
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(id, project);
        if (updatedProject == null) {
            return ResponseEntity.notFound().build();  // Возвращает 404, если проект не найден для обновления
        }
        return ResponseEntity.ok(updatedProject);  // Возвращает 200 и обновленный проект
    }

    // DELETE - Удаление проекта
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        boolean deleted = projectService.deleteProject(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();  // Возвращает 404, если проект не найден для удаления
        }
        return ResponseEntity.noContent().build();  // Возвращает 204, если проект успешно удален
    }
}
