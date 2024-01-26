package com.squad8.s8orangebackend.controller;
import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.dtos.ProjectRegistrationDto;
import com.squad8.s8orangebackend.service.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
@RestController
@RequestMapping("/project")
public class ProjectRegistrationController {
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
}
