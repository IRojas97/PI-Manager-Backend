package com.projectideamanager.demo.service;

import com.google.common.collect.Lists;
import com.mongodb.MongoClientException;
import com.projectideamanager.demo.model.comment.CommentComplete;
import com.projectideamanager.demo.model.comment.CommentMongo;
import com.projectideamanager.demo.model.comment.CommentMongoRepository;
import com.projectideamanager.demo.model.comment.CommentPost;
import com.projectideamanager.demo.model.solution.SolutionMongo;
import com.projectideamanager.demo.model.solution.SolutionMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
  @Autowired
  CommentMongoRepository commentMongoRepository;
  @Autowired
  SolutionMongoRepository solutionMongoRepository;

  public List<CommentMongo> getByParentId(String parentId) {
    return commentMongoRepository.findByParentId(parentId);
  }

  public CommentComplete getCompleteById(String id) {
    CommentComplete commentComplete;
    CommentMongo commentMongo = this.getById(id);

    if (commentMongo == null) {
      throw new MongoClientException("Could not find solution by ID: " + id);
    }

    commentComplete = mapMongoToComplete(commentMongo);

    return commentComplete;
  }



  public void saveComment(CommentMongo commentMongo) {
    SolutionMongo solutionMongo = solutionMongoRepository.findById(commentMongo.getParentId()).orElse(null);

    if (solutionMongo == null) {
      throw new MongoClientException("Could not find solution by ID: " + commentMongo.getParentId());
    }

    commentMongoRepository.save(commentMongo);

    List<String> commentList = new ArrayList<>();
    commentList.add(commentMongo.getId());
    commentList.addAll(solutionMongo.getComments());

    solutionMongo.setComments(commentList);
    solutionMongoRepository.save(solutionMongo);
  }

  public void saveReply(CommentMongo replyMongo) {
    CommentMongo commentMongo = commentMongoRepository.findById(replyMongo.getParentId()).orElse(null);

    if (commentMongo == null) {
      throw new MongoClientException("Could not find comment by ID: " + replyMongo.getParentId());
    }

    commentMongoRepository.save(replyMongo);

    List<String> replyList = new ArrayList<>();
    replyList.add(replyMongo.getId());
    replyList.addAll(commentMongo.getReplies());

    commentMongo.setReplies(replyList);
    commentMongoRepository.save(commentMongo);
  }

  public CommentMongo getById(String id) {
    Optional<CommentMongo> commentMongo = commentMongoRepository.findById(id);

    return commentMongo.orElse(null);
  }

  public CommentMongo mapPostToMongo(CommentPost commentPost) {
    CommentMongo commentMongo = new CommentMongo();

    commentMongo.setAuthor(commentPost.getAuthor());
    commentMongo.setText(commentPost.getText());
    commentMongo.setCreatedDate(commentPost.getCreatedDate());
    commentMongo.setEditedDate(commentPost.getEditedDate());
    commentMongo.setReplies(commentPost.getReplies());
    commentMongo.setParentId(commentPost.getParentId());

    return commentMongo;
  }

  private CommentComplete mapMongoToComplete(CommentMongo commentMongo) {
    CommentComplete commentComplete = new CommentComplete();

    commentComplete.setId(commentMongo.getId());
    commentComplete.setAuthor(commentMongo.getAuthor());
    commentComplete.setText(commentMongo.getText());
    commentComplete.setCreatedDate(commentMongo.getCreatedDate());
    commentComplete.setEditedDate(commentMongo.getEditedDate());
    commentComplete.setParentId(commentMongo.getParentId());
    commentComplete.setReplies(new ArrayList<>());

    if (!commentMongo.getReplies().isEmpty()) {
      commentComplete.setReplies(this.fetchAllReplies(commentMongo.getReplies()));
    }

    return commentComplete;
  }

  private List<CommentComplete> fetchAllReplies(List<String> replies) {
    List<CommentComplete> commentCompleteList = new ArrayList<>();
    List<CommentMongo> replyMongoList = Lists.newArrayList(commentMongoRepository.findAllById(replies));

    replyMongoList.parallelStream().forEach(
            reply -> commentCompleteList.add(mapMongoToComplete(reply)));
    commentCompleteList.sort(Comparator.comparing(CommentComplete::getCreatedDate));

    return commentCompleteList;
  }

    public void deleteByIds(List<String> commentList) {
      if (commentList.isEmpty()) {
        return;
      }

      Iterable<CommentMongo> commentMongos = commentMongoRepository.findAllById(commentList);
      commentMongos.forEach( comment -> this.deleteByIds(comment.getReplies()));
      commentMongoRepository.deleteAll(commentMongos);
    }
}
