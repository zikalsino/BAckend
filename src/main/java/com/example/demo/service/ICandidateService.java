package com.example.demo.service;

import com.example.demo.Repository.CandidateRepository;
import com.example.demo.entity.Application;
import com.example.demo.entity.Candidate;
import com.example.demo.entity.Competence;
import com.example.demo.entity.Experience;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@Service
public interface ICandidateService {

    List<Candidate> findAllCandidates();
    Candidate updateProfile(Candidate candidate);
    void uploadCV(Long candidateId, String  cvPath);
    public Candidate getCandidateById(Long userId);
    public List<Experience> getExperiencesByCandidateId(Long candidateId);
    public List<Competence> getCompetencesByCandidateId(Long candidateId);

    Application postuler(Long candidateId, Long offerId, MultipartFile cvFile) throws IOException;

    //public List<Candidate> searchCandidats(String cvPath);


}

