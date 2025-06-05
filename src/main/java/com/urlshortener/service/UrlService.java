package com.urlshortener.service;

import com.urlshortener.model.UrlMapping;
import com.urlshortener.repository.UrlRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final StringRedisTemplate redisTemplate;

    public UrlService(UrlRepository urlRepository, StringRedisTemplate redisTemplate) {
        this.urlRepository = urlRepository;
        this.redisTemplate = redisTemplate;
    }

    public String shortenUrl(String originalUrl) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // 1️⃣ Check Redis
        String cachedShortCode = ops.get(originalUrl);
        if (cachedShortCode != null) {
            return cachedShortCode;
        }

        // 2️⃣ Check DB
        Optional<UrlMapping> existing = urlRepository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) {
            String shortCode = existing.get().getShortCode();
            ops.set(originalUrl, shortCode); // update Redis
            ops.set(shortCode, originalUrl);
            return shortCode;
        }

        // 3️⃣ Generate new short code
        String shortCode = generateShortCode();
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortCode(shortCode);
        mapping.setCreateAt(LocalDateTime.now());
        mapping.setExpiresAt(LocalDateTime.now().plusDays(7));
        urlRepository.save(mapping);

        // 4️⃣ Store in Redis
        ops.set(originalUrl, shortCode);
        ops.set(shortCode, originalUrl);

        return shortCode;
    }

    public Optional<UrlMapping> getOriginalUrl(String shortCode) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // 1️⃣ Check Redis
        String cachedUrl = ops.get(shortCode);
        if (cachedUrl != null) {
            UrlMapping mapping = new UrlMapping();
            mapping.setOriginalUrl(cachedUrl);
            mapping.setShortCode(shortCode);
            return Optional.of(mapping);
        }

        // 2️⃣ Check DB
        Optional<UrlMapping> mapping = urlRepository.findByShortCode(shortCode);
        mapping.ifPresent(m -> {
            ops.set(shortCode, m.getOriginalUrl());
            ops.set(m.getOriginalUrl(), shortCode);
        });

        return mapping;
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6); // custom short code
    }
}
