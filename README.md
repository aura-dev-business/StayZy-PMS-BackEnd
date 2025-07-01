# StayZy Backend

StayZy is a backend service for a property booking platform, built with Spring Boot. It provides secure authentication, user management, booking, and wishlist functionalities, serving as the core API for the StayZy application.

## Features
- User registration and authentication (JWT-based)
- Secure endpoints for user and booking management
- Booking and wishlist management
- CORS and security configuration
- RESTful API design

## Technologies Used
- Java 17+
- Spring Boot
- Spring Security (JWT)
- Maven
- JPA/Hibernate

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven

### Setup & Run
1. **Clone the repository:**
   ```bash
   git clone <repo-url>
   cd zip
   ```
2. **Configure the application:**
   - Edit `src/main/resources/application.properties` for your DB and JWT settings.
3. **Build the project:**
   ```bash
   mvn clean install
   ```
4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
   The backend will start on `http://localhost:8080` by default.

## Project Structure
```
zip/
├── src/main/java/com/stayzy/
│   ├── config/         # Security and CORS configuration
│   ├── controller/     # REST controllers (Auth, Booking, User, etc.)
│   ├── dto/            # Data Transfer Objects
│   ├── model/          # JPA entities (User, Booking, Wishlist, etc.)
│   ├── repository/     # Spring Data JPA repositories
│   ├── security/       # JWT and user details logic
│   ├── service/        # Service interfaces and implementations
│   └── StayzyBackendApplication.java  # Main entry point
├── src/main/resources/
│   └── application.properties  # App configuration
└── pom.xml            # Maven build file
```

## API Overview
- `POST /auth/login` — Authenticate user and receive JWT
- `POST /auth/register` — Register a new user
- `GET /user/{id}` — Get user details (secured)
- `POST /booking` — Create a booking (secured)
- `GET /booking/{id}` — Get booking details (secured)
- `GET /wishlist` — Get user wishlist (secured)

> For full API details, see the controller classes in `src/main/java/com/stayzy/controller/`.

## License
This project is licensed under the MIT License. See `LICENSE` for details. 