package com.squad8.s8orangebackend.domain.project;
import com.squad8.s8orangebackend.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private EnumTag tag;
    @NotNull
    private String link;
    @NotEmpty
    private String description;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Project() {
    }

    public Project(Long id, String title, EnumTag tag, String link, String description, String imageUrl, User user) {
        this.id = id;
        this.title = title;
        this.tag = tag;
        this.link = link;
        this.description = description;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}