package com.projectideamanager.demo.controller;

import com.projectideamanager.demo.model.solution.SolutionMongo;
import com.projectideamanager.demo.model.solution.SolutionPost;
import com.projectideamanager.demo.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@Validated
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("api/v1/solutions")
public class SolutionController {

  SolutionService solutionService;

  @Autowired
  public SolutionController(SolutionService solutionService) {
    this.solutionService = solutionService;
  }

  @GetMapping(path = "/{solutionId}")
  public ResponseEntity getWithId(@PathVariable @NotBlank String solutionId) {
    SolutionMongo solutionMongo = solutionService.getById(solutionId);

    if (solutionMongo == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(solutionMongo, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity postSolution(@Valid @RequestBody SolutionPost solutionPost) {
    SolutionMongo solutionMongo = solutionService.mapPostToMongo(solutionPost);
    solutionService.saveSolution(solutionMongo);

    return new ResponseEntity<>(solutionMongo, HttpStatus.OK);
  }
}
