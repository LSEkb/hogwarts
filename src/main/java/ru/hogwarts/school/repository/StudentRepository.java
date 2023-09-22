package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByNameAndAge(String name, int age);

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int ageMin, int ageMax);

    List<Student> findByFaculty_id(long id);

    @Query(value = "select count (s) from Student s")
    Integer totalStudentsInSchool();

    @Query(value = "select avg (s.age) from Student s")
    Integer averageAgeOfStudents();

    @Query(value = "select * from Student s order by (s.id) desc limit :lasts", nativeQuery = true)
    List<Student> lastStudents(int lasts);
}
