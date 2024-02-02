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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private S3Service s3Services;

    private static final String URL = "https://s3.amazonaws.com/";


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

    public Project insertProject(Project project, List<EnumTag> tags, MultipartFile multipartFile) {

        List<Tag> projectTags = getTags(tags);

        project.getTags().addAll(projectTags);

        if (!multipartFile.isEmpty()) {
            String img = s3Services.saveFile(userService.getCurrentUser().getId(), multipartFile);
            project.setImageUrl(img);
        }

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
    public Project updateProjectBasicInformation(Long id, ProjectDto projectDto, MultipartFile file, List<EnumTag> tags) {

        try {
            User user = userService.getCurrentUser();
            Project entity = projectRepository.findById(id).get();
            return updateData(projectDto, file, user, entity, tags);
        } catch (Exception e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private Project updateData(ProjectDto projectDto, MultipartFile file, User user, Project entity, List<EnumTag> tags) {

        if ( user.equals(entity.getUser())) {
            entity.setTitle(projectDto.getTitle());
            entity.setLink(projectDto.getLink());
            entity.setDescription(projectDto.getDescription());

            List<Tag> desiredTags = getTags(tags);

            List<Tag> updatedTags = desiredTags.stream()
                    .map(desiredTag -> tagRepository.findTagByTag(desiredTag.getTag().toUpperCase()))
                    .filter(Objects::nonNull)
                    .toList();

            entity.getTags().clear();
            entity.getTags().addAll(updatedTags);

            String currentFile = URL + s3Services.getBucketName() + entity.getUser().getId() + file.getOriginalFilename();


            if (!Objects.equals(entity.getImageUrl(), currentFile)) {
                s3Services.deleteFile(entity.getImageUrl());
                String img = s3Services.saveFile(user.getId(), file);
                entity.setImageUrl(img);
            }
        }

        return projectRepository.save(entity);
    }

    private List<Tag> getTags(List<EnumTag> tags) {
        List<Tag> projectTags = tags.stream()
                .map(tag -> tagRepository.findTagByTag(tag.name().toUpperCase()))
                .filter(Objects::nonNull)
                .toList();
        return projectTags;
    }

}