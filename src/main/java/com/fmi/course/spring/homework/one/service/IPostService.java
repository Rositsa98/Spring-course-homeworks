package com.fmi.course.spring.homework.one.service;

import com.fmi.course.spring.homework.one.exception.NonExistingEntityException;
import com.fmi.course.spring.homework.one.model.Post;

import java.util.List;

public interface IPostService {

    public Post addPost(Post post);

    public Post getPost(String id) throws NonExistingEntityException;

    public List<Post> getPosts();

    public Post updatePost(Post post) throws NonExistingEntityException;

    public Post deletePost(String id) throws NonExistingEntityException;
}
