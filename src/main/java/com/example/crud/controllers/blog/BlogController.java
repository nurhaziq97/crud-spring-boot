package com.example.crud.controllers.blog;

import com.example.crud.models.Blog;
import com.example.crud.models.Tag;
import com.example.crud.models.User;
import com.example.crud.payload.request.BlogRequest;
import com.example.crud.payload.response.MessageResponse;
import com.example.crud.repositories.auths.UserRepository;
import com.example.crud.repositories.blogs.BlogRepository;
import com.example.crud.repositories.blogs.TagRepository;
import com.example.crud.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    TagRepository tagRepository;

    // Cannot call the SecurityContextHolder.getContext().getAuthentication() through global variable
    // must e called inside method
    // this happened because the route for /api/blog do not have authentication set in WebSecurityConfig file.

    @GetMapping(value="/{id}")
    public ResponseEntity<?> getBlogByUser(
            @PathVariable(value="id") Long itemId) {
         final Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        User user = new User(userDetails.getId(), userDetails.getUsername(),
                userDetails.getFirstName(), userDetails.getLastName(),
                userDetails.getEmail());
        Blog blog = blogRepository.findById(itemId)
                .orElseThrow(()->new IllegalArgumentException("Blog Not Found"));
        return ResponseEntity.ok(blog);
    }

    @PostMapping(value="/create")
    public ResponseEntity<?> createBlog(@Valid @RequestBody BlogRequest createBlog ) {
        if(createBlog.getContent().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Please submit the content"));
        }
        if(createBlog.getTitle().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Please enter the title for the blog"));
        }
        Blog blog = new Blog(
                createBlog.getTitle(),
                createBlog.getContent());

        List<Tag> tags = new ArrayList<>();

        if(createBlog.getTags()!=null && createBlog.getTags().size() > 0) {
            for (Tag tag : createBlog.getTags()) {
                Tag tagOk = tagRepository.findTagByTagName(tag.getTagName())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Tag " + tag.getTagName() + " Not Found"
                        ));
                tags.add(tagOk);
            }
            blog.setTags(tags);
        }
         final Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        User user = new User(userDetails.getId(), userDetails.getUsername(),
                userDetails.getFirstName(), userDetails.getLastName(),
                userDetails.getEmail());
        blog.setUser(user);
        blogRepository.save(blog);

        return ResponseEntity.ok(blog);
    }

    @PutMapping(value="/edit/{id}")
    public ResponseEntity<?> editBlog(
            @PathVariable(value="id") int id,
            @RequestBody BlogRequest blogRequest) {
         final Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Blog blog = blogRepository.findById((long) id)
                .orElseThrow(()->new IllegalArgumentException("Blog Not Found"));
        blog.setContent(blogRequest.getContent());
        blog.setTitle(blogRequest.getTitle());
        blogRepository.save(blog);
        return ResponseEntity.ok(blog);
    }
}
