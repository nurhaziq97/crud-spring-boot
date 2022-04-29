package com.example.crud.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="blog_id", insertable = false, updatable = false)
    private Long blogId;

    @ManyToOne
    private User user;
    @NotBlank
    private String title;
    // for postgresql @Lob annotation cannot be accepted
    // for mysql using @Lob will make the column datatype to change into longtext / text
    @NotBlank
    @Column(length=1024, columnDefinition = "text")
    private String content;


    @ManyToMany
    @JoinTable(name="blog_tag",
    joinColumns = @JoinColumn(name="blog_id"),
    inverseJoinColumns = @JoinColumn(name="tag_id"))
    private List<Tag> tags;

    @CreationTimestamp
    @Column(name="created_at", updatable=false, nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Column(name="updated_at", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Blog() {
    }

    public Blog(Long blogId, User user, String title, String content, List<Tag> tags) {
        this.blogId = blogId;
        this.user = user;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
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

    @Override
    public String toString() {
        return "Blog{" +
                "Id=" + blogId +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", createDate=" + createDate +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
