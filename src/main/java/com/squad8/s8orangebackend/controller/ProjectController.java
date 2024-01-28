package com.squad8.s8orangebackend.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.dtos.ProjectRegistrationDto;
import com.squad8.s8orangebackend.service.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Transactional
    @PostMapping("/add")
    public ResponseEntity<Project> insertProject(@RequestBody @Validated ProjectRegistrationDto projectRegistrationDTO, UriComponentsBuilder uriComponentsBuilder){
        Project project = projectService.fromDto(projectRegistrationDTO);
        project = projectService.insertProject(project);
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(project.getId()).toUri();
        return ResponseEntity.created(uri).body(project);
    }
    @GetMapping("/list")
    public ResponseEntity<List<Project>> listProject() {
        List<Project> projects = projectService.listProjects();
        return ResponseEntity.ok().body(projects);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }


}
