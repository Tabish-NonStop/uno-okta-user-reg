package com.learning.okta.repository;

import com.learning.okta.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Student, Integer> {
}
