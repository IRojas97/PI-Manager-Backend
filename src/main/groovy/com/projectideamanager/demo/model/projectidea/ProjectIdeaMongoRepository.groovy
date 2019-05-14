package com.projectideamanager.demo.model.projectidea

import org.springframework.data.mongodb.repository.MongoRepository

interface ProjectIdeaMongoRepository extends MongoRepository<ProjectIdeaMongo, String> {
    public ProjectIdeaMongo findByName(String name);
}