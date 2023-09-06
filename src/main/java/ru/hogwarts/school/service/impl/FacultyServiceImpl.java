package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private long counter;


    @Override
    public Faculty create(Faculty faculty) {
        if(facultyMap.containsValue(faculty)){
            throw new FacultyException("The faculty is already in the database");
        }
        faculty.setId(++counter);
        facultyMap.put(faculty.getId(),faculty);
        return faculty;
    }

    @Override
    public Faculty read(long id) {
        if(!facultyMap.containsKey(id)){
            throw new FacultyException("This faculty was not found in the database");
        }
        return facultyMap.get(id);
    }

    @Override
    public Faculty update(Faculty faculty) {
        if(!facultyMap.containsKey(faculty.getId())){
            throw new FacultyException("This faculty was not found in the database");
        }
        facultyMap.put(faculty.getId(),faculty);
        return faculty;
    }

    @Override
    public Faculty delete(long id) {
        Faculty faculty = facultyMap.remove(id);
        if(faculty==null){
            throw new FacultyException("This faculty was not found in the database");
        }
        return faculty;
    }

    @Override
    public List<Faculty> readAll (String color){
        return facultyMap.values().stream()
                .filter(f -> f.getColor().equals(color))
                .toList();
    }
}
