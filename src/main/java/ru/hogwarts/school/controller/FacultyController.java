package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/color/{color}")
    public Faculty readByColor(@PathVariable String color) {
        return facultyService.readAllByColor(color);
    }

    @GetMapping("/read")
    public Faculty readByColorOrName(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String color) {
        return facultyService.readByNameOrColor(name,color);
    }

    @GetMapping("/students")
    public List<Student> readStudentsInFaculty(@RequestParam long id){
        return facultyService.readStudentsByFaculty(id);
    }

    @GetMapping("/name-longest")
    public String longestFacultyName(){
        return facultyService.longestFacultyName();
    }

}