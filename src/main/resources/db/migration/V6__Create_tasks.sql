CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,
                       project_id BIGINT NOT NULL,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       status VARCHAR(50) DEFAULT 'NEW',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_tasks_project FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE
);
