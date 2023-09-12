package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("The faculty is already in the database");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("This faculty was not found in the database");
        }
        return faculty.get();
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("This faculty was not found in the database");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty delete(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("This faculty was not found in the database");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }

    @Override
    public Faculty readAllByColor(String color) {
        Optional<Faculty> faculty = facultyRepository.findByColor(color);
        if (faculty.isEmpty()) {
            throw new FacultyException("This faculty was not found in the database");
        }
        return faculty.get();
    }

    @Override
    public Faculty readByNameOrColor(String name, String color) {
        Optional<Faculty> faculty = facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        if (faculty.isEmpty()) {
            throw new FacultyException("This faculty was not found in the database");
        }
        return faculty.get();
    }

    @Override
    public List<Student> readStudentsByFaculty(long id) {
        if (!facultyRepository.existsById(id)) {
            throw new FacultyException("The faculty with this Id was not found in the database");
        }
        return studentRepository.findByFaculty_id(id);
    }
}

