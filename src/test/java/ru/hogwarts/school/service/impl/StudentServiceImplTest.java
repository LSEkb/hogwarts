package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Student;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    /*StudentServiceImpl underTest = new StudentServiceImpl();

    Student student1 = new Student(0L, "Harry", 12);
    Student student2 = new Student(0L, "Ron", 12);
    Student student3 = new Student(0L, "Cedric", 15);

    @Test
    void create_newStudent_addAndReturn() {
        Student result = underTest.create(student1);
        assertEquals((Long) 1L, result.getId());
        assertEquals(student1, result);
    }

    @Test
    void create_repeatedStudent_trowStudentException() {
        underTest.create(student1);
        StudentException result = assertThrows(StudentException.class, () -> underTest.create(student1));
        assertEquals("The student is already in the database", result.getMessage());
    }

    @Test
    void read_studentInDatabase_readAndReturn() {
        underTest.create(student1);
        Student result = underTest.read(1);
        assertEquals(student1, result);
    }

    @Test
    void read_studentNotInDatabase_trowStudentException() {
        StudentException result = assertThrows(StudentException.class, () -> underTest.read(2));
        assertEquals("This student was not found in the database", result.getMessage());
    }

    @Test
    void update_studentInDatabase_updateAndReturn() {
        underTest.create(student1);
        Student result = underTest.update(student1);
        assertEquals(student1, result);
    }

    @Test
    void update_studentNotInDatabase_trowStudentException() {
        StudentException result = assertThrows(StudentException.class, () -> underTest.update(student1));
        assertEquals("This student was not found in the database", result.getMessage());
    }

    @Test
    void delete_studentInDatabase_deleteAndReturn() {
        underTest.create(student1);
        Student result = underTest.delete(1);
        assertEquals(student1, result);
        assertThrows(StudentException.class, () -> underTest.read(1));
    }

    @Test
    void delete_studentNotInDatabase_trowStudentException() {
        StudentException result = assertThrows(StudentException.class, () -> underTest.read(1));
        assertEquals("This student was not found in the database", result.getMessage());
    }

    @Test
    void readAll_areStudentWithAge_returnListWithStudentByAge() {
        underTest.create(student1);
        underTest.create(student2);
        underTest.create(student3);
        List<Student> result = underTest.readAll(12);
        assertEquals(new ArrayList<>(Arrays.asList(student1, student2)), result);
    }

    @Test
    void readAll_noStudentWithAge_returnEmptyList() {
        underTest.create(student1);
        List<Student> result = underTest.readAll(14);
        List<Student> expected = Collections.<Student>emptyList();
        assertEquals(expected,result);
    }

    @Test
    void readAll_noStudentInDatabase_returnEmptyList() {
        List<Student> result = underTest.readAll(12);
        List<Student> expected = Collections.<Student>emptyList();
        assertEquals(expected,result);
    }

     */
}

