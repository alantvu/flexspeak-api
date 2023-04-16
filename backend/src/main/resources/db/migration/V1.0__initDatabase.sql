CREATE TABLE _user
(
    id        SERIAL PRIMARY KEY,
    firstname VARCHAR(255)        NOT NULL,
    lastname  VARCHAR(255)        NOT NULL,
    email     VARCHAR(255) UNIQUE NOT NULL UNIQUE,
    password  VARCHAR(255)        NOT NULL,
    role      VARCHAR(255)        NOT NULL
);

CREATE TABLE aac
(
    id         SERIAL PRIMARY KEY,
    sentence   TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    start_time TIMESTAMP WITH TIME ZONE,
    end_time   TIMESTAMP WITH TIME ZONE,
    user_id    INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES _user (id)
);