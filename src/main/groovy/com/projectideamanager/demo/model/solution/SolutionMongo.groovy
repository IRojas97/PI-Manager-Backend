package com.projectideamanager.demo.model.solution

import org.springframework.data.annotation.Id

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class SolutionMongo {
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
    List<String> comments
    @NotNull
    URL repository
    @NotBlank
    String parentId
}
