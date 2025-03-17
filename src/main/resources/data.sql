-- Удаляем старые данные перед вставкой (чтобы избежать дублирования)
DELETE FROM users;
DELETE FROM projects;
DELETE FROM time_entries;

-- Добавляем тестовых пользователей
INSERT INTO users (id, username, password, role) VALUES
                                                     (1, 'admin', '{noop}admin123', 'ROLE_ADMIN'),
                                                     (2, 'user1', '{noop}password', 'ROLE_USER'),
                                                     (3, 'user2', '{noop}password', 'ROLE_USER');

-- Добавляем тестовые проекты
INSERT INTO projects (id, name, description, created_by) VALUES
                                                             (1, 'Project Alpha', 'First test project', 2),
                                                             (2, 'Project Beta', 'Second test project', 3);

-- Добавляем тестовые записи о времени
INSERT INTO time_entries (id, user_id, project_id, start_time, end_time, duration) VALUES
                                                                                       (1, 2, 1, '2024-03-16 09:00:00', '2024-03-16 12:00:00', 180),
                                                                                       (2, 3, 2, '2024-03-16 10:00:00', '2024-03-16 13:00:00', 180);
