package com.fmi.course.spring.homework.one.controller;

import com.fmi.course.spring.homework.one.exception.InvalidEntityException;
import com.fmi.course.spring.homework.one.exception.NonExistingEntityException;
import com.fmi.course.spring.homework.one.model.ErrorResponse;
import com.fmi.course.spring.homework.one.model.User;
import com.fmi.course.spring.homework.one.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getUsers(){
        return service.getUsers();
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") String id) throws NonExistingEntityException {
        return service.getUser(id);
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User user, BindingResult bindingResult){

        if(bindingResult.hasFieldErrors()) {
            String  message = bindingResult.getFieldErrors().stream()
                    .map(err -> String.format("Invalid '%s' -> '%s': %s\n",
                            err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                    .reduce("", (acc, errStr) -> acc + errStr );
            throw new InvalidEntityException(message);
        }

        User addedUser = service.addUser(user);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}").build(addedUser.getId()))
                .body(addedUser);

    }

    @PutMapping("{id}")
    public User update(@PathVariable String id, @RequestBody User user) throws NonExistingEntityException {
        if(!id.equals(user.getId())) {
            throw new InvalidEntityException(
                    String.format("Entity ID='%s' is different from URL resource ID='%s'", user.getId(), id));
        }
        return service.updateUser(user);
    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable("id") String id) throws NonExistingEntityException {
        return service.deleteUser(id);
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
