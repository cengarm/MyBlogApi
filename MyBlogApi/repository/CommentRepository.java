package com.example.MyBlogApi.repository;

import com.example.MyBlogApi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByBlogId(Long blogId);

    List<Comment> findAllByUserId(Long userId);

}