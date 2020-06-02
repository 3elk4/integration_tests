package edu.iis.mto.blog.domain.model;

import java.beans.Transient;
import java.util.List;

import javax.persistence.*;

@Entity
public class BlogPost extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(columnDefinition = "CLOB NOT NULL")
    @Lob
    private String entry;

    @OneToMany
    @JoinColumn(name = "postId")
    private List<LikePost> likes;

    public BlogPost() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public void setLikes(List<LikePost> likes) {
        this.likes = likes;
    }

    public List<LikePost> getLikes() {
        return likes;
    }

    @Transient
    public int getLikesCount() {
        return likes.size();
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
