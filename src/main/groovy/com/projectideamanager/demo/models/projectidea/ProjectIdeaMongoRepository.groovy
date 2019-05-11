package com.projectideamanager.demo.models.projectidea

import org.springframework.data.mongodb.repository.MongoRepository

interface ProjectIdeaMongoRepository extends MongoRepository<ProjectIdeaMongo, String> {
    public ProjectIdeaMongo findByName(String name);
}