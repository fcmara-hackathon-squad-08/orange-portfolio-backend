package com.squad8.s8orangebackend.repository;
import com.squad8.s8orangebackend.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAll();
}
