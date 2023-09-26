package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @Mock
    StudentRepository studentRepository;
    @Mock
    FacultyRepository facultyRepository;
    @InjectMocks
    FacultyServiceImpl underTest;

    Faculty faculty = new Faculty(1L, "Griffindor", "red");
    Student student = new Student(1L, "Harry", 13);


    @Test
    void create_newFaculty_addAndReturn() {
        when(facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()))
                .thenReturn(Optional.empty());
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty result = underTest.create(faculty);
        assertEquals(faculty, result);
        assertEquals((Long) 1L, result.getId());
    }

    @Test
    void create_repeatedFaculty_throwFacultyException() {
        when(facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()))
                .thenReturn(Optional.of(faculty));
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.create(faculty));
        assertEquals("The faculty is already in the database", result.getMessage());
    }

    @Test
    void read_facultyInDatabase_readAndReturn() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        Faculty result = underTest.read(1);
        assertEquals(faculty, result);
    }

    @Test
    void read_facultyNotInDatabase_throwFacultyException() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.read(1L));
        assertEquals("This faculty was not found in the database", result.getMessage());
    }

    @Test
    void update_facultyInDatabase_updateAndReturn() {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty result = underTest.update(faculty);
        assertEquals(faculty, result);
    }

    @Test
    void update_facultyNotInDatabase_throwFacultyException() {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.empty());
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.update(faculty));
        assertEquals("This faculty was not found in the database", result.getMessage());
    }

    @Test
    void delete_facultyInDatabase_deleteAndReturn() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        doNothing().when(facultyRepository).deleteById(1L);
        Faculty result = underTest.delete(1L);
        assertEquals(faculty, result);
    }

    @Test
    void delete_facultyNotInDatabase_throwFacultyException() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.read(1L));
        assertThrows(FacultyException.class, () -> underTest.read(1L));
        assertEquals("This faculty was not found in the database", result.getMessage());
    }

    @Test
    void readAllByColor_isFacultyWithColor_returnFacultyByColor() {
        when(facultyRepository.findByColor("red")).thenReturn(Optional.of(faculty));
        Faculty result = underTest.readAllByColor("red");
        assertEquals(faculty, result);
    }

    @Test
    void readAllByColor_noFacultyWithColor_returnEmpty() {
        when(facultyRepository.findByColor("red")).thenReturn(Optional.empty());
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.readAllByColor("red"));
        assertThrows(FacultyException.class, () -> underTest.readAllByColor("red"));
        assertEquals("This faculty was not found in the database", result.getMessage());
    }

    @Test
    void readAllByColor_noFacultyInDatabase_returnEmpty() {
        when(facultyRepository.findByColor("red")).thenReturn(Optional.empty());
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.readAllByColor("red"));
        assertThrows(FacultyException.class, () -> underTest.readAllByColor("red"));
        assertEquals("This faculty was not found in the database", result.getMessage());
    }

    @Test
    void readByNameOrColor_isFacultyWithName_returnFaculty() {
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("griffindor", null))
                .thenReturn(Optional.of(faculty));
        Faculty result = underTest.readByNameOrColor("griffindor", null);
        assertEquals(faculty, result);
    }

    @Test
    void readByNameOrColor_isFacultyWithColor_returnFaculty() {
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(null, "red"))
                .thenReturn(Optional.of(faculty));
        Faculty result = underTest.readByNameOrColor(null, "red");
        assertEquals(faculty, result);
    }

    @Test
    void readByNameOrColor_notFacultyWithName_throwFacultyException() {
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase("griffindor", null))
                .thenReturn(Optional.empty());
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.readByNameOrColor("griffindor", null));
        assertThrows(FacultyException.class, () -> underTest.readByNameOrColor("griffindor", null));
        assertEquals("This faculty was not found in the database", result.getMessage());
    }

    @Test
    void readByNameOrColor_notFacultyWithColor_throwFacultyException() {
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(null, "red"))
                .thenReturn(Optional.empty());
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.readByNameOrColor(null, "red"));
        assertThrows(FacultyException.class, () -> underTest.readByNameOrColor(null, "red"));
        assertEquals("This faculty was not found in the database", result.getMessage());
    }

    @Test
    void readStudentsByFaculty_returnListStudent() {
        when(facultyRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.findByFaculty_id(1L)).thenReturn(List.of(student));
        List<Student> result = underTest.readStudentsByFaculty(1L);
        assertEquals(List.of(student), result);
    }

    @Test
    void readStudentsByFaculty_throwFacultyException() {
        when(facultyRepository.existsById(1L)).thenReturn(false);
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.readStudentsByFaculty(1L));
        assertThrows(FacultyException.class, () -> underTest.readStudentsByFaculty(1L));
        assertEquals("The faculty with this Id was not found in the database", result.getMessage());
    }

    @Test
    void longestFacultyName() {

        Faculty faculty1 = new Faculty(2L, "Ravenclaw", "blue");
        List<Faculty> faculties = List.of(faculty, faculty1);
        when(facultyRepository.findAll()).thenReturn(faculties);
        String result = underTest.longestFacultyName();
        assertEquals("Griffindor",result);
    }
}