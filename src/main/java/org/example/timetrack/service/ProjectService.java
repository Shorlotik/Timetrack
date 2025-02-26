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
    public Project getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.orElse(null);  // Возвращает проект, если он найден, иначе возвращает null
    }

    // UPDATE - Обновление проекта
    public Project updateProject(Long id, Project project) {
        if (projectRepository.existsById(id)) {
            project.setId(id);  // Устанавливаем ID, чтобы проект был обновлен
            return projectRepository.save(project);  // Сохраняем обновленный проект
        }
        return null;  // Если проект с таким ID не найден, возвращаем null
    }

    // DELETE - Удаление проекта
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);  // Удаляем проект
            return true;  // Успешное удаление
        }
        return false;  // Проект не найден для удаления
    }
}
