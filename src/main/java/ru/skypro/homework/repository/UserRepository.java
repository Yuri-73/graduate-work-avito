package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.model.User;

/**
 * @author Yuri-73
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}

