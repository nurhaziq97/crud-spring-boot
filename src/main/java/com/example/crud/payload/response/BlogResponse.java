package com.example.crud.payload.response;

import com.example.crud.models.Tag;

import java.util.ArrayList;
import java.util.List;

public class BlogResponse {
    private String blogTitle;
    private String blogContent;
    private ArrayList<Tag> tags;

    public BlogResponse(String blogTitle, String blogContent, ArrayList<Tag> tags) {
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.tags = tags;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }
}
