package com.squad8.s8orangebackend.controller;

import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.dtos.ProjectDto;
import com.squad8.s8orangebackend.enums.EnumTag;
import com.squad8.s8orangebackend.service.ConvertStringJsonToObject;
import com.squad8.s8orangebackend.service.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ConvertStringJsonToObject convertStringJsonToObject;

    @Transactional
    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<Project> insertProject(@RequestParam List<EnumTag> tags, @RequestParam(value = "file", required = false)MultipartFile file,
                                                 @RequestPart("projectDto") String projectDto, UriComponentsBuilder uriComponentsBuilder)
            throws IOException {
        Project project = projectService.fromDto(convertStringJsonToObject.deserialize(projectDto, ProjectDto.class));
        project = projectService.insertProject(project, tags, file);
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(project.getId()).toUri();
        return ResponseEntity.created(uri).body(project);
    }

    @GetMapping("/list/tags")
    public ResponseEntity<List<Project>> listProject(@RequestParam(required = false) List<EnumTag> tags) {
        List<Project> projects = projectService.listProjectByTag(tags);
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/list/tags/user")
    public ResponseEntity<List<Project>> listProjectByCurrentUser(@RequestParam(required = false) List<EnumTag> tags) {
        List<Project> projects = projectService.listAllUserProjectWithTagOrWithoutTag(tags);
        return ResponseEntity.ok().body(projects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody @Valid ProjectDto projectDto, @RequestParam List<EnumTag> tags ){
        Project project = projectService.updateFromDto(id, projectDto);
        project = projectService.updateProject(project, tags);
        return ResponseEntity.ok().body(project);
    }

}
