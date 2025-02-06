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
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final AdServiceImpl adService;


    @Override
    public CommentsDTO getComments(Integer adId) {
        List<Comment> comments = commentsRepository.getAllByAdId(adId).orElseThrow(()-> new CommentsNotFoundException(adId));
        return CommentMapper.toCommentsDTO(comments);
    }

    @Override
    public CommentDTO postComment(Integer adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Ad ad = adRepository.findById(adId).orElseThrow(()-> new AdNotFoundException(adId));
        User user = userRepository.findById(ad.getUser().getId()).orElseThrow(()-> new UserNotFoundException(ad.getUser().getUsername()));
        Comment comment = CommentMapper.createComment(createOrUpdateCommentDTO, ad, user);
        commentsRepository.save(comment);
        return CommentMapper.commentToDto(comment);
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
//        commentsRepository.deleteComment(adId, commentId);
        commentsRepository.deleteById(commentId);
    }

    @Override
    public CommentDTO updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Comment comment = commentsRepository.findById(commentId).orElseThrow(()->new CommentsNotFoundException(adId));
        comment.setText(createOrUpdateCommentDTO.getText());
        commentsRepository.save(comment);
        return CommentMapper.commentToDto(comment);
    }
}
