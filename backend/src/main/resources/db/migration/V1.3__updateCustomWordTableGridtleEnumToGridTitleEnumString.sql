BEGIN;

-- Add a new temporary column
ALTER TABLE custom_word ADD COLUMN grid_title_enum_temp VARCHAR(255);

-- Copy the data from the old column to the new column
UPDATE custom_word SET grid_title_enum_temp = grid_title_enum::text;

-- Drop the old column
ALTER TABLE custom_word DROP COLUMN grid_title_enum;

-- Rename the new column to the old column's name
ALTER TABLE custom_word RENAME COLUMN grid_title_enum_temp TO grid_title_enum;

COMMIT;
