package com.example.crud.payload.request;

import com.example.crud.models.Tag;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class BlogRequest {
    @NotBlank(message = "please enter title")
    private String title;
    @NotBlank
    private String content;

    private Set<Tag> tags;

    public BlogRequest(String title, String content, Set<Tag> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
