package ru.skypro.homework.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Chowo
 */
@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    //@OneToOne
//    @Column(name = "author")
    private Integer author;

    @Column(name = "author_image")
    private String authorImage;

    @Column(name = "author_first_name")
    private String authorFirstName;

    @Column(name = "created_at")
    private Long createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "pk")
    private Integer pk;

//    @Column(name = "text")
    private String text;

    public Comment(Integer author, String authorImage, String authorFirstName, String text) {
        this.author = author;
        this.authorImage = authorImage;
        this.authorFirstName = authorFirstName;
        this.createdAt = System.currentTimeMillis();
        this.text = text;
    }

    public Comment() {

    }

    public Integer getAuthor() {
        return author;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Integer getPk() {
        return pk;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(author, comment.author) && Objects.equals(authorImage, comment.authorImage) && Objects.equals(authorFirstName, comment.authorFirstName) && Objects.equals(createdAt, comment.createdAt) && Objects.equals(pk, comment.pk) && Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, authorImage, authorFirstName, createdAt, pk, text);
    }
}
