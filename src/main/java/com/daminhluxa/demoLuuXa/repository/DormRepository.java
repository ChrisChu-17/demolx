package com.daminhluxa.demoLuuXa.repository;

import com.daminhluxa.demoLuuXa.entity.Dormitory;
import com.daminhluxa.demoLuuXa.entity.SpiritualGuide;
import com.daminhluxa.demoLuuXa.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DormRepository extends JpaRepository<Dormitory, String>, JpaSpecificationExecutor<Dormitory> {
    Optional<Dormitory> findByName(String name);
    boolean existsBySpiritualGuide(SpiritualGuide guide);

    @Query("SELECT d FROM Dormitory d WHERE " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.area) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "d.contactInfo.primaryPhone LIKE CONCAT('%', :keyword, '%')")
    Page<Dormitory> searchDorm(String keyword, Pageable pageable);
}
