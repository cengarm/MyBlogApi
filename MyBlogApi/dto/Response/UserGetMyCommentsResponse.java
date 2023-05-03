package com.example.MyBlogApi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetMyCommentsResponse {

    private String body;
    private Date createdAt;
    private Date updatedAt;

}

