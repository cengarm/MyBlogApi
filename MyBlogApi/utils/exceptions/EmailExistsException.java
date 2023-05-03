package com.example.MyBlogApi.utils.exceptions;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException() {
        super(ErrorMessages.EMAIL_EXISTS.message());
    }
}