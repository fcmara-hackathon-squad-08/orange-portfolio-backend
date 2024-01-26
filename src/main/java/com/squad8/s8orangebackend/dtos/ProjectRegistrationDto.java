package com.squad8.s8orangebackend.dtos;

import com.squad8.s8orangebackend.domain.project.EnumTag;
import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.domain.user.User;
import jakarta.validation.constraints.NotNull;

public class ProjectRegistrationDto {

    @NotNull
    private String title;
    @NotNull
    private EnumTag tag;
    @NotNull
    private String link;
    @NotNull
    private String description;
    @NotNull
    private Long idUser;

    public ProjectRegistrationDto() {
    }

    public ProjectRegistrationDto(Project project, User user) {
        this.title = project.getTitle();
        this.tag = project.getTag();
        this.link = project.getLink();
        this.description = project.getDescription();
        this.idUser = user.getId();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EnumTag getTag() {
        return tag;
    }

    public void setTag(EnumTag tag) {
        this.tag = tag;
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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
