package com.projectideamanager.demo.model.solution

import org.springframework.lang.Nullable

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class SolutionPost {
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
    @Nullable
    URL repository
    @NotBlank
    String parentId
}
