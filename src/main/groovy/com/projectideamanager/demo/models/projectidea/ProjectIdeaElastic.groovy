package com.projectideamanager.demo.models.projectidea

import org.springframework.data.annotation.Id

class ProjectIdeaElastic {
    @Id
    String id
    String name
    String author
    String description
    Date createdDate
}
