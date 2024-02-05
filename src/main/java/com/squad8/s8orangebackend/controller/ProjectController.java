package com.squad8.s8orangebackend.controller;

import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.dtos.ProjectDto;
import com.squad8.s8orangebackend.enums.EnumTag;
import com.squad8.s8orangebackend.service.ProjectService;
import com.squad8.s8orangebackend.util.ConvertStringJsonToObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Insert project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the project",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User unauthorized",
                    content = @Content)})
    @Transactional
    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<Project> insertProject(@RequestParam List<String> tags, @RequestParam(value = "file", required = false)MultipartFile file,
                                                @Schema(example = "{\"title\":\"string\", \"link\":\"string\", \"description\":\"string\"}") @RequestPart("projectDto") String projectDto, UriComponentsBuilder uriComponentsBuilder)
            throws IOException {
        Project project = projectService.fromDto(convertStringJsonToObject.deserialize(projectDto, ProjectDto.class));
        project = projectService.insertProject(project, tags, file);
        URI uri = uriComponentsBuilder.path("/{id}").buildAndExpand(project.getId()).toUri();
        return ResponseEntity.created(uri).body(project);
    }

    @Operation(summary = "List projects by tag or list all without any specific tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the project",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content) })
    @GetMapping("/list/tags")
    public ResponseEntity<List<Project>> listProject(@RequestParam(required = false) List<String> tags) {
        List<Project> projects = projectService.listProjectByTag(tags);
        return ResponseEntity.ok().body(projects);
    }

    @Operation(summary = "List current user project by tag or list all without any specific tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the project",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content) })
    @GetMapping("/list/tags/user")
    public ResponseEntity<List<Project>> listProjectByCurrentUser(@RequestParam(required = false) List<String> tags) {
        List<Project> projects = projectService.listAllUserProjectWithTagOrWithoutTag(tags);
        return ResponseEntity.ok().body(projects);
    }

    @Operation(summary = "Delete a project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Found the project",
                    useReturnTypeSchema = false),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the project",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Project.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "User unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content) })
    @Transactional
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestParam() List<String> tags, @RequestParam(value = "file", required = false) MultipartFile file,
                                                 @Schema(example = "{\"title\":\"string\", \"link\":\"string\", \"description\":\"string\"}")
                                                 @RequestPart("projectDto") String projectDto) throws IOException {
        ProjectDto projectConverted = convertStringJsonToObject.deserialize(projectDto, ProjectDto.class);
        Project project = projectService.updateProjectBasicInformation(id, projectConverted, file, tags);
        return ResponseEntity.ok().body(project);
    }

}
