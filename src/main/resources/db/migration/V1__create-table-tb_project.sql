CREATE TABLE IF NOT EXISTS tb_project (
    projectId SERIAL PRIMARY KEY,
    projectTitle VARCHAR(50) NOT NULL,
    projectTags VARCHAR(50) NOT NULL,
    projectLink TEXT NOT NULL,
    projectDescription TEXT NOT NULL
);