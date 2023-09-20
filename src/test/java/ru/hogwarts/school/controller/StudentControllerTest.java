package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;

    Student student = new Student(1L, "Harry", 13);
    Faculty faculty = new Faculty(1L, "Griffindor", "red");

    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void create__returnStatus200() {
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), Objects.requireNonNull(studentResponseEntity.getBody()).getName());
        assertEquals(student.getAge(), Objects.requireNonNull(studentResponseEntity.getBody()).getAge());
    }

    @Test
    void read_StudentNotInDatabase_returnStatus400AndException() {
        ResponseEntity<String> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId(),
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, studentResponseEntity.getStatusCode());
        assertEquals("This student was not found in the database", studentResponseEntity.getBody());
    }

    @Test
    void update__returnStatus200() {
        Student s = studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(s),
                Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(s.getName(), Objects.requireNonNull(studentResponseEntity.getBody()).getName());
        assertEquals(s.getAge(), Objects.requireNonNull(studentResponseEntity.getBody()).getAge());
    }

    @Test
    void delete__returnStatus200() {
        Student s = studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + s.getId(),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
    }

    @Test
    void readAllAge__returnStatus200AndStudentList() {
        Student s = studentRepository.save(student);
        ResponseEntity<List<Student>> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/age",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<Student>>(){});
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(List.of(s), studentResponseEntity.getBody());

    }

    @Test
    void readFaculty__returnStatus200AndFaculty() {
        Faculty f = facultyRepository.save(faculty);
        student.setFaculty(f);
        Student s = studentRepository.save(student);
        ResponseEntity<Faculty> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + s.getId() + "/faculty",
                Faculty.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getFaculty(), studentResponseEntity.getBody());
    }
}