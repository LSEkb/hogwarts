package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

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

    @GetMapping("/age")
    public List<Student> readAllAge(@RequestParam int age, @RequestParam(defaultValue = "0") int age2) {
        if (age2==0) {
            return studentService.readAllByAge(age);
        }
        return studentService.readByAgeBetween(age, age2);
    }

    @GetMapping("/{id}/faculty")
    public Faculty readFaculty(@PathVariable long id){
        return studentService.readFaculty(id);
    }

    @GetMapping("/total")
    public int totalStudentsInSchool(){
        return studentService.totalStudentsInSchool();
    }

    @GetMapping("/age-average")
    public int averageAgeOfStudents(){
        return studentService.averageAgeOfStudents();
    }

    @GetMapping("/last-five-students")
    public List<Student> lastFiveStudents(){
        return studentService.lastFiveStudents();
    }

    @GetMapping("/name-first-a")
    public List<String> readNameWithFirstCharA(){
        return studentService.readNameWithFirstLetterA();
    }

    @GetMapping("/age-average-stream")
    public Double averageAgeOfStudentsByStream(){
        return studentService.averageAgeOfStudentsByStream();
    }
}
