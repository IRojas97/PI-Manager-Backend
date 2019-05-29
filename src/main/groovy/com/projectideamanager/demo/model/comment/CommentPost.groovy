package com.projectideamanager.demo.model.comment

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class CommentPost {
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
