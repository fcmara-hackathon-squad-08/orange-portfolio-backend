package com.squad8.s8orangebackend.service;

import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository actionProjectRepository;
    public List<Project> listProjects() {
        return actionProjectRepository.findAll();
    }
}