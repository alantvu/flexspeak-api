CREATE TABLE _user (
                      id INTEGER PRIMARY KEY,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      firstname VARCHAR(255),
                      lastname VARCHAR(255),
                      password VARCHAR(255),
                      role VARCHAR(255)
);

CREATE TABLE aac (
                     id INTEGER PRIMARY KEY,
                     created_at TIMESTAMP,
                     end_time TIMESTAMP,
                     sentence VARCHAR(255),
                     start_time TIMESTAMP,
                     updated_at TIMESTAMP,
                     user_id INTEGER NOT NULL,
                     CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES _user(id)
);
