package com.daminhluxa.demoLuuXa.repository;

import com.daminhluxa.demoLuuXa.entity.Dormitory;
import com.daminhluxa.demoLuuXa.entity.Major;
import com.daminhluxa.demoLuuXa.entity.SpiritualGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpiritualGuideRepository extends JpaRepository<SpiritualGuide, String> {
    boolean existsByDormitory(Dormitory dorm);
}
