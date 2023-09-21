package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    StudentController studentController;
    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    StudentServiceImpl facultyService;
    @Autowired
    ObjectMapper objectMapper;
    Student student = new Student(1L, "Harry", 13);

    @Test
    void create__returnStatus200() throws Exception {
        when(studentRepository.findByNameAndAge(student.getName(), student.getAge())).thenReturn(Optional.empty());
        when(studentRepository.save(any())).thenReturn(student);
        mockMvc.perform(post("/student")
                        .content(objectMapper.writeValueAsBytes(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void read__returnFacultyAndStatus200() throws Exception {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        mockMvc.perform(get("/student/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void update__returnNewFacultyAndStatus200() throws Exception {
        when(studentRepository.save(any())).thenReturn(student);
        when(studentRepository.findById(any())).thenReturn(Optional.of(student));
        mockMvc.perform(put("/student")
                        .content(objectMapper
                                .writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void delete__returnNewFacultyAndStatus200() throws Exception {
        when(studentRepository.findById(any())).thenReturn(Optional.of(student));
        mockMvc.perform(delete("/student/" + student.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void readAllAge__() throws Exception {
        when(studentRepository.findByAge(student.getAge())).thenReturn(List.of(student));
        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(List.of(student));
        mockMvc.perform(get("/student/age"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(student.getName()))
                .andExpect(jsonPath("$.[0].age").value(student.getAge()));
    }

    @Test
    void readFaculty__() throws Exception {
        Faculty f = new Faculty(1L,"Griffindor","red");
        student.setFaculty(f);
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        mockMvc.perform(get("/student/" + student.getId() + "/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getFaculty().getName()))
                .andExpect(jsonPath("$.color").value(student.getFaculty().getColor()));
    }
}