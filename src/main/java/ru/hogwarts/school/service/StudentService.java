package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    Student create (Student student);
    Student read (long id);
    Student update (Student student);
    Student delete (long id);
    List<Student> readAllByAge(int age);
    public List<Student> readByAgeBetween(int ageMin, int ageMax);
    Faculty readByFaculty(long id);
}
