# Scalable URL Shortener with Analytics

A robust URL shortening service built with **Spring Boot**, **MySQL**, and **Redis**, featuring click analytics, expiration handling, and a clean web UI using **Thymeleaf**. Designed with scalability, performance, and extensibility in mind.

---

## Features

-  **Shorten any valid URL**
-  **Expiration time** for links
-  **Redirection** to original URL
-  **Click analytics (backend only)**: timestamps and request metadata are stored for each redirection
-  **Redis caching** to reduce DB hits
-  **Responsive frontend** using Thymeleaf
-  Basic input validation and error handling

---

## Tech Stack

- **Backend**: Spring Boot, Spring MVC, Spring Data JPA  
- **Database**: MySQL  
- **Caching**: Redis  
- **Frontend**: Thymeleaf  
- **Build Tool**: Maven 
