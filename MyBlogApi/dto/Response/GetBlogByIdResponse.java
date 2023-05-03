package com.example.MyBlogApi.dto.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBlogByIdResponse {

    private String title;

    private String body;

    private String username;

    private Date createdAt;

    private Date updatedAt;

    private List<GetBlogCommentsResponse> comments;

}