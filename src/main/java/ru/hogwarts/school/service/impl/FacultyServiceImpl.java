package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("The Create method was called with data " + faculty);
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("The faculty is already in the database");
        }
        Faculty createdFaculty = facultyRepository.save(faculty);
        logger.info("Returned from the Create method " + createdFaculty);
        return createdFaculty;
    }

    @Override
    public Faculty read(long id) {
        logger.info("The Read method was called with data " + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("This faculty was not found in the database");
        }
        Faculty readedFaculty = faculty.get();
        logger.info("Returned from the Read method " + readedFaculty);
        return readedFaculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("The Update method was called with data " + faculty);
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("This faculty was not found in the database");
        }
        Faculty updatedFaculty = facultyRepository.save(faculty);
        logger.info("Returned from the Update method " + updatedFaculty);
        return updatedFaculty;
    }

    @Override
    public Faculty delete(long id) {
        logger.info("The Delete method was called with data " + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("This faculty was not found in the database");
        }
        facultyRepository.deleteById(id);
        Faculty deletedFaculty = faculty.get();
        logger.info("Returned from the Delete method " + deletedFaculty);
        return deletedFaculty;
    }

    @Override
    public Faculty readAllByColor(String color) {
        logger.info("The ReadAllByColor method was called with data " + color);
        Optional<Faculty> faculty = facultyRepository.findByColor(color);
        if (faculty.isEmpty()) {
            throw new FacultyException("This faculty was not found in the database");
        }
        Faculty readedFaculty = faculty.get();
        logger.info("Returned from the ReadAllByColor method " + readedFaculty);
        return readedFaculty;
    }

    @Override
    public Faculty readByNameOrColor(String name, String color) {
        logger.info("The ReadByNameOrColor method was called with data {} or {} ", color, name);
        Optional<Faculty> faculty = facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        if (faculty.isEmpty()) {
            throw new FacultyException("This faculty was not found in the database");
        }
        Faculty readedFaculty = faculty.get();
        logger.info("Returned from the ReadByNameOrColor method " + readedFaculty);
        return faculty.get();
    }

    @Override
    public List<Student> readStudentsByFaculty(long id) {
        logger.info("The ReadStudentsByFaculty method was called with data " + id);
        if (!facultyRepository.existsById(id)) {
            throw new FacultyException("The faculty with this Id was not found in the database");
        }
        List<Student> students = studentRepository.findByFaculty_id(id);
        logger.info("Returned from the ReadStudentsByFaculty method " + students);
        return students;
    }
}

