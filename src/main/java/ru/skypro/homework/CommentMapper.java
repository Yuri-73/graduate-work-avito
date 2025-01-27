package ru.skypro.homework;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.model.Comment;

@Component
public class CommentMapper {

    public CommentDTO toDto(Comment comment) {
        Integer author = comment.getAuthor();
        String authorImage = comment.getAuthorImage();
        String authorFirstName = comment.getAuthorFirstName();
        Long createdAt = comment.getCreatedAt();
        Integer pk = comment.getPk();
        String text = comment.getText();
        return new CommentDTO(author, authorImage, authorFirstName, createdAt, pk, text);
    }


    public Comment toComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setAuthor(commentDTO.getAuthor());
        comment.setAuthorImage(commentDTO.getAuthorImage());
        comment.setAuthorFirstName(commentDTO.getAuthorFirstName());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        comment.setPk(commentDTO.getPk());
        comment.setText(commentDTO.getText());
        return comment;
    }

}
