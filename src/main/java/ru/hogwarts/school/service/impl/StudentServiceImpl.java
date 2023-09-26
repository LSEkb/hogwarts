package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        logger.info("The Create method was called with data {}", student);
        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("The student is already in the database");
        }
        Student createdStudent = studentRepository.save(student);
        logger.info("Returned from the Create method {}", createdStudent);
        return createdStudent;
    }

    @Override
    public Student read(long id) {
        logger.info("The Read method was called with data {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("This student was not found in the database");
        }
        Student readedStudent = student.get();
        logger.info("Returned from the Read method {}", readedStudent);
        return readedStudent;
    }

    @Override
    public Student update(Student student) {
        logger.info("The Update method was called with data {}", student);
        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("This student was not found in the database");
        }
        Student updatedStudent = studentRepository.save(student);
        logger.info("Returned from the Update method {}", updatedStudent);
        return updatedStudent;
    }

    @Override
    public Student delete(long id) {
        logger.info("The Delete method was called with data {}", id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("This student was not found in the database");
        }
        studentRepository.deleteById(id);
        Student deletedStudent = student.get();
        logger.info("Returned from the Delete method {}", deletedStudent);
        return deletedStudent;
    }

    @Override
    public List<Student> readAllByAge(int age) {
        logger.info("The ReadAllByAge method was called with data {}", age);
        List<Student> students = studentRepository.findByAge(age);
        logger.info("Returned from the ReadAllByAge method {}", students);
        return students;
    }

    @Override
    public List<Student> readByAgeBetween(int ageMin, int ageMax) {
        logger.info("The ReadAllByAgeBetween method was called with data: minimal age {} and maximal age {}", ageMin, ageMax);
        List<Student> students = studentRepository.findByAgeBetween(ageMin, ageMax);
        logger.info("Returned from the ReadAllByAgeBetween method {}", students);
        return students;
    }

    @Override
    public Faculty readFaculty(long id) {
        logger.info("The ReadFaculty method was called with data {}", id);
        Faculty readedFaculty = read(id).getFaculty();
        logger.info("Returned from the ReadFaculty method {}", readedFaculty);
        return readedFaculty;
    }

    @Override
    public Integer totalStudentsInSchool() {
        logger.info("The TotalStudentsInSchool method was called");
        Integer total = studentRepository.totalStudentsInSchool();
        logger.info("Returned from the TotalStudentsInSchool method {}", total);
        return total;
    }

    @Override
    public Integer averageAgeOfStudents() {
        logger.info("The AverageAgeOfStudents method was called");
        Integer average = studentRepository.averageAgeOfStudents();
        logger.info("Returned from the AverageAgeOfStudents method {}", average);
        return average;
    }

    @Override
    public List<Student> lastFiveStudents() {
        logger.info("The LastFiveStudents method was called");
        List<Student> students = studentRepository.lastStudents(5);
        logger.info("Returned from the LastFiveStudents method {}", students);
        return students;
    }

    @Override
    public List<String> readNameWithFirstLetterA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(n -> StringUtils.startsWithIgnoreCase(n, "a"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
    }

    @Override
    public Double averageAgeOfStudentsByStream() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }
}
