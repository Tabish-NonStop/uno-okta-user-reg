package com.learning.okta.controller;

import com.learning.okta.entity.Student;
import com.learning.okta.exception.NoStudentFound;
import com.learning.okta.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String welcomeMessage() {
        return authService.welcomeMessage();
    }

    @GetMapping("/status")
    public String checkSystemStatus() {
        return authService.checkSystemStatus();
    }

    @GetMapping("/count-students")
    public Integer countEmployees() throws NoStudentFound {
        return authService.countStudents();
    }

    @GetMapping("/all-students")
    public List<Student> showAllStudents() throws NoStudentFound {
        return authService.showAllStudents();
    }

    @GetMapping("/id/{studentId}")
    public Student showStudentById(@PathVariable("studentId") Integer studentId) throws NoStudentFound {
        return authService.showStudentById(studentId);
    }

    @PostMapping("/create-student")
    public Student createStudent(@RequestBody Student student) {
        return authService.createStudent(student);
    }

    @PutMapping("/replace-student/{studentId}")
    public Student replaceStudent(@RequestBody Student student, @PathVariable("studentId") Integer studentId) throws NoStudentFound {
        return authService.updateStudent(student, studentId);
    }

    @PatchMapping("/update-student/{studentId}")
    public Student updateStudent(@RequestBody Map<String, Object> field, @PathVariable("studentId") Integer studentId) throws NoStudentFound {
        return authService.updateField(field, studentId);
    }

    @DeleteMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable("studentId") Integer studentId) throws NoStudentFound {
        authService.deleteStudent(studentId);
        return "Deleted student with id: " + studentId;
    }
}
