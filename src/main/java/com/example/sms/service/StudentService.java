package com.example.sms.service;

import com.example.sms.model.Student;
import com.example.sms.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){

        System.out.println("Inside Service Layer");

        return studentRepository.save(student);
    }
}