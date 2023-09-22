package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.Optional;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    Optional<Faculty> findByNameAndColor(String name, String color);

    Optional<Faculty> findByColor(String color);

    Optional<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
