CREATE TABLE tb_project_tag (
     project_id INT REFERENCES tb_project(id),
     tag_id INT REFERENCES tb_tag(id),
     PRIMARY KEY (project_id, tag_id)
);