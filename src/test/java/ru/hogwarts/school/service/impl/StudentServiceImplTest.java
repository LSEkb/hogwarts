package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;
    @InjectMocks
    StudentServiceImpl underTest;

    Student student = new Student(1L, "Harry", 12, new Faculty(1L, "Griffindor", "red"));

    @Test
    void create_newStudent_addAndReturn() {
        when(studentRepository.findByNameAndAge(student.getName(), student.getAge()))
                .thenReturn(Optional.empty());
        when(studentRepository.save(student)).thenReturn(student);
        Student result = underTest.create(student);
        assertEquals(student, result);
        assertEquals((Long) 1L, result.getId());
    }

    @Test
    void create_repeatedStudent_throwStudentException() {
        when(studentRepository.findByNameAndAge(student.getName(), student.getAge()))
                .thenReturn(Optional.of(student));
        StudentException result = assertThrows(StudentException.class, () -> underTest.create(student));
        assertEquals("The student is already in the database", result.getMessage());
    }

    @Test
    void read_studentInDatabase_readAndReturn() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student result = underTest.read(1);
        assertEquals(student, result);
    }

    @Test
    void read_studentNotInDatabase_throwStudentException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        StudentException result = assertThrows(StudentException.class, () -> underTest.read(1L));
        assertEquals("This student was not found in the database", result.getMessage());
    }

    @Test
    void update_studentInDatabase_updateAndReturn() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);
        Student result = underTest.update(student);
        assertEquals(student, result);
    }

    @Test
    void update_studentNotInDatabase_throwStudentException() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
        StudentException result = assertThrows(StudentException.class, () -> underTest.update(student));
        assertEquals("This student was not found in the database", result.getMessage());
    }

    @Test
    void delete_studentInDatabase_deleteAndReturn() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).deleteById(1L);
        Student result = underTest.delete(1L);
        assertEquals(student, result);
    }

    @Test
    void delete_studentNotInDatabase_throwStudentException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        StudentException result = assertThrows(StudentException.class, () -> underTest.read(1L));
        assertThrows(StudentException.class, () -> underTest.read(1L));
        assertEquals("This student was not found in the database", result.getMessage());
    }

    @Test
    void readAllByAge_areStudentWithAge_returnListWithStudentByAge() {
        when(studentRepository.findByAge(12)).thenReturn(List.of(student));
        List<Student> result = underTest.readAllByAge(12);
        assertEquals(List.of(student), result);
    }

    @Test
    void readAllByAge_noStudentWithAge_returnEmptyList() {
        when(studentRepository.findByAge(12)).thenReturn(new ArrayList<>());
        List<Student> result = underTest.readAllByAge(12);
        assertEquals(Collections.<Student>emptyList(), result);
    }

    @Test
    void readByAgeBetween_isStudentWithThisAge_returnListStudentsWithThisAge() {
        when(studentRepository.findByAgeBetween(10, 14)).thenReturn(List.of(student));
        List<Student> result = underTest.readByAgeBetween(10, 14);
        assertEquals(List.of(student), result);
    }

    @Test
    void readByAgeBetween_notStudentWithThisAge_returnEmptyList() {
        when(studentRepository.findByAgeBetween(10, 14)).thenReturn(new ArrayList<>());
        List<Student> result = underTest.readByAgeBetween(10, 14);
        assertEquals(Collections.<Student>emptyList(), result);
    }

    @Test
    void readFaculty_isStudentWithThisId_returnFacultyOfStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Faculty result = underTest.readFaculty(1L);
        assertEquals(new Faculty(1L, "Griffindor", "red"), result);
    }

    @Test
    void readFaculty_notStudentWithThisId_throwStudentException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        StudentException result = assertThrows(StudentException.class, () -> underTest.read(1L));
        assertThrows(StudentException.class, () -> underTest.readFaculty(1L));
        assertEquals("This student was not found in the database", result.getMessage());
    }
}

