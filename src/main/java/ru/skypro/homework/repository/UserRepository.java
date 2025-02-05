package ru.skypro.homework.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.User;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Yuri-73
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    Optional<User> findByUsername(String username);
}

