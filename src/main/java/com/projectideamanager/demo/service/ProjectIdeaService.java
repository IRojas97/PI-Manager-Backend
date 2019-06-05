package com.projectideamanager.demo.service;

import com.mongodb.MongoClientException;
import com.projectideamanager.demo.model.interfaces.Difficulty;
import com.projectideamanager.demo.model.projectidea.ProjectIdeaComplete;
import com.projectideamanager.demo.model.projectidea.ProjectIdeaMongo;
import com.projectideamanager.demo.model.projectidea.ProjectIdeaMongoRepository;
import com.projectideamanager.demo.model.projectidea.ProjectIdeaPost;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.projectideamanager.demo.model.solution.SolutionComplete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProjectIdeaService {
  @Autowired
  ProjectIdeaMongoRepository projectIdeaMongoRepository;
  @Autowired
  SolutionService solutionService;

  public List<ProjectIdeaMongo> getAll() {
    return projectIdeaMongoRepository.findAll();
  }

  public ProjectIdeaMongo getById(String id) {
    Optional<ProjectIdeaMongo> projectIdeaMongo = projectIdeaMongoRepository.findById(id);

    return projectIdeaMongo.orElse(null);
  }

  public ProjectIdeaComplete getCompleteById(String id) {
    ProjectIdeaComplete projectIdeaComplete;
    ProjectIdeaMongo projectIdeaMongo = getById(id);

    if (projectIdeaMongo == null) {
      throw new MongoClientException("Could not find solution by ID: " + id);
    }

    projectIdeaComplete = mapMongoToComplete(projectIdeaMongo);

    return projectIdeaComplete;
  }

  public void saveProjectIdea(ProjectIdeaMongo projectIdeaMongo) {
    projectIdeaMongoRepository.save(projectIdeaMongo);
  }

  public void deleteById(String id) {
    projectIdeaMongoRepository.deleteById(id);
  }

  public ProjectIdeaMongo mapPostToMongo(ProjectIdeaPost projectIdeaPost) {
    ProjectIdeaMongo projectIdeaMongo = new ProjectIdeaMongo();

    projectIdeaMongo.setAuthor(projectIdeaPost.getAuthor());
    projectIdeaMongo.setCreatedDate(projectIdeaPost.getCreatedDate());
    projectIdeaMongo.setDescription(projectIdeaPost.getDescription());
    projectIdeaMongo.setName(projectIdeaPost.getName());
    projectIdeaMongo.setSolutionList(projectIdeaPost.getSolutionList());
    projectIdeaMongo.setDifficulty(projectIdeaPost.getDifficulty());
    projectIdeaMongo.setEditedDate(projectIdeaPost.getEditedDate());

    return  projectIdeaMongo;
  }

  public ProjectIdeaComplete mapMongoToComplete(ProjectIdeaMongo projectIdeaMongo) {
    ProjectIdeaComplete projectIdeaComplete = new ProjectIdeaComplete();
    List<String> solutionList = projectIdeaMongo.getSolutionList();
    List<SolutionComplete> solutionCompleteList = new ArrayList<>();

    projectIdeaComplete.setId(projectIdeaMongo.getId());
    projectIdeaComplete.setAuthor(projectIdeaMongo.getAuthor());
    projectIdeaComplete.setCreatedDate(projectIdeaMongo.getCreatedDate());
    projectIdeaComplete.setDescription(projectIdeaMongo.getDescription());
    projectIdeaComplete.setName(projectIdeaMongo.getName());
    projectIdeaComplete.setDifficulty(projectIdeaMongo.getDifficulty());
    projectIdeaComplete.setEditedDate(projectIdeaMongo.getEditedDate());

    if (!solutionList.isEmpty()) {
      solutionList.parallelStream().forEach(solution ->
              solutionCompleteList.add(solutionService.getCompleteById(solution)));
    }
    solutionCompleteList.sort(Comparator.comparing(SolutionComplete::getCreatedDate));
    projectIdeaComplete.setSolutionList(solutionCompleteList);

    return projectIdeaComplete;
  }

  public void remap() {
    List<ProjectIdeaMongo> projectIdeaMongoList = getAll();

    projectIdeaMongoList.parallelStream().forEach(project -> {
      if(project.getDifficulty() == null) {
        project.setDifficulty(Difficulty.EASY);
      }
      if (project.getEditedDate() == null) {
        project.setEditedDate(project.getCreatedDate());
      }
    });

    projectIdeaMongoRepository.saveAll(projectIdeaMongoList);
  }
}
