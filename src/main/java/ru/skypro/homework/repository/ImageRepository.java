package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Image;

import java.util.Optional;

/**
 * @author Chowo
 */
public interface ImageRepository extends JpaRepository<Image, Integer> {

    Optional<Image> findByImagePath(String imagePath);
}
