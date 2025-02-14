package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comment.CommentDTO;
import ru.skypro.homework.dto.comment.CommentsDTO;
import ru.skypro.homework.dto.comment.CreateOrUpdateCommentDTO;

import java.security.Principal;

public interface CommentService {

    public CommentsDTO getComments(Integer adId);

    public CommentDTO postComment(Integer adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO, Principal principal);

    public void deleteComment(Integer adId, Integer commentId);

    public CommentDTO updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO);

}
