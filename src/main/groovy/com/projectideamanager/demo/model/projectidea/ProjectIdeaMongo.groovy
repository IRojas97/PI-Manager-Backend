package com.projectideamanager.demo.model.projectidea

import org.springframework.data.annotation.Id

class ProjectIdeaMongo {
    @Id
    String id
    String name
    String author
    String description
    Date createdDate
}
