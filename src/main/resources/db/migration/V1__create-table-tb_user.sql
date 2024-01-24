CREATE TABLE IF NOT EXISTS tb_user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    country VARCHAR(50),
    imageUrl TEXT
);