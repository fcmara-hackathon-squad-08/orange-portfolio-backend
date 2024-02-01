package com.squad8.s8orangebackend.service;
import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.domain.tag.Tag;
import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.dtos.ProjectDto;
import com.squad8.s8orangebackend.enums.EnumTag;
import com.squad8.s8orangebackend.repository.ProjectRepository;
import com.squad8.s8orangebackend.repository.TagRepository;
import com.squad8.s8orangebackend.repository.UserRepository;
import com.squad8.s8orangebackend.service.exceptions.ResourceNotFoundException;
import com.squad8.s8orangebackend.service.exceptions.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserService userService;

    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    public List<Project> listProjectByTag(List<EnumTag> tags) {
        List<Project> projects = new ArrayList<>();

        boolean isNull = tags == null;
        if (!isNull) {
            for (EnumTag tag : tags) {
                projects.addAll(projectRepository.findProjectByTag(tag.name().toUpperCase()));
            }
        } else {
            projects = projectRepository.findAllDistinctProject();
        }

        return projects;
    }

    public List<Project> listAllUserProjectWithTagOrWithoutTag(List<EnumTag> tags) {
        List<Project> projects = new ArrayList<>();

        User user = userService.getCurrentUser();
        boolean isNull = tags == null;
        if (!isNull) {
            for (EnumTag tag : tags) {
                projects.addAll(projectRepository.findAllDistinctProjectByUserAndTag(tag.name().toUpperCase(), user.getId()));
            }
        } else {
            projects = projectRepository.findAllDistinctProjectByUser(user.getId());
        }

        return projects;
    }

    public Project insertProject(Project project, List<EnumTag> tags) {
        List<Tag> projectTags = tags.stream()
                .map(tag -> {
                    String tagName = tag.name().toUpperCase();
                    return tagRepository.findTagByTag(tagName);
                })
                .toList();

        project.getTags().addAll(projectTags);

        return projectRepository.save(project);
    }
    public Project fromDto(ProjectDto projectDto) {
        Project project = new Project();
        project.setTitle(projectDto.getTitle());
        project.setLink(projectDto.getLink());
        project.setDescription(projectDto.getDescription());
        project.setImageUrl(projectDto.getImageUrl());
        project.setUser(userService.getCurrentUser());
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        return projectRepository.save(project);
    }
    public void deleteProject(Long id) {
        boolean isProjectSaved = projectRepository.existsById(id);
        boolean isProjectUserOwner = Objects.equals(projectRepository.getReferenceById(id).getUser().getId(), userService.getCurrentUser().getId());
        if (isProjectSaved && isProjectUserOwner) {
            projectRepository.deleteById(id);
        } else {
            throw new UnauthorizedAccessException("User is not the owner of the project" + id);
        }
    }
    public Project updateFromDto(Long id, ProjectDto projectDto) {
        boolean isProjectSaved = projectRepository.existsById(id);
        boolean isProjectUserOwner = Objects.equals(projectRepository.getReferenceById(id).getUser().getId(), userService.getCurrentUser().getId());
        if (isProjectSaved && isProjectUserOwner) {
            Project entity = projectRepository.getReferenceById(id);
            updateData(entity, projectDto);
            return projectRepository.save(entity);
        } else {
            throw new UnauthorizedAccessException("User is not the owner of the project" + id);
        }
    }
    public Project updateProject(Project project, List<EnumTag> tags ){
        List<Tag> projectTags = tags.stream()
                .map(tag -> {
                    String tagName = tag.name().toUpperCase();
                    return tagRepository.findTagByTag(tagName);
                })
                .toList();

        project.getTags().clear();
        project.getTags().addAll(projectTags);
        return projectRepository.save(project);
    }
    private void updateData(Project entity, ProjectDto projectDto) {
        entity.setTitle(projectDto.getTitle());
        entity.setLink(projectDto.getLink());
        entity.setDescription(projectDto.getDescription());
        entity.setImageUrl(projectDto.getImageUrl());
        entity.setUser(userService.getCurrentUser());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
    }
}