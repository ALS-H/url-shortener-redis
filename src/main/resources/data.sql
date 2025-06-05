INSERT INTO url_mapping (short_code, original_url, create_at, expires_at) VALUES 
('abc123', 'https://example.com', NOW(), NOW() + INTERVAL 7 DAY),
('xyz789', 'https://openai.com', NOW(), NOW() + INTERVAL 7 DAY),
('lmno45', 'https://spring.io', NOW(), NOW() + INTERVAL 7 DAY),
('qwerty', 'https://github.com', NOW(), NOW() + INTERVAL 7 DAY),
('hjk987', 'https://stackoverflow.com', NOW(), NOW() + INTERVAL 7 DAY),
('zxcvb1', 'https://news.ycombinator.com', NOW(), NOW() + INTERVAL 7 DAY),
('poiuyt', 'https://linkedin.com', NOW(), NOW() + INTERVAL 7 DAY),
('asdfgh', 'https://twitter.com', NOW(), NOW() + INTERVAL 7 DAY),
('zmn123', 'https://reddit.com', NOW(), NOW() + INTERVAL 7 DAY),
('lkj456', 'https://medium.com', NOW(), NOW() + INTERVAL 7 DAY);

INSERT INTO click_analytics (short_code, ip, user_agent, clicked_at) VALUES
('abc123', '192.168.1.10', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', NOW()),
('abc123', '192.168.1.11', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)', NOW()),
('xyz789', '10.0.0.5', 'Mozilla/5.0 (Linux; Android 10)', NOW()),
('lmno45', '172.16.0.2', 'Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X)', NOW()),
('qwerty', '203.0.113.7', 'Mozilla/5.0 (Windows NT 6.1; WOW64)', NOW()),
('hjk987', '198.51.100.12', 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:88.0)', NOW()),
('zxcvb1', '192.0.2.50', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3)', NOW()),
('poiuyt', '10.10.10.10', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0)', NOW()),
('asdfgh', '192.168.1.20', 'Mozilla/5.0 (Linux; Android 11)', NOW()),
('zmn123', '203.0.113.20', 'Mozilla/5.0 (iPad; CPU OS 13_4 like Mac OS X)', NOW());


ALTER TABLE url_mapping MODIFY click_count INT NOT NULL DEFAULT 0;
