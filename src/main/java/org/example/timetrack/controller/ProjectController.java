package org.example.timetrack.controller;

import org.example.timetrack.entity.Project;
import org.example.timetrack.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {  // Определение контроллера для работы с проектами

    private final ProjectService projectService;  // Сервис для работы с проектами (инициализируется через конструктор)

    @GetMapping  // Обработчик GET-запросов на "/projects" (получение всех проектов)
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());  // Возвращает список всех проектов с HTTP-статусом 200 (OK)
    }

    @PostMapping  // Обработчик POST-запросов на "/projects" (создание нового проекта)
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project.getName()));  // Создает проект и возвращает его с HTTP-статусом 200 (OK)
    }
}