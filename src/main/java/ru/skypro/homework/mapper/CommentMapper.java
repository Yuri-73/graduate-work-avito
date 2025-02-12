package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Chowo
 */
@Component
public class CommentMapper {

    /**
     * Метод преобразует объект класса Comment в Dto CommentDTO.
     *
     * @param comment объект класса Comment.
     * @return Dto CommentDTO.
     */
    public static CommentDTO commentToDto(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Попытка конвертировать comment == null");
        }
        Integer author = comment.getAuthor().getId();
        String authorImage = "/images/" + comment.getAuthor().getImage().getId();

        String authorFirstName = comment.getAuthor().getFirstname();
        LocalDateTime createdAt = comment.getCreatedAt();
        Integer pk = comment.getPk();
        String text = comment.getText();
        return new CommentDTO(author, authorImage, authorFirstName, createdAt, pk, text);
    }

    /**
     * Метод преобразует объект класса CommentDTO в Comment.
     *
     * @param commentDTO объект класса CommentDTO.
     * @return Comment.
     */
    public static Comment commentDtoToComment(CommentDTO commentDTO) {
        if (commentDTO == null) {
            throw new IllegalArgumentException("Попытка конвертировать commentDTO == null");
        }
        Comment comment = new Comment();

        comment.setPk(commentDTO.getPk());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        comment.setText(commentDTO.getText());

        return comment;
    }

    /**
     * Метод преобразует список объектов Comment в объект Dto CommentDTO,
     * содержащий список объектов Comment и их количество.
     * @param comments Лист объектов класса Comment.
     * @return Dto CommentsDTO.
     */
    public static CommentsDTO toCommentsDTO(List<Comment> comments) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCount(comments.size());
        commentsDTO.setResults(comments.stream().map(e -> commentToDto(e)).collect(Collectors.toList()));
        return commentsDTO;
    }

    /**
     * Метод преобразует Dto CreateOrUpdateCommentDTO в entity Comment.
     * @param commentDTO объект класса CommentDTO.
     * @param ad объект класса Ad.
     * @param user объект класса User.
     * @return entity Comment.
     */
    public static Comment createComment(CreateOrUpdateCommentDTO commentDTO, Ad ad, User user) {
        if (commentDTO == null) {
            throw new IllegalArgumentException("Попытка конвертировать commentDTO == null");
        }
        Comment comment = new Comment();

        comment.setAd(ad);
        comment.setAuthor(user);
        comment.setText(commentDTO.getText());

        return comment;
    }
}

