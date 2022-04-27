package com.example.crud.security.services.blog;

import com.example.crud.models.Blog;
import com.example.crud.models.User;
import com.example.crud.repositories.auths.UserRepository;
import com.example.crud.repositories.blogs.BlogRepository;
import com.example.crud.security.services.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Service
public class BlogServiceImpl {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserRepository userRepository;

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog getBlog(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(
                () -> new IllegalArgumentException("Invalid Blog Id")
        );
    }

    public void deleteBlog(Long blogId, UserDetailsImpl userDetails) {
        Blog blog = getBlog(blogId);
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("Does not found user"));
        if(blog.getUser() != user) {
            throw new IllegalArgumentException("Blog does not belong to this user");
        }
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
    @Transactional
    public Blog updateBlog(Long blogId, @NotBlank String blogTitle, @NotBlank String blogContent, UserDetailsImpl userDetails) {
        Blog blog = getBlog(blogId);
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        if(blog.getUser() != user) {
            throw new IllegalArgumentException("Blog does not belong to this user");
        }
        if(blogTitle.length() > 0 && !blogTitle.equalsIgnoreCase(blog.getTitle())) {
            blog.setTitle(blogTitle);
        }
        if(blogContent.length() > 0 && !blogContent.equalsIgnoreCase(blog.getContent())) {
            blog.setContent(blogContent);
        }

        blogRepository.save(blog);

        return blog;
    }

    public List<Blog> getBlogByUser(UserDetailsImpl userDetails) {
        User user = userRepository.getById(userDetails.getId());
        return blogRepository.findBlogsByUser(user);
    }
}
