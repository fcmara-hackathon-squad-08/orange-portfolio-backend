package com.squad8.s8orangebackend.controller;

import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.dtos.ProjectDto;
import com.squad8.s8orangebackend.enums.EnumTag;
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
    public ResponseEntity<Project> insertProject(@RequestBody @Validated ProjectDto projectDTO, UriComponentsBuilder uriComponentsBuilder){
        Project project = projectService.fromDto(projectDTO);
        project = projectService.insertProject(project);
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(project.getId()).toUri();
        return ResponseEntity.created(uri).body(project);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Project>> listProject() {
        List<Project> projects = projectService.listProjects();
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/list/tags")
    public ResponseEntity<List<Project>> listProject(@RequestParam List<EnumTag> tags) {
        List<Project> projects = projectService.listProjectByTag(tags);
        return ResponseEntity.ok().body(projects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody ProjectDto projectDto){
        Project project = projectService.updateProjectBasicInformation(id, projectDto);
        return ResponseEntity.ok().body(project);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateProject(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        projectService.updatePartialProject(id, fields);
        return ResponseEntity.ok().build();
    }
}
