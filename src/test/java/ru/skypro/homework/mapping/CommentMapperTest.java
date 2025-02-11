
package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CommentMapperTest {
    @Spy
    private CommentMapper commentMapper;

    @Test
    public void commentToDtoTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        Image image = new Image();
        image.setImagePath("/avatar");
        image.setId(1);
        user.setImage(image);

        Ad ad = new Ad();
        ad.setTitle(" ");
        ad.setPrice(100);
        ad.setDescription("Свежий333");
        ad.setImage(image);
        ad.setUser(user);

        Comment comment = new Comment();
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setCreatedAt(localDateTime);
        comment.setPk(1);
        comment.setText("покупаю");
        comment.setAuthor(user);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCreatedAt(localDateTime);
        commentDTO.setPk(1);
        commentDTO.setText("покупаю");
        commentDTO.setAuthor(1);
        commentDTO.setAuthorFirstName("Ivan");
        commentDTO.setAuthorImage("/images/" + comment.getAuthor().getImage().getId());

        assertThat(commentMapper.commentToDto(comment)).isEqualTo(commentDTO);
    }

    @Test
    public void comment_Null_ToDtoTest() {
        Comment comment = null;

        CommentDTO commentDTO = new CommentDTO();

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> commentMapper.commentToDto(comment));
    }

    @Test
    public void commentDtoToCommentTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        Image image = new Image();
        image.setImagePath("/avatar");
        image.setId(1);
        user.setImage(image);

        Ad ad = new Ad();
        ad.setTitle(" ");
        ad.setPrice(100);
        ad.setDescription("Свежий333");
        ad.setImage(image);
        ad.setUser(user);

        LocalDateTime localDateTime = LocalDateTime.now();

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCreatedAt(localDateTime);
        commentDTO.setPk(1);
        commentDTO.setText("покупаю");
        commentDTO.setAuthor(1);
        commentDTO.setAuthorFirstName("Ivan");
        commentDTO.setAuthorImage("/images/1");

        Comment comment = new Comment();

        comment.setCreatedAt(localDateTime);
        comment.setPk(1);
        comment.setText("покупаю");
        comment.setAuthor(user);

        assertThat(commentMapper.commentDtoToComment(commentDTO)).isEqualTo(comment);
    }

    @Test
    public void commentDto_Null_ToCommentTest() {
        CommentDTO commentDTO = null;
        Comment comment = null;

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> commentMapper.commentDtoToComment(commentDTO));
    }

    @Test
    public void toCommentsDTOTest() {

        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        Image image = new Image();
        image.setImagePath("/avatar");
        image.setId(1);
        user.setImage(image);

        Ad ad = new Ad();
        ad.setTitle(" ");
        ad.setPrice(100);
        ad.setDescription("Свежий333");
        ad.setImage(image);
        ad.setUser(user);

        LocalDateTime localDateTime = LocalDateTime.now();

        Comment comment = new Comment();

        comment.setCreatedAt(localDateTime);
        comment.setPk(1);
        comment.setText("покупаю");
        comment.setAuthor(user);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCount(comments.size());
        commentsDTO.setResults(comments.stream().map(comment1 -> commentMapper.commentToDto(comment)).collect(Collectors.toList()));
    }

    @Test
    public void createCommentTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        Image image = new Image();
        image.setImagePath("/avatar");
        image.setId(1);
        user.setImage(image);

        Ad ad = new Ad();
        ad.setTitle(" ");
        ad.setPrice(100);
        ad.setDescription("Свежий333");
        ad.setImage(image);
        ad.setUser(user);

        LocalDateTime localDateTime = LocalDateTime.now();

        Comment comment = new Comment();
        comment.setCreatedAt(localDateTime);
        comment.setPk(1);
        comment.setText("покупаю");
        comment.setAuthor(user);

        CreateOrUpdateCommentDTO commentDTO = new CreateOrUpdateCommentDTO();
        commentDTO.setText("покупаю");

        assertThat(commentMapper.createComment(commentDTO, ad, user)).isEqualTo(comment);
    }

    @Test
    public void createComment_Null_Test() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        Image image = new Image();
        image.setImagePath("/avatar");
        image.setId(1);
        user.setImage(image);

        Ad ad = new Ad();
        ad.setTitle(" ");
        ad.setPrice(100);
        ad.setDescription("Свежий333");
        ad.setImage(image);
        ad.setUser(user);

        LocalDateTime localDateTime = LocalDateTime.now();

        Comment comment = new Comment();
        comment.setCreatedAt(localDateTime);
        comment.setPk(1);
        comment.setText("покупаю");
        comment.setAuthor(user);

        CreateOrUpdateCommentDTO commentDTO = null;

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> commentMapper.createComment(commentDTO, ad, user));
    }
}

