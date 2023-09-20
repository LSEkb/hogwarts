package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    StudentRepository studentRepository;

    Faculty faculty = new Faculty(1L, "Griffindor", "red");

    @AfterEach
    void afterEach() {
        facultyRepository.deleteAll();
        studentRepository.deleteAll();
    }

    @Test
    void create__returnStatus200() {
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                faculty,
                Faculty.class);
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        assertEquals(faculty.getName(), Objects.requireNonNull(facultyResponseEntity.getBody()).getName());
        assertEquals(faculty.getColor(), Objects.requireNonNull(facultyResponseEntity.getBody()).getColor());
    }

    @Test
    void read_StudentNotInDatabase_returnStatus400AndException() {
        ResponseEntity<String> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty" + "/" + faculty.getId(),
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, facultyResponseEntity.getStatusCode());
        assertEquals("This faculty was not found in the database", facultyResponseEntity.getBody());
    }

    @Test
    void update__returnStatus200() {
        Faculty f = facultyRepository.save(faculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(f),
                Faculty.class);
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        assertEquals(faculty.getName(), Objects.requireNonNull(facultyResponseEntity.getBody()).getName());
        assertEquals(faculty.getColor(), Objects.requireNonNull(facultyResponseEntity.getBody()).getColor());
    }

    @Test
    void delete__returnStatus200() {
        Faculty f = facultyRepository.save(faculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + f.getId(),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Faculty.class);
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
    }

    @Test
    void readByColor__returnStatus200AndFaculty() {
        facultyRepository.save(faculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/color/" + faculty.getColor(),
                Faculty.class);
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        assertEquals(faculty.getName(), Objects.requireNonNull(facultyResponseEntity.getBody()).getName());
        assertEquals(faculty.getColor(), Objects.requireNonNull(facultyResponseEntity.getBody()).getColor());
    }

    @Test
    void readByColorOrName__returnStatus400AndException() {
        ResponseEntity<String> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty" + "/read",
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, facultyResponseEntity.getStatusCode());
        assertEquals("This faculty was not found in the database", facultyResponseEntity.getBody());
    }

    @Test
    void readStudentsInFaculty__returnStatus200AndStudentsList() {
        facultyRepository.save(faculty);
        Student student1 = new Student(1L, "Harry", 13);
        Student student2 = new Student(2L, "Ron", 13);
        student1.setFaculty(faculty);
        student2.setFaculty(faculty);
        Student s1 = studentRepository.save(student1);
        Student s2 = studentRepository.save(student2);
        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>(){});
        List<Student> students = responseEntity.getBody();
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(List.of(student1, student2), students);
    }
}