package com.squad8.s8orangebackend.controller;
import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/project")
public class ProjectListController {
    @Autowired
    private ProjectService projectService;
    @GetMapping("/list")
    public ResponseEntity<List<Project>> listProject() {
        List<Project> projects = projectService.listProjects();
        return ResponseEntity.ok().body(projects);
    }
}