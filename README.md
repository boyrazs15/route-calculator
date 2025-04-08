# ✈️ ️ Route Calculator - Full Stack Web Application
A full-stack Java + React project for calculating transportation routes between locations. A Spring Boot-based backend system designed for the aviation industry. The system calculates all valid transportation routes from one location to another, combining flights with optional before/after transfers such as bus, subway, or Uber. It enables users to explore flexible and optimized travel options between two points.

A simple Spring Boot project that demonstrates CRUD operations for a `Location` and `Transportation` entity & a `Route API` to list available routes between locations by using using Java 17, Spring Boot 3, PostgreSQL, and Swagger UI.

---
## ✅ Features

- Full CRUD for locations and transportations
- Route List API that uses these locations and transportations
- Layered Architecture (Controller → Service → Repository)
- PostgreSQL integration (JPA/Hibernate)
- Swagger UI for API documentation
- Postman collection for sample requests
- SpringBootTest for available routes API
- Backend validations (e.g. duplicate record, db conflict)
- Cache for locations and transportations
- Global Exception Handling with `@ControllerAdvice`
- Generic API Response DTO
- Config structure for values that can change (transportation types)
- Health check http://localhost:8080/actuator/health
- Security check with X-API-KEY filter
- Rate Limiter filter
- Notifications with Toastify (frontend)

## 📦 Project Structure
```
project-root/
├── backend/ → Spring Boot 3 (Java 17, JPA, REST)
├── frontend/ → React (JavaScript, Axios, Toastify)
└── postman/ → API collection & environment
```
---

## 🚀 Getting Started

🔧 Pre-requisites
Before you start, make sure you have the following installed:

- ✅ Java 17 or higher
- ✅ Maven 3.8+
- ✅ PostgreSQL 12+
- ✅ Node.js 16+ (for frontend)
- ✅ npm or yarn
- ✅ An IDE (Recommended: IntelliJ IDEA)
- ✅ Lombok Plugin for your IDE
Especially if you use IntelliJ:
Preferences → Plugins → Search for "Lombok" → Install.
Then enable annotation processing:
Preferences → Build, Execution, Deployment → Compiler → Annotation Processors → Enable
- ✅ Docker - to run tests

### 1. Clone the Project
```bash
git clone https://github.com/boyrazs15/route-calculator.git
cd route-calculator
```
### 2. PostgreSQL Setup
```sql
CREATE DATABASE route_calculator;
```

### 3. Run the Application Backend
   Using terminal:
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```
It will run on http://localhost:8080

Swagger UI: http://localhost:8080/swagger-ui.html or http://localhost:8080/documentation

### 4. Run the Application Frontend
Using terminal:
   ```bash
   cd ..
   cd frontend
   cp .env.example .env
   npm install
   npm run start
   ```
It will run on http://localhost:3000

## 🧭 Postman
Under the /postman folder you will find:

`route-calculator.postman_collection.json` – All endpoints

`route-calculator.postman_environment.json` – Set environment variable (e.g. {{baseUrl}})

You can import both in Postman to quickly test API.

