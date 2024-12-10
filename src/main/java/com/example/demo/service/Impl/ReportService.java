package com.example.demo.service.Impl;

import com.example.demo.Handlers.ReportNotFoundException;
import com.example.demo.entity.Report;
import com.example.demo.Repository.ReportRepository;
import com.example.demo.service.IReportService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReportService implements IReportService {


    private final ReportRepository reportRepository;

    // Méthode pour récupérer tous les rapports
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // Méthode pour récupérer un rapport par ID
    public Optional<Report> getReportById(long id) {
        return reportRepository.findById(id);
    }

    // Méthode pour créer un nouveau rapport
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    // Méthode pour mettre à jour un rapport existant
    public Report updateReport(long id, Report reportDetails) {
        Optional<Report> existingReport = reportRepository.findById(id);
        if (existingReport.isPresent()) {
            Report report = existingReport.get();
            report.setContenu(reportDetails.getContenu());
            report.setTitle(reportDetails.getTitle());
            report.setDescription(reportDetails.getDescription());
            report.setCreatedDate(reportDetails.getCreatedDate());
            return reportRepository.save(report);
        }
        return null; // ou vous pouvez lancer une exception si vous préférez
    }

    // Méthode pour supprimer un rapport
    public void deleteReport(long id) {
        reportRepository.deleteById(id);
    }
    public Resource getReportFile(Long id) throws ReportNotFoundException {
        // Recherche le rapport dans la base de données
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException("Rapport non trouvé avec l'ID " + id));

        // Chemin ou contenu du fichier
        String filePath = report.getFilePath(); // Supposons que le chemin du fichier est stocké dans la base
        Path file = Paths.get(filePath);

        // Retourne le fichier sous forme de ressource
        try {
            return (Resource) new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erreur lors du chargement du fichier", e);
        }
    }
}
