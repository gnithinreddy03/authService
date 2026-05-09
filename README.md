# Spring Boot Auth Service

JWT-based Authentication & Authorization service built using Spring Security, PostgreSQL, and Spring Boot.

---

## Features

- User Registration
- User Login
- JWT Token Generation
- Role-Based Authorization
- Stateless Authentication
- Spring Security Integration
- PostgreSQL Database Integration
- Swagger/OpenAPI Documentation
- Password Encryption using BCrypt

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (JSON Web Token)
- Maven
- Swagger/OpenAPI
- Lombok

---

## Project Structure

```text
src/main/java/com/nithin/authService
│
├── config
├── controller
├── dao
├── dto
├── filter
├── repository
├── service
└── util
```

---

## Authentication Flow

1. User registers using `/auth/register`
2. Password gets encrypted using BCrypt
3. User logs in using `/auth/login`
4. Spring Security authenticates credentials
5. JWT token is generated
6. Client sends token in Authorization header
7. JWT filter validates token for protected routes

---

## API Endpoints

### Register User

```http
POST /auth/register
```

### Request Body

```json
{
  "username": "nithin",
  "password": "password123",
  "roles": ["USER"]
}
```

### Response

```json
{
  "message": "User registered successfully"
}
```

---

## Login User

```http
POST /auth/login
```

### Request Body

```json
{
  "username": "nithin",
  "password": "password123"
}
```

### Response

```json
{
  "token": "jwt-token-here"
}
```

---

## Access USER Endpoint

```http
GET /helloUser
```

### Headers

```http
Authorization: Bearer <jwt-token>
```

### Required Role

```text
USER
```

### Response

```text
Hello User
```

---

## Access ADMIN Endpoint

```http
GET /helloAdmin
```

### Headers

```http
Authorization: Bearer <jwt-token>
```

### Required Role

```text
ADMIN
```

### Response

```text
Hello Admin
```

---

## Swagger Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI Docs:

```text
http://localhost:8080/v3/api-docs
```

---

## Environment Variables

The application uses environment variables for sensitive configuration.

### Required Variables

```env
DB_URL=jdbc:postgresql://localhost:5432/NithinDb
DB_USERNAME=postgres
DB_PASSWORD=yourpassword

JWT_SECRET=your-secret-key
```

---

## application.yml

```yaml
spring:
  application:
    name: authService

  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: update

jwt:
  secret: ${JWT_SECRET}
  expiration: 3600000
```

---

## Running the Project

### Clone Repository

```bash
git clone https://github.com/gnithinreddy03/authService.git
```

---

### Navigate to Project

```bash
cd authService
```

---

### Build Project

```bash
mvn clean install
```

---

### Run Application

```bash
mvn spring-boot:run
```

---

## Security Features

- BCrypt password hashing
- Stateless JWT authentication
- Role-based access control
- Spring Security filter chain
- Protected endpoints
- AuthenticationManager-based login validation

## Author

GitHub: https://github.com/gnithinreddy03