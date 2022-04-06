package com.example.crud.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="blog_id", insertable = false, updatable = false)
    private Long Id;

    @ManyToOne
    private User user;
    @NotBlank
    private String title;
    @NotBlank
    private String content;


    @ManyToMany
    @JoinTable(name="blog_tag",
    joinColumns = @JoinColumn(name="blog_id"),
    inverseJoinColumns = @JoinColumn(name="tag_id"))
    private List<Tag> tags;

    @CreationTimestamp
    @Column(name="create_date", updatable=false, nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Column(name="updated_at", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Blog() {
    }

    public Blog(Long id, User user, String title, String content, List<Tag> tags) {
        Id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
