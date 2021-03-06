package com.projectideamanager.demo.model.projectidea

import com.projectideamanager.demo.model.interfaces.Difficulty
import org.springframework.data.annotation.Id

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class ProjectIdeaMongo {
    @Id
    @NotBlank
    String id
    @NotBlank
    String name
    @NotBlank
    String author
    @NotBlank
    String description
    @NotNull
    Date createdDate
    @NotNull
    Date editedDate
    @NotNull
    Difficulty difficulty
    @NotNull
    List<String> solutionList
}
