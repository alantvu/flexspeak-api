ALTER TABLE _user
ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();
