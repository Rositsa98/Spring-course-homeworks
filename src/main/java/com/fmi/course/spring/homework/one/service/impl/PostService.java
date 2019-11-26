package com.fmi.course.spring.homework.one.service.impl;

import com.fmi.course.spring.homework.one.dao.PostRepository;
import com.fmi.course.spring.homework.one.dao.UserRepository;
import com.fmi.course.spring.homework.one.exception.NonExistingEntityException;
import com.fmi.course.spring.homework.one.model.Post;
import com.fmi.course.spring.homework.one.model.User;
import com.fmi.course.spring.homework.one.service.IPostService;
import com.fmi.course.spring.homework.one.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository repo;


    @Override
    public Post addPost(Post post) {
        return repo.insert(post);
    }

    @Override
    public Post getPost(String id) throws NonExistingEntityException {

        return repo.findById(id).orElseThrow(() -> new NonExistingEntityException("Post does not exist!"));
    }

    @Override
    public List<Post> getPosts() {
        return repo.findAll();
    }

    @Override
    public Post updatePost(Post post) throws NonExistingEntityException {

        Optional<Post> foundPost = repo.findById(post.getId());

        if (!foundPost.isPresent()) {
            throw new NonExistingEntityException("Trying to update non existing post!");
        }

        return repo.save(post);
    }

    @Override
    public Post deletePost(String id) throws NonExistingEntityException {

        Optional<Post> foundPost = repo.findById(id);

        if (!foundPost.isPresent()) {
            throw new NonExistingEntityException("Trying to delete non existing post!");
        }
        repo.deleteById(id);

        return foundPost.get();
    }
}
