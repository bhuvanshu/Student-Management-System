package com.example.sms.service;

import com.example.sms.model.Student;
import com.example.sms.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo){
        this.repo = repo;
    }

    // Method to create a new student in database
    public Student createStudent(Student student){

        System.out.println("Inside Service Layer");

        return repo.save(student);
    }

    // Method to get all students from database
    public List<Student> getAllStudents(){
        return repo.findAll();
    }

    // Method to update an existing student by their ID
    // This method finds the student by ID and updates their details
    public Student updateStudent(Long id, Student updatedStudent){
        
        // Find the student by ID from database
        Optional<Student> existingStudent = repo.findById(id);
        
        // If student exists, update their information
        if(existingStudent.isPresent()){
            Student student = existingStudent.get();
            
            // Update the name if new name is provided
            if(updatedStudent.getName() != null){
                student.setName(updatedStudent.getName());
            }
            
            // Update the email if new email is provided
            if(updatedStudent.getEmail() != null){
                student.setEmail(updatedStudent.getEmail());
            }
            
            // Update the course if new course is provided
            if(updatedStudent.getCourse() != null){
                student.setCourse(updatedStudent.getCourse());
            }
            
            // Save the updated student to database
            return repo.save(student);
        }
        
        // Return null if student not found
        return null;
    }

    // Method to delete a student by their ID
    // This method removes the student record from database
    public boolean deleteStudent(Long id){
        
        // Check if student exists in database
        if(repo.existsById(id)){
            // Delete the student using their ID
            repo.deleteById(id);
            return true; // Return true if deletion was successful
        }
        
        // Return false if student does not exist
        return false;
    }
}