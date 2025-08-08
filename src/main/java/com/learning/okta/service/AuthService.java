package com.learning.okta.service;

import com.learning.okta.entity.Student;
import com.learning.okta.exception.NoStudentFound;
import com.learning.okta.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements AuthServiceInterface {
    private final AuthRepository authRepository;
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Value("${okta.domain}")
    private String oktaDomain;

    @Value("${spring.okta.apiToken}")
    private String oktaApiToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public String createOktaUser(Student student) {
        String url = oktaDomain + "/api/v1/users?active=true";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(oktaApiToken);

        Map<String, Object> body = Map.of(
                "profile", Map.of(
                        "name", student.getName().split(" ")[0],
                        "email", student.getEmail(),
                        "login",  student.getEmail()
                ),
                "credentials", Map.of(
                        "password", Map.of("value", student.getPassword())
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
        return response.getBody().get("id").toString();
    }

    @Override
    public String welcomeMessage() {
        return "Welcome to this PoC for Registering a user on Okta from Spring Boot Backend";
    }

    @Override
    public String checkSystemStatus() {
        return "System Up and Working";
    }

    @Override
    public Integer countStudents() throws NoStudentFound {
        List<Student> stu;
        stu = authRepository.findAll();
        if(stu.isEmpty()){
            throw new NoStudentFound("No students found");
        }
        return stu.size();
    }

    @Override
    public Student createStudent(Student student) {
        return authRepository.save(student);
    }

    @Override
    public void deleteStudent(Integer studentId) throws NoStudentFound {
        if (authRepository.findById(studentId).isPresent()){
            authRepository.deleteById(studentId);
        } else {
            throw new NoStudentFound("No student with id: " + studentId);
        }
    }

    @Override
    public List<Student> showAllStudents() throws NoStudentFound {
        List<Student> stu;
        stu = authRepository.findAll();
        if (stu.isEmpty()){
            throw new NoStudentFound("No students found");
        }
        return stu;
    }

    @Override
    public Student showStudentById(Integer id) throws NoStudentFound {
        Optional<Student> stu = authRepository.findById(id);
        if (stu.isPresent()) {
            return stu.get();
        } else  {
            throw new NoStudentFound("No student with id " + id);
        }
    }

    @Override
    public Student updateStudent(Student student, int studentId) throws  NoStudentFound {
        Optional<Student> stu = authRepository.findById(studentId);
        if (stu.isPresent()) {
            Student savedStudent = stu.get();
            savedStudent.setName(student.getName());
            savedStudent.setEmail(student.getEmail());
            savedStudent.setPassword(student.getPassword());
            authRepository.save(savedStudent);
            return savedStudent;
        } else  {
            throw new NoStudentFound("No student with id " + studentId);
        }
    }

    @Override
    public Student updateField(Map<String, Object> field, int studentId) throws NoStudentFound {
        Optional<Student> stu = authRepository.findById(studentId);
        if (stu.isPresent()) {
            Student savedStudent = stu.get();
            field.forEach((key, value) ->
            {
                Field getField = ReflectionUtils.findField(Student.class, key);
                if (getField != null) {
                    getField.setAccessible(true);
                    ReflectionUtils.setField(getField, savedStudent, value);
                }
            });
            return authRepository.save(savedStudent);
        } else {
            throw new NoStudentFound("No student with id " + studentId);
        }
    }
}
