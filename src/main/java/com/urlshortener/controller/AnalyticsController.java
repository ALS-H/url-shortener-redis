package com.urlshortener.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortener.service.UrlAnalyticsService;

@RestController
public class AnalyticsController {

    @Autowired
    private UrlAnalyticsService analyticsService;

    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<Map<String, Long>> getClickStats(@PathVariable String shortCode) {
        long count = analyticsService.getClickCount(shortCode);
        return ResponseEntity.ok(Map.of("clicks", count));
    }
}
