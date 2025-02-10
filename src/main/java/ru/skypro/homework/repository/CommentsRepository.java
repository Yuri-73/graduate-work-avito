package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

import java.util.List;
import java.util.Optional;

/**
 * @author Chowo
 */
@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    public Optional<List<Comment>> getAllByAdId(Integer adId);

}
