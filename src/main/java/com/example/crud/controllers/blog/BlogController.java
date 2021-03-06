package com.example.crud.controllers.blog;

import com.example.crud.models.Blog;
import com.example.crud.models.Tag;
import com.example.crud.models.User;
import com.example.crud.payload.mapper.BlogMapper;
import com.example.crud.payload.request.BlogRequest;
import com.example.crud.payload.response.MessageResponse;
import com.example.crud.repositories.auths.UserRepository;
import com.example.crud.repositories.blogs.TagRepository;
import com.example.crud.security.services.blog.BlogServiceImpl;
import com.example.crud.security.services.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    TagRepository tagRepository;

    @Autowired
    BlogServiceImpl blogServiceImpl;

    // Cannot call the SecurityContextHolder.getContext().getAuthentication() through global variable
    // must be called inside method
    // this happened because the route for /api/blog do not have authentication set in WebSecurityConfig file.

    @GetMapping(value="/view/{id}")
    @ResponseBody
    public ResponseEntity<?> getBlogByUser(
            @PathVariable(value="id") Long  blogId) {
        if(blogId <= 0) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("No blog has been found")
            );
        }
        Blog blog = blogServiceImpl.getBlog(blogId);
//        System.out.println(blog.toString());
        return ResponseEntity.ok(BlogMapper.toBlogResponse(blog));
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
        blog = blogServiceImpl.createBlog(blog);

        return ResponseEntity.ok(blog);
    }

    @PutMapping(value="/edit/{id}")
    public ResponseEntity<?> editBlog(
            @PathVariable(value="id") Long blogId,
            @RequestBody BlogRequest blogRequest) {
//         final Authentication auth =
//                SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        Blog blog = blogServiceImpl.updateBlog(blogId, blogRequest.getTitle(), blogRequest.getContent());
        return ResponseEntity.ok(blog);
    }

    @GetMapping(value="/myblog")
    public ResponseEntity<?> getUserBlog() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails
                = auth.getPrincipal() instanceof UserDetailsImpl ?
                (UserDetailsImpl) auth.getPrincipal() : null;
        if(userDetails==null) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Login First"));
        }
        List<Blog> blogsList = blogServiceImpl.getBlogByUser(userDetails);
        return ResponseEntity.ok(blogsList);
    }

    @GetMapping(value="/list")
    public ResponseEntity<?> getAllBlog() {
        return ResponseEntity.ok(blogServiceImpl.getAllBlogs());
    }
}
