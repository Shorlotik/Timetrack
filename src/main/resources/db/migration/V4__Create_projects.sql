CREATE TABLE projects (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL UNIQUE,
                          description TEXT,
                          created_by BIGINT NOT NULL,
                          CONSTRAINT fk_projects_user FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE CASCADE
);
