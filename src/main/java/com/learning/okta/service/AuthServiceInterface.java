package com.learning.okta.service;

import com.learning.okta.entity.Student;
import com.learning.okta.exception.NoStudentFound;

import java.util.List;
import java.util.Map;

public interface AuthServiceInterface {

    public String welcomeMessage();
    public String checkSystemStatus();
    public Integer countStudents() throws NoStudentFound;
    public Student createStudent(Student student);
    public void deleteStudent(Integer studentId) throws NoStudentFound;
    public List<Student> showAllStudents() throws NoStudentFound;
    public Student showStudentById(Integer id) throws NoStudentFound;
    public Student updateStudent(Student student, int studentId) throws NoStudentFound;
    public Student updateField(Map<String, Object> field, int studentId)  throws NoStudentFound;
}
