package com.fmi.course.spring.homework.one.controller;

import com.fmi.course.spring.homework.one.exception.InvalidEntityException;
import com.fmi.course.spring.homework.one.exception.NonExistingEntityException;
import com.fmi.course.spring.homework.one.model.ErrorResponse;
import com.fmi.course.spring.homework.one.model.Post;
import com.fmi.course.spring.homework.one.service.impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService service;

    @GetMapping
    public List<Post> getUsers(){
        return service.getPosts();
    }

    @GetMapping("{id}")
    public Post getPost(@PathVariable("id") String id) throws NonExistingEntityException {
        return service.getPost(id);
    }

    @PostMapping()
    public ResponseEntity<Post> addPost(@RequestBody Post post, BindingResult bindingResult){

        if(bindingResult.hasFieldErrors()) {
            String  message = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .reduce("", (acc, errStr) -> acc + errStr );
            throw new InvalidEntityException(message);
        }

        Post addedPost = service.addPost(post);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}").build(addedPost.getId()))
                .body(addedPost);

    }

    @PutMapping("{id}")
    public Post update(@PathVariable String id, @RequestBody Post post) throws NonExistingEntityException {
        if(!id.equals(post.getId())) {
            throw new InvalidEntityException(
                    String.format("Entity ID='%s' is different from URL resource ID='%s'", post.getId(), id));
        }
        return service.updatePost(post);
    }

    @DeleteMapping("{id}")
    public Post deletePost(@PathVariable("id") String id) throws NonExistingEntityException {
        return service.deletePost(id);
    }


    @ExceptionHandler({NonExistingEntityException.class, InvalidEntityException.class})
    public ResponseEntity<ErrorResponse> handleExceptions(RuntimeException ex) {
        if(ex instanceof NonExistingEntityException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
        }
    }
}
