package com.example.demo.controller;

import com.example.demo.Handlers.ReportNotFoundException;
import com.example.demo.entity.Report;

import com.example.demo.service.Impl.ReportService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reports")
public class ReportController {


    private final ReportService reportService;

    // Récupérer tous les rapports
    @GetMapping("/list")
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    // Récupérer un rapport par ID
    @GetMapping("/report/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer un nouveau rapport
    @PostMapping("/create")
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report createdReport = reportService.createReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    // Mettre à jour un rapport existant
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable long id, @RequestBody Report reportDetails) {
        Report updatedReport = reportService.updateReport(id, reportDetails);
        return updatedReport != null ? ResponseEntity.ok(updatedReport) : ResponseEntity.notFound().build();
    }

    // Supprimer un rapport
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadReport(@PathVariable Long id) {
        try {
            // Appelle le service pour récupérer le fichier
            Resource file = reportService.getReportFile(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF) // Type de fichier, à adapter si ce n'est pas un PDF
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report-" + id + ".pdf\"")
                    .body(file);
        } catch (ReportNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
