package com.squad8.s8orangebackend.service;
import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.dtos.ProjectDto;
import com.squad8.s8orangebackend.repository.ProjectRepository;
import com.squad8.s8orangebackend.repository.UserRepository;
import com.squad8.s8orangebackend.service.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    public Project insertProject(Project project) {
        return projectRepository.save(project);
    }

    public Project fromDto(ProjectDto projectDto) {
        Project project = new Project();
        User user = userRepository.findById(projectDto.getIdUser()).orElseThrow();
        project.setTitle(projectDto.getTitle());
        project.setTag(projectDto.getTag());
        project.setLink(projectDto.getLink());
        project.setDescription(projectDto.getDescription());
        project.setImageUrl(projectDto.getImageUrl());
        project.setUser(user);
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
        User user = userRepository.findById(projectDto.getIdUser()).orElseThrow();
        entity.setTitle(projectDto.getTitle());
        entity.setTag(projectDto.getTag());
        entity.setLink(projectDto.getLink());
        entity.setDescription(projectDto.getDescription());
        entity.setImageUrl(projectDto.getImageUrl());
        entity.setUser(user);
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