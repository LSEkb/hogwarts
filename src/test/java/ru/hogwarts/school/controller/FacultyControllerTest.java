package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {FacultyController.class})
public class FacultyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    FacultyController facultyController;
    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    FacultyServiceImpl facultyService;
    @SpyBean
    StudentServiceImpl studentService;
    @Autowired
    ObjectMapper objectMapper;
    Faculty faculty = new Faculty(1L, "Griffindor", "red");

    @Test
    void create() throws Exception {
        when(facultyRepository.findByNameAndColor(anyString(), anyString())).thenReturn(Optional.empty());
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void read() throws Exception {
//        when(facultyRepository.findById(anyLong())).thenReturn(Optional.empty());
//        mockMvc.perform(get("/faculty")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpClientErrorException.BadRequest))
//                .andExpect(result -> assertEquals("This faculty was not found in the database", result.getResolvedException().getMessage()));
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void update() throws Exception {
        when(facultyRepository.save(any())).thenReturn(faculty);
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));
        mockMvc.perform(put("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void delete__returnNewFacultyAndStatus200() throws Exception {
        when(facultyRepository.save(any())).thenReturn(faculty);
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));
        mockMvc.perform(delete("/faculty/" + faculty.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void readAllByColor() throws Exception {
        when(facultyRepository.findByColor(faculty.getColor())).thenReturn(Optional.of(faculty));
        mockMvc.perform(get("/faculty/color/" + faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }

    @Test
    void readAllByColorOrName() throws Exception {
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase
                (faculty.getName(), faculty.getColor())).thenReturn(Optional.of(faculty));
        mockMvc.perform(get("/faculty/read"))
                .andExpect(status().isOk());

    }

    @Test
    void readStudentsInFaculty() throws Exception {
        Student student = new Student(1L, "Harry", 13, faculty);
        when(studentRepository.findByFaculty_id(anyLong())).thenReturn(List.of(student));
        mockMvc.perform(get("/faculty/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(student.getName()))
                .andExpect(jsonPath("$.[0].age").value(student.getAge()));
    }
}