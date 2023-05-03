package com.example.MyBlogApi.utils.exceptions;

import jakarta.validation.ValidationException;

public class InvalidPasswordException extends ValidationException {
    public InvalidPasswordException() {
        super(ErrorMessages.PASSWORD_INVALID.message());
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}