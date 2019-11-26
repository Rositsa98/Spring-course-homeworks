package com.fmi.course.spring.homework.one.dao;

import com.fmi.course.spring.homework.one.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
