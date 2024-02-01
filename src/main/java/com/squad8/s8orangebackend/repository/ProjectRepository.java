package com.squad8.s8orangebackend.repository;
import com.squad8.s8orangebackend.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
            SELECT distinct(pj), tg.tag FROM Project AS pj
            JOIN pj.tags as tg
            WHERE tg.tag = :tag
            """)
    List<Project> findProjectByTag(String tag);

    @Query("""
            SELECT distinct(pj) FROM Project as pj
            """)
    List<Project> findAllDistinctProject();

    @Query("""
            SELECT distinct(pj), tg.tag FROM Project AS pj
            JOIN pj.tags as tg
            WHERE tg.tag = :tag AND pj.user.id = :id
            """)
    List<Project> findAllDistinctProjectByUserAndTag(String tag, Long id);

    @Query("""
            SELECT distinct(pj) FROM Project as pj
            WHERE pj.user.id = :id
            """)
    List<Project> findAllDistinctProjectByUser(Long id);
}
