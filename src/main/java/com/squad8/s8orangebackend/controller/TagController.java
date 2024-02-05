package com.squad8.s8orangebackend.controller;

import com.squad8.s8orangebackend.domain.tag.Tag;
import com.squad8.s8orangebackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping()
    public ResponseEntity<List<Tag>> findAllTags() {
        List<Tag> tags = tagService.findAllTags();
        return ResponseEntity.ok().body(tags);
    }
}
