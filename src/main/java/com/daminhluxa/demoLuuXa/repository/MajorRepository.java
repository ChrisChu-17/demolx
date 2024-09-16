package com.daminhluxa.demoLuuXa.repository;

import com.daminhluxa.demoLuuXa.entity.Major;
import com.daminhluxa.demoLuuXa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, String> {
    Optional<Major> findByName(String name);
}
