package com.squad8.s8orangebackend.domain.project;
import com.squad8.s8orangebackend.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
@Entity
@Table(name = "tb_project")
public class Project  implements Serializable {
    @Serial
    private static final Long serialVersionUID = 1l;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;
    @NotEmpty(message = "Informe um nome para o projeto")
    private String projectTitle;
    @NotEmpty(message = "Informe ao menos uma tag para o projeto")
    private EnumTags projectTags;
    private String projectLink;
    @NotEmpty(message = "Informe a decricao do projeto")
    private String projectDescription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Project() {
    }
    public Project(Long projectId, String projectTitle, EnumTags projectTags, String projectLink, String projectDescription) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.projectTags = projectTags;
        this.projectLink = projectLink;
        this.projectDescription = projectDescription;
    }
    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public String getProjectTitle() {
        return projectTitle;
    }
    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
    public EnumTags getProjectTags() {
        return projectTags;
    }
    public void setProjectTags(EnumTags projectTags) {
        this.projectTags = projectTags;
    }
    public String getProjectLink() {
        return projectLink;
    }
    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }
    public String getProjectDescription() {
        return projectDescription;
    }
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(projectId, project.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId);
    }
}