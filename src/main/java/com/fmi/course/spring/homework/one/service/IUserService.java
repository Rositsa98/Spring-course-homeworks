package com.fmi.course.spring.homework.one.service;

import com.fmi.course.spring.homework.one.exception.NonExistingEntityException;
import com.fmi.course.spring.homework.one.model.User;

import java.util.List;

public interface IUserService {

    public User addUser( User user);
    public User getUser(String id) throws NonExistingEntityException;

    public List<User> getUsers();

    public User updateUser(User user) throws NonExistingEntityException;

    public User deleteUser(String id) throws NonExistingEntityException;

    public User getUserByUsername(String username);


}
