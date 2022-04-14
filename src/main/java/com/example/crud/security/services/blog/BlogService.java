package com.example.crud.security.services.blog;

import com.example.crud.models.Blog;
import com.example.crud.repositories.blogs.BlogRepository;
import com.example.crud.security.services.user.UserDetailsImpl;

import java.util.List;

public interface BlogService {

    Blog createBlog(Blog blog);

    List<Blog> getAllBlogs();

    Blog getBlog(Long blogId);

    void deleteBlog(Long blogId);

    Blog updateBlog(Long blogId, String blogTitle, String blogContent);

    List<Blog> getBlogByUser(UserDetailsImpl user);
}
