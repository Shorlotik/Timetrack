CREATE TABLE time_entries (
                              id BIGSERIAL PRIMARY KEY,
                              user_id BIGINT NOT NULL,
                              task_id BIGINT NOT NULL,
                              start_time TIMESTAMP NOT NULL,
                              end_time TIMESTAMP,
                              duration BIGINT,
                              description TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_time_entries_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                              CONSTRAINT fk_time_entries_task FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE
);
