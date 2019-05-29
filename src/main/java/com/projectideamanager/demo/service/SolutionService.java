package com.projectideamanager.demo.service;

import com.mongodb.MongoClientException;
import com.projectideamanager.demo.model.projectidea.ProjectIdeaMongo;
import com.projectideamanager.demo.model.projectidea.ProjectIdeaMongoRepository;
import com.projectideamanager.demo.model.solution.SolutionMongo;
import com.projectideamanager.demo.model.solution.SolutionMongoRepository;
import com.projectideamanager.demo.model.solution.SolutionPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolutionService {
  @Autowired
  SolutionMongoRepository solutionMongoRepository;
  @Autowired
  ProjectIdeaMongoRepository projectIdeaMongoRepository;

  public List<SolutionMongo> getByParentId(String parentId) {
    return solutionMongoRepository.findByParentId(parentId);
  }

  public void saveSolution(SolutionMongo solutionMongo) {
    ProjectIdeaMongo projectIdeaMongo = projectIdeaMongoRepository.findById(solutionMongo.getParentId()).orElse(null);

    if (projectIdeaMongo == null) {
      throw new MongoClientException("Could not find project by ID: " + solutionMongo.getParentId());
    }

    solutionMongoRepository.save(solutionMongo);

    List<String> solutionList = new ArrayList<>();
    solutionList.add(solutionMongo.getId());
    solutionList.addAll(projectIdeaMongo.getSolutionList());

    projectIdeaMongo.setSolutionList(solutionList);
    projectIdeaMongoRepository.save(projectIdeaMongo);
  }

  public SolutionMongo getById(String id) {
    Optional<SolutionMongo> solutionMongo = solutionMongoRepository.findById(id);

    return solutionMongo.orElse(null);
  }

  public SolutionMongo mapPostToMongo(SolutionPost solutionPost) {
    SolutionMongo solutionMongo = new SolutionMongo();

    solutionMongo.setAuthor(solutionPost.getAuthor());
    solutionMongo.setText(solutionPost.getText());
    solutionMongo.setCreatedDate(solutionPost.getCreatedDate());
    solutionMongo.setEditedDate(solutionPost.getEditedDate());
    solutionMongo.setAccepted(solutionPost.getAccepted());
    solutionMongo.setComments(solutionPost.getComments());
    solutionMongo.setRepository(solutionPost.getRepository());
    solutionMongo.setParentId((solutionPost.getParentId()));

    return solutionMongo;
  }
}
