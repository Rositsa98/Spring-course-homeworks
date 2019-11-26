package com.fmi.course.spring.homework.one.service.impl;

import com.fmi.course.spring.homework.one.dao.UserRepository;
import com.fmi.course.spring.homework.one.exception.NonExistingEntityException;
import com.fmi.course.spring.homework.one.model.User;
import com.fmi.course.spring.homework.one.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repo;


    @Override
    public User addUser(User user) {
        return repo.insert(user);
    }

    @Override
    public User getUser(String id) throws NonExistingEntityException {

        return repo.findById(id).orElseThrow( () -> new NonExistingEntityException("User does not exist!"));
    }

    @Override
    public List<User> getUsers() {
        return repo.findAll();
    }

    @Override
    public User updateUser(User user) throws NonExistingEntityException {

        Optional<User> foundUser = repo.findById(user.getId());

        if(!foundUser.isPresent()) {
            throw new NonExistingEntityException("Trying to update non existing user!");
        }

        return repo.save(user);
    }

    @Override
    public User deleteUser(String id) throws NonExistingEntityException {

        Optional<User> foundUser = repo.findById(id);

        if(!foundUser.isPresent()){
            throw new NonExistingEntityException("Trying to delete non existing user!");
        }
        repo.deleteById(id);

        return foundUser.get();
    }

    @Override
    public User getUserByUsername(String username) {
        return repo.findAll().stream().filter(u -> u.getName().equals(username)).findFirst().get();
    }
}
