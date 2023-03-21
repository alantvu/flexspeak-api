CREATE TYPE grid_title_enum AS ENUM ('ALL', 'EAT');

CREATE TABLE custom_word
(
    id                SERIAL PRIMARY KEY,
    word_to_display   VARCHAR(255) NOT NULL,
    word_to_speak     VARCHAR(255) NOT NULL,
    image_path        VARCHAR(255),
    grid_row          INTEGER NOT NULL,
    grid_column       INTEGER NOT NULL,
    grid_title_enum   grid_title_enum NOT NULL,
    created_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    user_id           INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES _user (id)
);
