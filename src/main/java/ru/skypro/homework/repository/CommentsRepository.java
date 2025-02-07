package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Chowo
 */
@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    @Transactional
    public Optional<List<Comment>> getAllByAdId (Integer adId);

    @Transactional
    @Query(value = "DELETE FROM comments WHERE ad_id = ?1 AND pk = ?2", nativeQuery = true)
    public void deleteComment (@Param("adId") Integer adId, @Param("commentId") Integer commentId);

    @Transactional
    @Query(value = "DELETE FROM comments WHERE pk = ?1", nativeQuery = true)
    public void deleteComment (@Param("commentId") Integer commentId);

    @Transactional
    @Query(value = "SELECT * FROM comments WHERE ad_id = ?1 AND pk = ?2", nativeQuery = true)
    public Optional<Comment> findComment (@Param("adId") Integer adId, @Param("commentId") Integer commentId);

    @Transactional
    @Query(value = "SELECT * FROM comments WHERE pk = ?1", nativeQuery = true)
    public Optional<Comment> findComment (@Param("commentId") Integer commentId);

}
