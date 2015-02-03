CREATE TABLE "user" (
    username VARCHAR(255) PRIMARY key,
    dispaly_name VARCHAR(255),
    email varchar(254),
    created_at TIMESTAMP
);

CREATE UNIQUE INDEX ON "user" ((lower(email)));
