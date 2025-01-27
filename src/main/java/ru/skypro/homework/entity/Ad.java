package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность, представляющая объявление в системе.
 * <p>
 * Класс {@code Ad} является сущностью JPA и представляет собой объявление.
 * Включает в себя данные, такие как название, описание и цена объявления.
 * Также связан с другими сущностями, такими как {@code Image}, {@code User},
 * и {@code Comment}, для представления изображения объявления, пользователя,
 * опубликовавшего объявление, и комментариев к объявлению соответственно.
 * </p>
 * <p>
 * Этот класс используется для взаимодействия с базой данных и представления информации
 * о объявлениях в приложении.
 * </p>
 *
 * @author fifimova
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String title;
    private String description;
    private Integer price;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "ad", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
}
