package com.squad8.s8orangebackend.service;

import com.squad8.s8orangebackend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository actionProjectRepository;
    public ResponseEntity<?> listProjects() {
        return ResponseEntity.ok(actionProjectRepository.findAll());
    }
}
