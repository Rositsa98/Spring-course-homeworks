package com.fmi.course.spring.homework.one.exception;

public class NonExistingEntityException extends RuntimeException {

    public NonExistingEntityException(String message){
        super(message);
    }

}
