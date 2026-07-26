package com.example.sms.controller;

import com.example.sms.model.Student;
import com.example.sms.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    // Constructor (runs once when app starts)
    // It initializes the service to handle business logic
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    // POST endpoint - Create a new student
    // URL: POST http://localhost:8080/api/students
    // Body: JSON with student details (name, email, course)
    @PostMapping
    public ResponseEntity<Student> createStudent(
            @RequestBody Student student) {

        System.out.println("Student received from frontend:");

        // Call service layer to save the student in database
        Student savedStudent =
                studentService.createStudent(student);

        // Return the saved student with HTTP 201 (CREATED) status
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStudent);
    }

    // GET endpoint - Get all students
    // URL: GET http://localhost:8080/api/students
    // Returns a list of all students from database
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // PUT endpoint - Update an existing student
    // URL: PUT http://localhost:8080/api/students/1
    // @PathVariable: The ID number in the URL (e.g., 1, 2, 3...)
    // @RequestBody: New student details to update
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {

        System.out.println("Updating student with ID: " + id);

        // Call service layer to update the student
        Student updatedStudent = studentService.updateStudent(id, student);

        // If student found and updated, return updated student with 200 (OK) status
        if(updatedStudent != null){
            return ResponseEntity.ok(updatedStudent);
        }

        // If student not found, return 404 (NOT FOUND) status
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    // DELETE endpoint - Delete a student
    // URL: DELETE http://localhost:8080/api/students/1
    // @PathVariable: The ID number in the URL (e.g., 1, 2, 3...)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Long id) {

        System.out.println("Deleting student with ID: " + id);

        // Call service layer to delete the student
        boolean isDeleted = studentService.deleteStudent(id);

        // If deletion was successful, return success message with 200 (OK) status
        if(isDeleted){
            return ResponseEntity.ok("Student with ID " + id + " deleted successfully");
        }

        // If student not found, return 404 (NOT FOUND) status
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Student with ID " + id + " not found");
    }
}