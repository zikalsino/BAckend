package com.example.demo.Repository;// Importation des bibliothèques nécessaires


import com.example.demo.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
   // List<JobOffer> findByIsActiveTrue();
    List<JobOffer> findByPostedById(Long Id);
   // List<JobOffer> findByPublishedTrue();


    List<JobOffer> findByKeywordAndLocationAndDescription(String keyword, String location, String description);
    List<JobOffer> findByPublierTrue();
    @Query("SELECT j FROM JobOffer j WHERE " +
            "(LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.requirements) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')) AND " +
            "LOWER(j.company) LIKE LOWER(CONCAT('%', :company, '%')) AND " +
            "j.publier = true")
    List<JobOffer> searchJobOffers(
            @Param("keyword") String keyword,
            @Param("location") String location,
            @Param("location") String requirements
    );
}
