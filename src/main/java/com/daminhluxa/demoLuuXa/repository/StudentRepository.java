package com.daminhluxa.demoLuuXa.repository;

import com.daminhluxa.demoLuuXa.entity.Student;
import com.daminhluxa.demoLuuXa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {
    Page<Student> findByDormitoryId(String name, Pageable pageable);
}
