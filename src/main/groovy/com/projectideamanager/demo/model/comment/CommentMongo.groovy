package com.projectideamanager.demo.model.comment

import org.springframework.data.annotation.Id

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CommentMongo {
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
    List<String> replies
    @NotBlank
    String parentId
}
