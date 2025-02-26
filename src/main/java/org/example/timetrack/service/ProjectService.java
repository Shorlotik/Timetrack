package org.example.timetrack.service;

import org.example.timetrack.entity.Project;
import org.example.timetrack.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // CREATE - Создание проекта
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // READ ALL - Получение всех проектов
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // READ ONE - Получение проекта по ID
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    // UPDATE - Обновление проекта
    public Optional<Project> updateProject(Long id, Project project) {
        if (projectRepository.existsById(id)) {
            project.setId(id);
            return Optional.of(projectRepository.save(project));
        }
        return Optional.empty();
    }

    // DELETE - Удаление проекта
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // NEW: Получение проектов пользователя
    public List<Project> getProjectsByUser(Long userId) {
        return projectRepository.findByUserId(userId);
    }

    // NEW: Поиск проектов по названию (поиск по части названия)
    public List<Project> searchProjectsByName(String name) {
        return projectRepository.findByNameContainingIgnoreCase(name);
    }
}
