package com.example.MyBlogApi.services.abstracts;

import com.example.MyBlogApi.dto.Request.AddCommentRequest;
import com.example.MyBlogApi.dto.Request.UpdateCommentRequest;
import com.example.MyBlogApi.dto.Response.GetBlogCommentsResponse;
import com.example.MyBlogApi.dto.Response.GetCommentByIdResponse;
import com.example.MyBlogApi.dto.Response.UserGetMyCommentsResponse;

import java.util.List;

public interface CommentService {

    void add(AddCommentRequest addCommentRequest, Long blogId);
    void update(UpdateCommentRequest updateCommentRequest, Long commentId);

    void delete(Long id);

    List<GetBlogCommentsResponse> getBlogComments(Long blogId);

    GetCommentByIdResponse getCommentById(Long commentId);

    List<UserGetMyCommentsResponse> getMyComments();
}
