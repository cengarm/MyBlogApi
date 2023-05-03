package com.example.MyBlogApi.utils.exceptions;

import jakarta.validation.ValidationException;

public class InvalidBlogTitleException extends ValidationException {
    public InvalidBlogTitleException(String message) {
        super(message);
    }
}