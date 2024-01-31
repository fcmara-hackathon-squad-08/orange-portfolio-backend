package com.squad8.s8orangebackend.repository;
import com.squad8.s8orangebackend.domain.project.Project;
import com.squad8.s8orangebackend.enums.EnumTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
            SELECT distinct(pj) FROM Project AS pj
            LEFT JOIN pj.tags as tg
            WHERE tg.tag = :tag OR tg.id IS NULL
            """)
    List<Project> findProjectByTag(String tag);

    @Query("""
            SELECT distinct(pj) FROM Project as pj
            """)
    List<Project> findAllDistinctProject();
}
