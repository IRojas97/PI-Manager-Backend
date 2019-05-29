package com.projectideamanager.demo.model.projectidea

import com.projectideamanager.demo.model.interfaces.Difficulty

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class ProjectIdeaPost {
    @NotBlank
    String name
    @NotBlank
    String author
    @NotBlank
    String description
    @NotNull
    Date createdDate
    @NotNull
    Difficulty difficulty
    @NotNull
    Date editedDate
    @NotNull
    List<String> solutionList
}
