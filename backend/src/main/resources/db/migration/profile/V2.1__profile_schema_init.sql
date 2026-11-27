CREATE SCHEMA IF NOT EXISTS profile;

CREATE TABLE profile.profile (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE,
    phone VARCHAR(20),
    library_card VARCHAR(20) UNIQUE,
    surname VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50)
);