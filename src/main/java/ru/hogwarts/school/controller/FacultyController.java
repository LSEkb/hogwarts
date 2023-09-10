package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("/{id}")
    public Faculty read(@PathVariable long id) {
        return facultyService.read(id);
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return facultyService.update(faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty delete(@PathVariable long id) {
        return facultyService.delete(id);
    }

    @GetMapping("/color/{color}/{name}")
    public Faculty readAll(@PathVariable String color, @PathVariable(required = false) String name) {
//        if ((name == null || name.isBlank()) && (color == null || color.isBlank())) {
//            ResponseEntity.status(400);
//        }

//        facultyService.readAllByColor(color)
        if (name != null && !name.isBlank() || color != null && !color.isBlank()) {
            return facultyService.findByNameOrColor(color, name);
        }
        throw new FacultyException("Не задан параметр поиска");
    }
}
