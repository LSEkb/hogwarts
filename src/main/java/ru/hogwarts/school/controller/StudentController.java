package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Student read(@PathVariable long id) {
        return studentService.read(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping("/read/age,age2")
    public List<Student> readAll(@RequestParam int age, @RequestParam(defaultValue = "0") int age2) {
        if (age2==0) {
            return studentService.readAllByAge(age);
        }
        return studentService.readByAgeBetween(age, age2);
    }

    @GetMapping("/{id}/faculty")
    public Faculty readFaculty(@PathVariable long id){
        return studentService.readFaculty(id);
    }
}
