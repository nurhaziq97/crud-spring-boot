package com.example.crud.repositories.blogs;

import com.example.crud.models.Blog;
import com.example.crud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    public Optional<Blog> findById(Long id);

//    public List<Blog> findBlogsByUser(User user, Pageable pageable);

    public List<Blog> findBlogsByUser(User user);

}
