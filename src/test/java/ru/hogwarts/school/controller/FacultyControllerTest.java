package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    FacultyRepository facultyRepository;
    Faculty faculty = new Faculty(1L, "Griffindor", "red");

    @AfterEach
    void afterEach() {
        facultyRepository.deleteAll();
    }

    @Test
    void create__returnStatus200(){

    }

    @Test
    void read__returnStatus400AndException(){

    }

    @Test
    void update__returnStatus200(){

    }

    @Test
    void delete__returnStatus200(){

    }

    @Test
    void readByColor__returnStatus200AndFaculty(){

    }

    @Test
    void readByColorOrName__returnStatus400AndException(){

    }

    @Test
    void readStudentByFaculty__returnStatus200AndStudentsList(){

    }

}