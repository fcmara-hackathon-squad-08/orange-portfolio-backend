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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        try {
            boolean project = projectRepository.existsById(id);
            if (project)
                projectRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public Project updateProjectBasicInformation(Long id, ProjectDto projectDto) {

        try {
            Project entity = projectRepository.getReferenceById(id);
            updateData(entity, projectDto);
            return projectRepository.save(entity);
        } catch (Exception e) {
            throw new ResourceNotFoundException(id);
        }
    }
    private void updateData(Project entity, ProjectDto projectDto) {
        //User user = userRepository.findById(projectDto.getIdUser()).orElseThrow();
        entity.setTitle(projectDto.getTitle());
        entity.setLink(projectDto.getLink());
        entity.setDescription(projectDto.getDescription());
        entity.setImageUrl(projectDto.getImageUrl());
        //entity.setUser(user);
    }
    public void updatePartialProject(Long id, Map<String, Object> fields) {
        Project project = projectRepository.getReferenceById(id);
        fields.forEach((propertyName, propertyValue) ->{
            if(propertyName.equals("imageUrl")){
                project.setImageUrl((String) propertyValue);
            }
        });
        projectRepository.save(project);
    }

}