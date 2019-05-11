package com.projectideamanager.demo.controllers;

import com.projectideamanager.demo.models.projectidea.ProjectIdeaMongo;
import com.projectideamanager.demo.models.projectidea.ProjectIdeaPost;
import com.projectideamanager.demo.services.ProjectIdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api/projectIdeas")
public class ProjectIdeaController {

  ProjectIdeaService projectIdeaService;

  @Autowired
  public ProjectIdeaController(ProjectIdeaService projectIdeaService) {
    this.projectIdeaService = projectIdeaService;
  }

  @GetMapping
  public ResponseEntity getAll() {
    return new ResponseEntity<>(projectIdeaService.getAll(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity postProjectIdea(@RequestBody ProjectIdeaPost projectIdeaPost) {
    ProjectIdeaMongo projectIdeaMongo = projectIdeaService.mapPostToMongo(projectIdeaPost);
    projectIdeaService.saveProjectIdea(projectIdeaMongo);

    return new ResponseEntity<>(projectIdeaMongo, HttpStatus.OK);
  }
}
