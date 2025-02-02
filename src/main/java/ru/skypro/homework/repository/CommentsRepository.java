package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

/**
 * @author Chowo
 */
@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
