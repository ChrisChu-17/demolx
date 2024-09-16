package com.daminhluxa.demoLuuXa.repository;

import com.daminhluxa.demoLuuXa.entity.Student;
import com.daminhluxa.demoLuuXa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
}
