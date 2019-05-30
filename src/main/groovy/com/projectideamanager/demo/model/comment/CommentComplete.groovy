package com.projectideamanager.demo.model.comment

import org.springframework.data.annotation.Id

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CommentComplete {
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
    List<CommentComplete> replies
    @NotBlank
    String parentId
}
