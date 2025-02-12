package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentsNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

/**
 * {@link Класс} CommentServiceImpl реализации логики работы с комментариями <br>
 *
 * @author Chowo
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final AdServiceImpl adService;

    /**
     * Метод для получения всех комментариев объявления.
     *
     * @param adId идентификатор объявления.
     * @return Dto CommentsDTO.
     * @throws CommentsNotFoundException выбрасывается, если по id объявления нет комментариев.
     */
    @Override
    public CommentsDTO getComments(Integer adId) {
        List<Comment> comments = commentsRepository.getAllByAdId(adId).orElseThrow(()-> new CommentsNotFoundException(adId));
        return CommentMapper.toCommentsDTO(comments);
    }

    /**
     * Метод для записи комментария для объявления.
     *
     * @param adId идентификатор объявления.
     * @param createOrUpdateCommentDTO Dto.
     * @param principal объект аутентификации.
     * @return Dto CommentsDTO.
     * @throws AdNotFoundException выбрасывается, если по id объявления нет объявлений.
     * @throws UserNotFoundException выбрасывается, если по имени пользователя нет пользователя в БД.
     */
    @Override
    public CommentDTO postComment(Integer adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO, Principal principal) {
        Ad ad = adRepository.findById(adId).orElseThrow(()-> new AdNotFoundException(adId));
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(()-> new UserNotFoundException(principal.getName()));
        Comment comment = CommentMapper.createComment(createOrUpdateCommentDTO, ad, user);
        commentsRepository.save(comment);
        return CommentMapper.commentToDto(comment);
    }

    /**
     * Метод удаления комментария для объявления.
     *
     * @param adId идентификатор объявления.
     * @param commentId идентификатор комментария.
     */
    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        commentsRepository.deleteById(commentId);
    }

    /**
     * Метод обновления комментария для объявления.
     *
     * @param adId идентификатор объявления.
     * @param commentId идентификатор комментария.
     * @param createOrUpdateCommentDTO Dto комментария.
     * @throws CommentsNotFoundException выбрасывается, если по id объявления нет комметариев.
     */
    @Override
    public CommentDTO updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Comment comment = commentsRepository.findById(commentId).orElseThrow(()->new CommentsNotFoundException(adId));
        comment.setText(createOrUpdateCommentDTO.getText());
        commentsRepository.save(comment);
        return CommentMapper.commentToDto(comment);
    }

    /**
     * Метод нахождения комментария.
     *
     * @param commentId идентификатор комментария.
     * @throws CommentsNotFoundException выбрасывается, если комментарий отсутствует.
     */
    public String getCommentUserName(Integer commentId) {
        return commentsRepository.findById(commentId).orElseThrow(CommentsNotFoundException::new).getAuthor().getUsername();
    }
}
