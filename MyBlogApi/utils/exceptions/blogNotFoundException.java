package com.example.MyBlogApi.utils.exceptions;

public class blogNotFoundException extends RuntimeException{
    public blogNotFoundException() {
        super(ErrorMessages.BLOG_NOT_FOUND.message());
    }
}