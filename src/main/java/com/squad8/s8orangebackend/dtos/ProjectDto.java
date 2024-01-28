package com.squad8.s8orangebackend.dtos;

import com.squad8.s8orangebackend.domain.project.EnumTag;
import com.squad8.s8orangebackend.domain.project.Project;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ProjectDto {
    @NotNull
    private String title;
    @NotNull
    private EnumTag tag;
    @NotNull
    private String link;
    @NotEmpty
    private String description;
    private String imageUrl;

    public ProjectDto() {
    }
    public ProjectDto(Project project) {
        this.title = project.getTitle();
        this.tag = project.getTag();
        this.link = project.getLink();
        this.description = project.getDescription();
        this.imageUrl = project.getImageUrl();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
