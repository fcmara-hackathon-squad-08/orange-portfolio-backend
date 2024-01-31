package com.squad8.s8orangebackend.domain.tag;

import com.squad8.s8orangebackend.domain.project.Project;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_tag")
public class Tag implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String tag;

    @ManyToMany(mappedBy = "tags")
    private Set<Project> projects = new HashSet<>();


    public Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }
}
