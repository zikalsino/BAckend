package com.example.demo.Repository;

import com.example.demo.entity.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    List<Competence> findByCandidateId(Long candidateId);
    // Ajoutez des méthodes si nécessaire
}
