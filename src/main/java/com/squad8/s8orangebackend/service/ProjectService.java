package com.squad8.s8orangebackend.service;
import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.domain.user.User;
import com.squad8.s8orangebackend.dtos.ProjectRegistrationDto;
import com.squad8.s8orangebackend.repository.ProjectRepository;
import com.squad8.s8orangebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    public List<Project> listProjects() {
        return projectRepository.findAll();
    }

    public Project insertProject(Project project) {
        return projectRepository.save(project);
    }

    public Project fromDto(ProjectRegistrationDto projectRegistrationDTO) {
        Project project = new Project();
        User user = userRepository.findById(projectRegistrationDTO.getIdUser()).orElseThrow();

        project.setTitle(projectRegistrationDTO.getTitle());
        project.setTag(projectRegistrationDTO.getTag());
        project.setLink(projectRegistrationDTO.getLink());
        project.setDescription(projectRegistrationDTO.getDescription());
        project.setUser(user);

        return projectRepository.save(project);
    }
}