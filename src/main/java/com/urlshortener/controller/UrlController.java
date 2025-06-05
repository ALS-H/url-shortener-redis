package com.urlshortener.controller;

import com.urlshortener.model.UrlMapping;
import com.urlshortener.service.UrlAnalyticsService;
import com.urlshortener.service.UrlService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlAnalyticsService analyticsService;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shorten(@RequestBody Map<String, String> payload) {
        try {
            String originalUrl = payload.get("url");

            if (originalUrl == null || !originalUrl.matches("^(http|https)://.*$")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid URL format"));
            }

            String shortCode = urlService.shortenUrl(originalUrl);
            return ResponseEntity.ok(Map.of("shortUrl", "http://localhost:8080/" + shortCode));
        } catch (Exception e) {
            e.printStackTrace(); // 🔍 print the error in logs
            return ResponseEntity.status(500).body(Map.of("error", "Internal Server Error"));
        }
    }


    @GetMapping("/{shortCode}")
    public String redirect(@PathVariable String shortCode, HttpServletRequest request, RedirectAttributes attributes) {
        System.out.println("Redirect requested for short code: " + shortCode);  // ✅

        Optional<UrlMapping> mappingOpt = urlService.getOriginalUrl(shortCode);

        if (mappingOpt.isEmpty()) {
            System.out.println("Short code not found: " + shortCode);  // ✅
            return "redirect:/error";
        }

        UrlMapping mapping = mappingOpt.get();

        if (mapping.getExpiresAt() != null && mapping.getExpiresAt().isBefore(LocalDateTime.now())) {
            attributes.addFlashAttribute("error", "This URL has expired.");
            return "redirect:/expired";
        }

        analyticsService.logClick(shortCode, request);
        return "redirect:" + mapping.getOriginalUrl();
    }


}
