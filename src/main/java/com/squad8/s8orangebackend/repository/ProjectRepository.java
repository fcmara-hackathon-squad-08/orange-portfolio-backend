package com.squad8.s8orangebackend.repository;

import com.squad8.s8orangebackend.domain.project.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findAll();
}
