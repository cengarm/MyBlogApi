package com.example.MyBlogApi.services.concretes;


import com.example.MyBlogApi.dto.Request.AddCommentRequest;
import com.example.MyBlogApi.dto.Request.UpdateCommentRequest;
import com.example.MyBlogApi.dto.Response.GetBlogCommentsResponse;
import com.example.MyBlogApi.dto.Response.GetCommentByIdResponse;
import com.example.MyBlogApi.dto.Response.UserGetMyCommentsResponse;
import com.example.MyBlogApi.entity.Comment;
import com.example.MyBlogApi.entity.User;
import com.example.MyBlogApi.repository.BlogRepository;
import com.example.MyBlogApi.repository.CommentRepository;
import com.example.MyBlogApi.repository.UserRepository;
import com.example.MyBlogApi.services.abstracts.CommentService;
import com.example.MyBlogApi.services.rules.CommentRules;
import com.example.MyBlogApi.utils.exceptions.blogNotFoundException;
import com.example.MyBlogApi.utils.exceptions.CommentNotFoundException;
import com.example.MyBlogApi.utils.exceptions.ErrorMessages;
import com.example.MyBlogApi.utils.mappers.ModelMapperService;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class CommentManager implements CommentService {

    private static final Logger logger = LogManager.getLogger(CommentManager.class);

    private ModelMapperService mapperService;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private BlogRepository blogRepository;
    private CommentRules rules;

    @Autowired
    public CommentManager(ModelMapperService mapperService, CommentRepository commentRepository,
                          UserRepository userRepository, BlogRepository blogRepository, CommentRules rules) {
        this.mapperService = mapperService;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.rules = rules;
    }

    @Override
    public void add(AddCommentRequest addCommentRequest, Long blogId) {

        rules.isBodyValid(addCommentRequest.getBody());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        Comment comment = mapperService.forRequest().map(addCommentRequest,Comment.class);

        comment.setBlog(blogRepository.findById(blogId).orElseThrow(()-> new blogNotFoundException()));
        comment.setUser(user);

        var c = commentRepository.save(comment);

        logger.info(username + " has commented(id: " + c.getId() + ") on the blog(id: " + c.getBlog().getId() + ")");
    }

    @Override
    public void update(UpdateCommentRequest updateCommentRequest, Long commentId) {

        rules.isBodyValid(updateCommentRequest.getBody());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());

        if(!comment.getUser().equals(user)){
            throw new CommentNotFoundException();
        }

        var blog = comment.getBlog();

        comment = mapperService.forRequest().map(updateCommentRequest,Comment.class);

        comment.setUser(user);
        comment.setBlog(blog);
        comment.setId(commentId);
        commentRepository.save(comment);

        logger.info(username + " has edited their comment(id: " + comment.getId() + ") on the blog(id: " + blog.getId() + ")");
    }

    @Override
    public void delete(Long id) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        Comment comment = commentRepository.findById(id).orElseThrow(()->new CommentNotFoundException());

        if(!comment.getUser().equals(user)){
            throw new CommentNotFoundException();
        }

        commentRepository.delete(comment);
        logger.info(username + " has deleted their comment(id: " + id + ") on the blog(id: " + comment.getBlog().getId()
                + ")");
    }

    @Override
    public List<GetBlogCommentsResponse> getBlogComments(Long blogId) {

        List<Comment> comments = commentRepository.findAllByBlogId(blogId);
        List<GetBlogCommentsResponse> responses = new ArrayList<GetBlogCommentsResponse>();

        for (Comment comment:comments) {
            GetBlogCommentsResponse response = mapperService.forResponse().map(comment, GetBlogCommentsResponse.class);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public GetCommentByIdResponse getCommentById(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new CommentNotFoundException());

        GetCommentByIdResponse response = mapperService.forResponse().map(comment, GetCommentByIdResponse.class);

        return response;

    }

    @Override
    public List<UserGetMyCommentsResponse> getMyComments() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        List<Comment> comments = commentRepository.findAllByUserId(user.getId());
        List<UserGetMyCommentsResponse> responses = new ArrayList<>();

        for (Comment comment:comments) {
            UserGetMyCommentsResponse response =mapperService.forResponse().map(comment,UserGetMyCommentsResponse.class);
            responses.add(response);
        }

        return responses;
    }
}
