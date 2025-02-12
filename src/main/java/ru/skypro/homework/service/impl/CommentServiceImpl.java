package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    /**
     * Метод для получения всех комментариев объявления.
     *
     * @param adId идентификатор объявления.
     * @return Dto CommentsDTO.
     * @throws CommentsNotFoundException выбрасывается, если по id объявления нет комментариев.
     */
    @Override
    public CommentsDTO getComments(Integer adId) {
        logger.info("CommentService getComments is running");
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
        logger.info("CommentService postComment is running");
        Ad ad = adRepository.findById(adId).orElseThrow(()-> new AdNotFoundException(adId));
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(()-> new UserNotFoundException(principal.getName()));
        Comment comment = CommentMapper.createComment(createOrUpdateCommentDTO, ad, user);
        commentsRepository.save(comment);
        logger.info("Comment [{}] successfully created for Ad [{}]", comment.getPk(), adId);
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
        logger.info("CommentService deleteComment is running");
        commentsRepository.deleteById(commentId);
        logger.info("Comments with id: {} successfully deleted for ad with id: {}", commentId, adId);
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
        logger.info("CommentService updateComment is running");
        Comment comment = commentsRepository.findById(commentId).orElseThrow(()->new CommentsNotFoundException(adId));
        comment.setText(createOrUpdateCommentDTO.getText());
        commentsRepository.save(comment);
        logger.info("Comment [{}] successfully created for Ad [{}]", comment.getPk(), adId);
        return CommentMapper.commentToDto(comment);
    }

    /**
     * Метод нахождения комментария.
     *
     * @param commentId идентификатор комментария.
     * @throws CommentsNotFoundException выбрасывается, если комментарий отсутствует.
     */
    public String getCommentUserName(Integer commentId) {
        logger.info("CommentService getCommentUserName is running");
        return commentsRepository.findById(commentId).orElseThrow(CommentsNotFoundException::new).getAuthor().getUsername();
    }
}
