package com.projectideamanager.demo.model.comment

import org.springframework.data.mongodb.repository.MongoRepository

interface CommentMongoRepository extends MongoRepository<CommentMongo, String> {
    List<CommentMongo> findByParentId(String parentId);
}