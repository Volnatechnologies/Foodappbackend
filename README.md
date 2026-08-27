# 🍕 Food Delivery Backend

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://oracle.com)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Supported-blue.svg)](https://www.docker.com/)

A modular, multi-service Spring Boot backend powering a food delivery platform. Built with a Database-per-Service architecture, strong *JWT authentication*, *Flyway schema migrations*, and *Docker Compose* orchestration.

---

📌 Microservices Overview

| Microservice | Port | Core Responsibilities | Key Technologies |
| :--- | :---: | :--- | :--- |
 Auth-Service| Configurable| User registration, authentication, JWT token issuance | Spring Boot, Spring Security, JWT 
 
User-Service| 8082 | User profiles, delivery address management (CRUD) | Spring Boot, PostgreSQL, JPA |

Restaurant-Service| Configurable| Restaurant onboarding, menu management, document verification | Spring Boot, File Storage, JPA |

---

 🏗 System Architecture & Auth Flow

 1. High-Level Architecture
text
                         ┌──────────────────────┐
                         │   Client / Postman   │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │     Auth-Service     │
                         │   (Issues JWT Token) │
                         └──────────┬───────────┘
                                    │
                         JWT Authenticated Requests
                                    │
                 ┌──────────────────┴──────────────────┐
                 │                                     │
                 ▼                                     ▼
       ┌────────────────────┐              ┌────────────────────────┐
       │    User-Service    │              │   Restaurant-Service   │
       │ (Profile & Address)│              │  (Onboarding & Docs)   │
       └─────────┬──────────┘              └────────────┬───────────┘
                 │                                      │
                 ▼                                      ▼
       ┌────────────────────┐              ┌────────────────────────┐
       │   User PostgreSQL  │              │ Restaurant PostgreSQL  │
       └────────────────────┘              └────────────────────────┘
2. Authentication
3.  FlowRegister/Login
4.  $\rightarrow$ Request sent to Auth-Service.
5.  Issue Token $\rightarrow$ Auth-Service validates credentials and returns a JWT Bearer Token.
6.  API Access $\rightarrow$ Client sends Authorization: Bearer <JWT_TOKEN> with downstream requests.
7.  Authorize $\rightarrow$ Services validate the JWT locally via Spring Security context before processing.
8.  ⚡ Quick Start (Docker Compose)PrerequisitesJava 21+Maven 3.9+Docker Desktop1.
9.  Build the ServicesFrom the repository root, package each microservice JAR:Bash# Build User-Service
mvn clean package

Build Auth-Service
cd auth-service && mvn clean package && cd ..

Build Restaurant-Service
cd restaurant-service && mvn clean package && cd ..
2. Launch Container EnvironmentBash# Spin up services and databases in detached mode
docker compose up --build -d

Check running container status
docker compose ps
📡 API Cheat Sheet🔑 
Auth-ServicePOST /api/v1/auth/register
— Register a new userPOST /api/v1/auth/login 
— Authenticate and receive JWT token👤 User-Service (:8082)GET /api/v1/users/me 
— Fetch logged-in user detailsPUT /api/v1/users/me 
— Update user profileGET /api/v1/users/addresses 
— Fetch user address listPOST /api/v1/users/addresses 
— Add a new addressDELETE /api/v1/users/addresses/{addressId}
— Remove an address🍽️ Restaurant-ServicePOST /api/v1/restaurants 
— Register a restaurantGET /api/v1/restaurants/{id} 
— Fetch restaurant detailsPUT /api/v1/restaurants/{id}
— Update restaurant profilePOST /api/v1/restaurants/documents
— Upload verification documentGET /api/v1/restaurants/documents/{id}
— Retrieve document metadata🛠 Project & Database StructurePlaintextUser-Service/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── src/
│   └── main/
│       ├── java/com/volna/userservice/
│       └── resources/
│           ├── application.yaml
│           └── db/migration/
├── auth-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
└── restaurant-service/
    ├── Dockerfile
    ├── pom.xml
    └── src/
Each service owns an isolated PostgreSQL database following microservices isolation practices:PlaintextAuth-Service       ──►  auth_db       (PostgreSQL :5432)
User-Service       ──►  volna_user_db (PostgreSQL :5432)
Restaurant-Service ──►  restaurant_db (PostgreSQL :5432)
Schema changes are version-controlled and applied automatically on application startup via Flyway:Plaintextsrc/main/resources/db/migration/
├── V1__create_tables.sql
└── V2__add_indexes.sql
🔍 Useful CommandsBash# Stream User-Service logs
docker compose logs -f user-service

Open interactive PostgreSQL shell (User DB)
docker exec -it volna-user-db psql -U postgres -d volna_user_db

 Stop containers and remove volumes (Wipes local database data)
docker compose down -v
📋 Pre-Commit 
Checklist[x] 
Auth registration & login issuing valid JWTs[x]
User-Service profile and address CRUD operational[x] 
Restaurant registration & document uploads functional[x] 
Flyway migrations passing on container startup[x] 
Unauthenticated endpoints properly blocked (401/403)

📄 License
This repository is maintained for development, learning, and platform evaluation purposes.
