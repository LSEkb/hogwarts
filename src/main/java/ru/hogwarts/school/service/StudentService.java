package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {

    Student create (Student student);
    Student read (long id);
    Student update (Student student);
    Student delete (long id);
    List<Student> readAllByAge(int age);
    public List<Student> findByAgeBetween(int ageMin, int ageMax);
}
