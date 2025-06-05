package com.urlshortener.repository;

import com.urlshortener.model.ClickAnalytics;  // Import your entity class
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<ClickAnalytics, Long> {
    long countByShortCode(String shortCode);
}
