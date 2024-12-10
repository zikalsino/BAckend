package com.example.demo.Repository;

import com.example.demo.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByCandidateId(Long candidateId);

    // Ajoutez des méthodes si nécessaire
}
