package com.projectideamanager.demo.controller;

import com.projectideamanager.demo.model.comment.CommentMongo;
import com.projectideamanager.demo.model.comment.CommentPost;
import com.projectideamanager.demo.model.solution.SolutionMongo;
import com.projectideamanager.demo.model.solution.SolutionPost;
import com.projectideamanager.demo.service.CommentService;
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
@RequestMapping("api/v1/comments")
public class CommentController {

  CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping(path = "/{commentId}")
  public ResponseEntity getWithId(@PathVariable @NotBlank String commentId) {
    CommentMongo commentMongo = commentService.getById(commentId);

    if (commentMongo == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(commentMongo, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity postComment(@Valid @RequestBody CommentPost commentPost) {
    CommentMongo commentMongo = commentService.mapPostToMongo(commentPost);
    commentService.saveComment(commentMongo);

    return new ResponseEntity<>(commentMongo, HttpStatus.OK);
  }

  @PostMapping(path = "/reply")
  public ResponseEntity postReply(@Valid @RequestBody CommentPost commentPost) {
    CommentMongo commentMongo = commentService.mapPostToMongo(commentPost);
    commentService.saveReply(commentMongo);

    return new ResponseEntity<>(commentMongo, HttpStatus.OK);
  }

  @PutMapping(path = "/{commentId}")
  public ResponseEntity putComment(@PathVariable @NotBlank String commentId,
                                    @Valid @RequestBody CommentPost commentPost) {
    if (commentService.getById(commentId) == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    CommentMongo commentMongo = commentService.mapPostToMongo(commentPost);
    commentMongo.setId(commentId);
    commentService.saveComment(commentMongo);

    return new ResponseEntity<>(commentMongo, HttpStatus.OK);
  }
}
