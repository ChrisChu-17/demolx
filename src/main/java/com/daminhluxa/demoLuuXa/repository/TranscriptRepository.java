package com.daminhluxa.demoLuuXa.repository;

import com.daminhluxa.demoLuuXa.entity.School;
import com.daminhluxa.demoLuuXa.entity.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TranscriptRepository extends JpaRepository<Transcript, String> {
}
