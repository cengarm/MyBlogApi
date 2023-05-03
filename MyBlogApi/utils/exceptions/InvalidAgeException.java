package com.example.MyBlogApi.utils.exceptions;

import jakarta.validation.ValidationException;

public class InvalidAgeException extends ValidationException {
    public InvalidAgeException() {
        super(ErrorMessages.AGE_INVALID.message());
    }

    public InvalidAgeException(String message) {
        super(message);
    }
}
