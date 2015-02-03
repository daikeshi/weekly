CREATE TABLE user_task (
    id bigserial PRIMARY key,
    task_id bigserial NOT NULL,
    username VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE user_task ADD FOREIGN KEY (task_id) REFERENCES task (task_id);
ALTER TABLE user_task ADD FOREIGN KEY (username) REFERENCES "user" (username);
