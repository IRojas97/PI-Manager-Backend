package com.projectideamanager.demo.model.solution

import org.springframework.data.mongodb.repository.MongoRepository

interface SolutionMongoRepository extends MongoRepository<SolutionMongo, String> {
    List<SolutionMongo> findByParentId(String parentId);
}