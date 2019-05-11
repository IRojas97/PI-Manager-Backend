package com.projectideamanager.demo.services;

import com.mongodb.client.model.Projections;
import com.projectideamanager.demo.models.projectidea.ProjectIdeaMongo;
import com.projectideamanager.demo.models.projectidea.ProjectIdeaMongoRepository;

import java.util.ArrayList;
import java.util.List;

import com.projectideamanager.demo.models.projectidea.ProjectIdeaPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectIdeaService {
  @Autowired
  ProjectIdeaMongoRepository projectIdeaMongoRepository;

  public List<ProjectIdeaMongo> getAll() {
    return projectIdeaMongoRepository.findAll();
  }

  public void saveProjectIdea(ProjectIdeaMongo projectIdeaMongo) {
    projectIdeaMongoRepository.save(projectIdeaMongo);
  }

  public ProjectIdeaMongo mapPostToMongo(ProjectIdeaPost projectIdeaPost) {
    ProjectIdeaMongo projectIdeaMongo = new ProjectIdeaMongo();

    projectIdeaMongo.setAuthor(projectIdeaPost.getAuthor());
    projectIdeaMongo.setCreatedDate(projectIdeaPost.getCreatedDate());
    projectIdeaMongo.setDescription(projectIdeaPost.getDescription());
    projectIdeaMongo.setName(projectIdeaPost.getName());

    return  projectIdeaMongo;
  }
}
