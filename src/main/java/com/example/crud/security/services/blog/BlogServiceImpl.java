package com.example.crud.security.services.blog;

import com.example.crud.models.Blog;
import com.example.crud.models.User;
import com.example.crud.repositories.blogs.BlogRepository;
import com.example.crud.security.services.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    BlogRepository blogRepository;

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog getBlog(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(
                () -> new IllegalArgumentException("Invalid Blog Id")
        );
    }

    @Override
    public void deleteBlog(Long blogId) {
        Blog blog = getBlog(blogId);
        blogRepository.delete(blog);
    }

    /* To update the blog, there are few restriction
     * - the value must exist and not null or empty
     * - the new value must different with the old value
     */
    /**
     *
     * @param blogId
     * @param blogTitle
     * @param blogContent
     * @return
     */
    @Override
    @Transactional
    public Blog updateBlog(Long blogId, @NotBlank String blogTitle, @NotBlank String blogContent) {
        Blog blog = getBlog(blogId);
        if(blogTitle.length() > 0 && !blogTitle.equalsIgnoreCase(blog.getTitle())) {
            blog.setTitle(blogTitle);
        }
        if(blogContent.length() > 0 && !blogContent.equalsIgnoreCase(blog.getContent())) {
            blog.setContent(blogContent);
        }

        blogRepository.save(blog);

        return blog;
    }

    @Override
    public List<Blog> getBlogByUser(UserDetailsImpl user) {
        return blogRepository.findBlogsByUser(user);
    }
}
