package com.projectideamanager.demo.service;

import com.projectideamanager.demo.model.projectidea.ProjectIdeaMongo;
import com.projectideamanager.demo.model.projectidea.ProjectIdeaMongoRepository;
import com.projectideamanager.demo.model.projectidea.ProjectIdeaPost;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectIdeaService {
  @Autowired
  ProjectIdeaMongoRepository projectIdeaMongoRepository;

  public List<ProjectIdeaMongo> getAll() {
    return projectIdeaMongoRepository.findAll();
  }

  public ProjectIdeaMongo getById(String id) {
    Optional<ProjectIdeaMongo> projectIdeaMongo = projectIdeaMongoRepository.findById(id);

    return projectIdeaMongo.orElse(null);
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

    return  projectIdeaMongo;
  }
}
