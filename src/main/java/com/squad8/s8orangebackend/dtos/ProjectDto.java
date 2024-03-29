package com.squad8.s8orangebackend.dtos;

import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.domain.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProjectDto {

    @NotNull
    private String title;
    @NotNull
    private String link;
    @NotEmpty
    private String description;

    public ProjectDto() {
    }

    public ProjectDto(Project project) {
        this.title = project.getTitle();
        this.link = project.getLink();
        this.description = project.getDescription();
        User user = project.getUser();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
