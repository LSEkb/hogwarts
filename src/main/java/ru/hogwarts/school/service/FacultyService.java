package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {
    Faculty create (Faculty faculty);
    Faculty read (long id);
    Faculty update (Faculty faculty);
    Faculty delete (long id);
    Faculty readAllByColor(String color);
    Faculty readByNameOrColor(String name, String color);
    List<Student> readStudentsByFaculty(long id);

    String longestFacultyName();
}
