package com.example.sms.controller;

import com.example.sms.model.Student;
import com.example.sms.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    // constructor (runs once when app starts)
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(
            @RequestBody Student student) {

        System.out.println("Student received from frontend:");

        Student savedStudent =
                studentService.createStudent(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStudent);
    }
}