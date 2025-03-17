-- Добавляем тестовые роли
INSERT INTO roles (name) VALUES
                             ('USER'),
                             ('ADMIN'),
                             ('MANAGER');

-- Добавляем тестовых пользователей
INSERT INTO users (username, password, email, full_name)
VALUES
    ('admin', 'hashed_password_1', 'admin@example.com', 'Admin User'),
    ('user1', 'hashed_password_2', 'user1@example.com', 'User One'),
    ('user2', 'hashed_password_3', 'user2@example.com', 'User Two'),
    ('user3', 'hashed_password_4', 'user3@example.com', 'User Three');

-- Привязываем пользователей к ролям
INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 2), -- admin -> ADMIN
                                              (2, 1), -- user1 -> USER
                                              (3, 1), -- user2 -> USER
                                              (4, 3); -- user3 -> MANAGER

-- Добавляем тестовые проекты
INSERT INTO projects (name, description, created_by)
VALUES
    ('Project Alpha', 'Alpha test project', 1),
    ('Project Beta', 'Beta test project', 2),
    ('Project Gamma', 'Gamma test project', 3);

-- Добавляем участников проектов
INSERT INTO project_memberships (user_id, project_id, role)
VALUES
    (2, 1, 'MEMBER'),
    (3, 1, 'MEMBER'),
    (4, 2, 'LEADER');

-- Добавляем тестовые задачи
INSERT INTO tasks (project_id, title, description, status)
VALUES
    (1, 'Setup Backend', 'Initial backend setup', 'IN_PROGRESS'),
    (2, 'Design UI', 'Create UI design', 'NEW'),
    (3, 'API Integration', 'Integrate API with frontend', 'COMPLETED');

-- Добавляем записи времени (time_entries)
INSERT INTO time_entries (user_id, task_id, start_time, end_time, duration, description)
VALUES
    (2, 1, '2025-03-10 09:00:00', '2025-03-10 11:00:00', 120, 'Backend setup work'),
    (3, 2, '2025-03-10 12:00:00', '2025-03-10 13:30:00', 90, 'UI Design work'),
    (4, 3, '2025-03-11 14:00:00', '2025-03-11 16:00:00', 120, 'API Integration work');

-- Добавляем тестовые сессии пользователей
INSERT INTO sessions (user_id, token, created_at, expires_at)
VALUES
    (1, 'token1', '2025-03-10 08:00:00', '2025-03-10 18:00:00'),
    (2, 'token2', '2025-03-10 09:30:00', '2025-03-10 19:30:00'),
    (3, 'token3', '2025-03-11 10:00:00', '2025-03-11 20:00:00');

-- Добавляем логи системы
INSERT INTO logs (user_id, action, timestamp)
VALUES
    (1, 'User logged in', '2025-03-10 08:05:00'),
    (2, 'User started task', '2025-03-10 09:35:00'),
    (3, 'User completed task', '2025-03-11 16:05:00');

-- Добавляем тестовые записи в `record`
INSERT INTO record (user_id, action, timestamp)
VALUES
    (1, 'Created Project Alpha', '2025-03-10 09:00:00'),
    (2, 'Joined Project Alpha', '2025-03-10 10:00:00'),
    (3, 'Finished UI Design', '2025-03-11 13:45:00');
