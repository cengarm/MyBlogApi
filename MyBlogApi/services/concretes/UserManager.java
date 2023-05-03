package com.example.MyBlogApi.services.concretes;
import com.example.MyBlogApi.dto.Response.UserGetMyBlogsResponse;
import com.example.MyBlogApi.dto.Response.UserGetProfileResponse;
import com.example.MyBlogApi.entity.Blog;
import com.example.MyBlogApi.entity.User;
import com.example.MyBlogApi.repository.RoleRepository;
import com.example.MyBlogApi.repository.UserRepository;
import com.example.MyBlogApi.services.abstracts.UserService;
import com.example.MyBlogApi.utils.exceptions.ErrorMessages;
import com.example.MyBlogApi.utils.mappers.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserManager implements UserDetailsService, UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapperService mapperService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));
    }

    @Override
    public UserGetProfileResponse getProfile(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USERNAME_NOT_FOUND.message()));

        UserGetProfileResponse response = mapperService.forResponse().map(user, UserGetProfileResponse.class);

        List<UserGetMyBlogsResponse> blogsResponses = new ArrayList<>();

        //manually mapping blogs.
        for (Blog blog:user.getBlogs()) {
            UserGetMyBlogsResponse blogResponse = new UserGetMyBlogsResponse();
            blogResponse.setTitle(blog.getTitle());
            blogResponse.setBody(blog.getBody());
            blogResponse.setCommentCount(blog.getComments().size());
            blogResponse.setCreatedAt(blog.getCreatedAt());
            blogsResponses.add(blogResponse);
        }

        response.setBlogs(blogsResponses);

        return response;
    }


}
