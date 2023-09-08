package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @Mock
    FacultyRepository facultyRepository;
    @InjectMocks
    FacultyServiceImpl underTest;

    Faculty faculty = new Faculty(1L, "Griffindor", "red");

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
    void readAllByColor_areFacultyWithColor_returnListWithFacultyByAge() {
        when(facultyRepository.findByColor("red")).thenReturn(List.of(faculty));
        List<Faculty> result = underTest.readAllByColor("red");
        assertEquals(new ArrayList<>(Arrays.asList(faculty)), result);
    }

    @Test
    void readAllByColor_noFacultyWithColor_returnEmptyList() {
        when(facultyRepository.findByColor("red")).thenReturn(new ArrayList<>());
        List<Faculty> result = underTest.readAllByColor("red");
        List<Faculty> expected = Collections.<Faculty>emptyList();
        assertEquals(expected, result);
    }

    @Test
    void readAllByColor_noFacultyInDatabase_returnEmptyList() {
        when(facultyRepository.findByColor("red")).thenReturn(new ArrayList<>());
        List<Faculty> result = underTest.readAllByColor("red");
        List<Faculty> expected = Collections.<Faculty>emptyList();
        assertEquals(expected, result);
    }
}