package com.projectideamanager.demo.model.solution

import com.projectideamanager.demo.model.comment.CommentComplete
import com.projectideamanager.demo.model.comment.CommentMongo
import org.springframework.data.annotation.Id

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull;

class SolutionComplete {
    @Id
    @NotBlank
    String id
    @NotBlank
    String author
    @NotBlank
    String text
    @NotNull
    Date createdDate
    @NotNull
    Date editedDate
    @NotNull
    Boolean accepted
    @NotNull
    List<CommentComplete> comments
    @NotNull
    URL repository
    @NotBlank
    String parentId
}
