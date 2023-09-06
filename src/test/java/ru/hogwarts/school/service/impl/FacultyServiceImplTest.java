package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {

    FacultyServiceImpl underTest = new FacultyServiceImpl();

    Faculty faculty1 = new Faculty(0L, "Griffindor", "red");
    Faculty faculty2 = new Faculty(0L, "Ravenclaw", "blue");
    Faculty faculty3 = new Faculty(0L, "Slytherin", "green");


    @Test
    void create_newFaculty_addAndReturn(){
        Faculty result = underTest.create(faculty1);
        assertEquals((Long) 1L,result.getId());
    }

    @Test
    void create_repeatedFaculty_trowFacultyException() {
        underTest.create(faculty1);
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.create(faculty1));
        assertEquals("The faculty is already in the database", result.getMessage());
    }

    @Test
    void read_facultyInDatabase_readAndReturn() {
        underTest.create(faculty1);
        Faculty result = underTest.read(1);
        assertEquals(faculty1, result);
    }

    @Test
    void read_facultyNotInDatabase_trowFacultyException() {
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.read(2));
        assertEquals("This faculty was not found in the database", result.getMessage());
    }

    @Test
    void update_studentInDatabase_updateAndReturn() {
        underTest.create(faculty1);
        Faculty result = underTest.update(faculty1);
        assertEquals(faculty1, result);
    }

    @Test
    void update_studentNotInDatabase_trowFacultyException() {
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.update(faculty1));
        assertEquals("This faculty was not found in the database", result.getMessage());
    }

    @Test
    void delete_studentInDatabase_deleteAndReturn() {
        underTest.create(faculty1);
        Faculty result = underTest.delete(1);
        assertEquals(faculty1, result);
        assertThrows(FacultyException.class, () -> underTest.read(1));
    }

    @Test
    void delete_studentNotInDatabase_trowFacultyException() {
        FacultyException result = assertThrows(FacultyException.class, () -> underTest.read(1));
        assertEquals("This faculty was not found in the database", result.getMessage());
    }

    @Test
    void readAll_areFacultyWithAge_returnListWithFacultyByAge() {
        underTest.create(faculty1);
        underTest.create(faculty2);
        underTest.create(faculty3);
        List<Faculty> result = underTest.readAll("red");
        assertEquals(new ArrayList<>(Arrays.asList(faculty1)), result);
    }

    @Test
    void readAll_noFacultyWithAge_returnEmptyList() {
        underTest.create(faculty1);
        List<Faculty> result = underTest.readAll("yellow");
        List<Faculty> expected = Collections.<Faculty>emptyList();
        assertEquals(expected,result);
    }

    @Test
    void readAll_noFacultyInDatabase_returnEmptyList() {
        List<Faculty> result = underTest.readAll("red");
        List<Faculty> expected = Collections.<Faculty>emptyList();
        assertEquals(expected,result);
    }

}