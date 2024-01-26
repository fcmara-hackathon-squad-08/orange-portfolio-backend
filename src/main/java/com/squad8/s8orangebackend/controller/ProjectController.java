package com.squad8.s8orangebackend.controller;
import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService actionProjectService;
    @GetMapping("/list")
    public ResponseEntity<List<Project>> listProjects() {
        List<Project> projects = actionProjectService.listProjects();
        return ResponseEntity.ok().body(projects);
    }
}