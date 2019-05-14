package com.projectideamanager.demo.controller;

import com.projectideamanager.demo.model.projectidea.ProjectIdeaMongo;
import com.projectideamanager.demo.model.projectidea.ProjectIdeaPost;
import com.projectideamanager.demo.service.ProjectIdeaService;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/projectIdeas/")
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

  @GetMapping(path = "{projectIdeaId}")
  public ResponseEntity getWithId(@PathVariable @NotBlank String projectIdeaId) {
    ProjectIdeaMongo projectIdeaMongo = projectIdeaService.getById(projectIdeaId);

    if (projectIdeaMongo == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(projectIdeaMongo, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity postProjectIdea(@RequestBody ProjectIdeaPost projectIdeaPost) {
    ProjectIdeaMongo projectIdeaMongo = projectIdeaService.mapPostToMongo(projectIdeaPost);
    projectIdeaService.saveProjectIdea(projectIdeaMongo);

    return new ResponseEntity<>(projectIdeaMongo, HttpStatus.OK);
  }

  @PutMapping(path = "{projectIdeaId}")
  public ResponseEntity putProjectIdea(@PathVariable @NotBlank String projectIdeaId,
                                       @RequestBody ProjectIdeaPost projectIdeaPost) {
    if (projectIdeaService.getById(projectIdeaId) == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    ProjectIdeaMongo projectIdeaMongo = projectIdeaService.mapPostToMongo(projectIdeaPost);
    projectIdeaMongo.setId(projectIdeaId);
    projectIdeaService.saveProjectIdea(projectIdeaMongo);

    return new ResponseEntity<>(projectIdeaMongo, HttpStatus.OK);
  }

  @DeleteMapping(path = "{projectIdeaId}")
  public ResponseEntity deleteProjectIdea(@PathVariable String projectIdeaId) {
    if (projectIdeaService.getById(projectIdeaId) == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    projectIdeaService.deleteById(projectIdeaId);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
