package com.urlshortener.service;

import com.urlshortener.model.ClickAnalytics;
import com.urlshortener.repository.AnalyticsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class UrlAnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    public UrlAnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    // Log click details to DB with request info
    public void logClick(String shortCode, HttpServletRequest request) {
        ClickAnalytics click = new ClickAnalytics();
        click.setShortCode(shortCode);
        click.setIp(request.getRemoteAddr());
        click.setUserAgent(request.getHeader("User-Agent"));
        analyticsRepository.save(click);
    }

    // Get clicks count by counting DB rows for shortCode
    public long getClickCount(String shortCode) {
        return analyticsRepository.countByShortCode(shortCode);
    }
}
