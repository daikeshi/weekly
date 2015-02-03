CREATE TABLE Task(
    task_id bigserial PRIMARY key,
    created_at TIMESTAMP NOT NULL,
    content text NOT NULL
)
