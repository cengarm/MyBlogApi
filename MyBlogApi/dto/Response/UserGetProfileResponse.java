package com.example.MyBlogApi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetProfileResponse {
    private String username;

    private String email;

    private int age;

    private List<UserGetMyBlogsResponse> blogs;

    private List<UserGetMyCommentsResponse> comments;
}