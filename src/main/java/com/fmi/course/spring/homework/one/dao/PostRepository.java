package com.fmi.course.spring.homework.one.dao;

import com.fmi.course.spring.homework.one.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
