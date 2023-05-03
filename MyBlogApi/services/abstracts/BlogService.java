package com.example.MyBlogApi.services.abstracts;


import com.example.MyBlogApi.dto.Request.AddBlogRequest;
import com.example.MyBlogApi.dto.Request.UpdateBlogRequest;
import com.example.MyBlogApi.dto.Response.GetBlogByIdResponse;
import com.example.MyBlogApi.dto.Response.UserGetAllBlogsResponse;
import com.example.MyBlogApi.dto.Response.UserGetMyBlogsResponse;

import java.util.List;

public interface BlogService {
    void add(AddBlogRequest addBlogRequest);

    void update(UpdateBlogRequest updateBlogRequest, Long blogId);

    void delete(Long id);

    List<UserGetMyBlogsResponse> getMyBlogs();

    List<UserGetAllBlogsResponse> getAllBlogs(String orderByField);

    GetBlogByIdResponse getBlogById(Long blogId);

    List<UserGetAllBlogsResponse> getBlogsByTitleLike(String title);

}