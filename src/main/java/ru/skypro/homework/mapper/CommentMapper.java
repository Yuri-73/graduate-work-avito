package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

import java.time.LocalDateTime;

/**
 * @author Chowo
 */
@Component
public class CommentMapper {

    public CommentDTO toDto(Comment comment, User user) {
        Integer author = comment.getAuthor().getId();
        String authorImage = user.getAuthorImage().toString();
        String authorFirstName = user.getFirstname();
        LocalDateTime createdAt = comment.getCreatedAt();
        Integer pk = comment.getPk();
        String text = comment.getText();
        return new CommentDTO(author, authorImage, authorFirstName, createdAt, pk, text);
    }

    public Comment toComment(CommentDTO commentDTO, User user) {
        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setCreatedAt(commentDTO.getCreatedAt());
        comment.setPk(commentDTO.getPk());
        comment.setText(commentDTO.getText());
        return comment;
    }

}
