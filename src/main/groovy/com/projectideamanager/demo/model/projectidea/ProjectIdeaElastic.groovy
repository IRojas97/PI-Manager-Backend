package com.projectideamanager.demo.model.projectidea

import org.springframework.data.annotation.Id

class ProjectIdeaElastic {
    @Id
    String id
    String name
    String author
    String description
    Date createdDate
}
