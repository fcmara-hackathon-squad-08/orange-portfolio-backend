package com.squad8.s8orangebackend.service;

import com.squad8.s8orangebackend.domain.tag.Tag;
import com.squad8.s8orangebackend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }
}
