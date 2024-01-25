CREATE TABLE IF NOT EXISTS tb_project (
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    tag VARCHAR(50) NOT NULL,
    link TEXT NOT NULL,
    description TEXT NOT NULL
);