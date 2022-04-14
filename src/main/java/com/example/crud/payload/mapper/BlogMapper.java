package com.example.crud.payload.mapper;

import com.example.crud.models.Blog;
import com.example.crud.models.Tag;
import com.example.crud.payload.response.BlogResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BlogMapper {
    public static BlogResponse toBlogResponse(Blog blog) {
        return new BlogResponse(
                blog.getTitle(),
                blog.getContent(),
                new ArrayList<Tag>()
        );
    }
}
