# Notification Service API

A multi-channel notification service built with Spring Boot. Supports EMAIL, SMS, and IN_APP notifications with JWT authentication.

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Security + JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- Docker

## Project Structure

```
src/main/java/com/adham/notification_service/
├── controller/
│   ├── AuthController.java        # Register and login endpoints
│   └── NotificationController.java # Notification endpoints
├── exception/
│   └── GlobalExceptionHandler.java # Handles validation and bad request errors
├── model/
│   ├── Channel.java               # Enum: EMAIL, SMS, IN_APP
│   ├── Notification.java          # Notification entity
│   ├── Role.java                  # Enum: USER, ADMIN
│   └── User.java                  # User entity
├── repository/
│   ├── NotificationRepository.java
│   └── UserRepository.java
├── security/
│   ├── CustomUserDetailsService.java # Loads users from DB for Spring Security
│   ├── JwtFilter.java             # Intercepts requests and validates JWT
│   ├── JwtUtil.java               # Generates and validates JWT tokens
│   └── SecurityConfig.java        # Spring Security configuration
└── service/
    ├── AuthService.java           # Register and login logic
    └── NotificationService.java   # Notification business logic
```

## Prerequisites

- Java 21+
- Maven
- Docker Desktop

## Setup

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/notification-service.git
cd notification-service
```

### 2. Start Docker Desktop

Make sure Docker Desktop is open and running.

### 3. Start the PostgreSQL container

If you're running it for the first time:

```bash
docker run --name notification-db -e POSTGRES_PASSWORD=password -e POSTGRES_DB=notificationdb -p 5432:5432 -d postgres
```

If the container already exists from a previous run:

```bash
docker start notification-db
```

### 4. Set environment variables

In IntelliJ, go to **Run > Edit Configurations** and add the following environment variables:

```
DB_URL=jdbc:postgresql://localhost:5432/notificationdb
DB_USERNAME=postgres
DB_PASSWORD=password
JWT_SECRET=notificationservicesecretkey123456789012345678901234567890
```

### 5. Run the application

Hit the green play button in IntelliJ or run:

```bash
mvn spring-boot:run
```

The app will start on `http://localhost:8080`

## API Endpoints

### Auth

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/auth/register` | Register a new user | No |
| POST | `/auth/login` | Login and receive JWT token | No |

### Notifications

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/notifications` | Send a notification | Yes |
| GET | `/api/notifications/user/{userId}` | Get all notifications for a user | Yes |

## Usage

### 1. Register

```bash
POST /auth/register
Content-Type: application/json

{
  "username": "adham",
  "password": "password123"
}
```

### 2. Login

```bash
POST /auth/login
Content-Type: application/json

{
  "username": "adham",
  "password": "password123"
}
```

Returns a JWT token string.

### 3. Send a notification

```bash
POST /api/notifications
Authorization: Bearer your.jwt.token
Content-Type: application/json

{
  "userId": "user123",
  "message": "Your order has shipped!",
  "channel": "EMAIL"
}
```

Valid channel values: `EMAIL`, `SMS`, `IN_APP`

### 4. Get notifications for a user

```bash
GET /api/notifications/user/user123
Authorization: Bearer your.jwt.token
```

## How Authentication Works

1. Register to create an account
2. Login to receive a JWT token
3. Include the token in the `Authorization` header of every protected request as `Bearer <token>`
4. Tokens expire after 10 hours — login again to get a new one
