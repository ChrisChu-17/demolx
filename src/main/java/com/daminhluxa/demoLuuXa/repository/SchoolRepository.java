package com.daminhluxa.demoLuuXa.repository;

import com.daminhluxa.demoLuuXa.entity.Role;
import com.daminhluxa.demoLuuXa.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, String> {
    Optional<School> findByName(String name);
}
