package com.squad8.s8orangebackend.repository;

import com.squad8.s8orangebackend.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findTagByTag(String tag);
}
