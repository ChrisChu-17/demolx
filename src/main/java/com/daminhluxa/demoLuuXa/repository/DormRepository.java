package com.daminhluxa.demoLuuXa.repository;

import com.daminhluxa.demoLuuXa.entity.Dormitory;
import com.daminhluxa.demoLuuXa.entity.SpiritualGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DormRepository extends JpaRepository<Dormitory, String> {
    Optional<Dormitory> findByName(String name);
    boolean existsBySpiritualGuide(SpiritualGuide guide);
}
