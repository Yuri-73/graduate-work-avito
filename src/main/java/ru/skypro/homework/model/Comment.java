package ru.skypro.homework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * {@link Класс} AdComment комментарий объявления <br>
 * {@link Integer} pk - идентификатор комментария (not null) <br>
 * {@link User} author - автор комментария (not null) <br>
 * {@link String} authorImage - аватарка автора (not null) <br>
 * {@link String} authorFirstName - имя создателя комментария (not null) <br>
 * {@link LocalDateTime} createdAt  - дата и время создания комментария (not null) <br>
 * {@link String} text - текст комментария (not null) <br>
 * {@link Ad} ad  - комментируемое объявление (not null) <br>
 *
 * @author Chowo
 */
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private Integer pk;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Basic(fetch=FetchType.LAZY)
    @Column(name = "author_Image")
    private byte [] authorImage;

    @Column(name = "author_first_name")
    private String authorFirstName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "text")
    private String text;

    public Comment() {

    }

    @ManyToOne
    @JoinColumn(name = "ad_id", referencedColumnName = "id")
    private Ad ad;
}
