CREATE TABLE record (
                        id BIGSERIAL PRIMARY KEY,
                        start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                        finish_time TIMESTAMP,
                        duration BIGINT,
                        project_id BIGINT NOT NULL,
                        user_id BIGINT NOT NULL,
                        CONSTRAINT fk_record_project FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE CASCADE,
                        CONSTRAINT fk_record_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
