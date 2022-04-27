package com.example.crud.payload.response;

import com.example.crud.models.Tag;
import com.example.crud.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogResponse {

    private Long blogId;
    private String blogTitle;
    private String blogContent;
    private List<Tag> tags;

    private String username;

    private String email;

    private Date createdAt;

    public BlogResponse(String blogTitle, String blogContent, List<Tag> tags) {
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.tags = tags;
    }

    public BlogResponse(Long blogId, String blogTitle, String blogContent, List<Tag> tags, User user, Date createdAt) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogContent = blogContent;
        this.tags = tags;
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt= createdAt;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
}
