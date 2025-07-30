# JunieMVC Developer Guidelines

## Project Overview

JunieMVC is a Spring Boot application that demonstrates a RESTful API for managing beer entities. The application follows standard Spring MVC architecture patterns.

### Tech Stack
- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- Spring MVC
- H2 Database (in-memory)
- Flyway for database migrations
- Maven for build management
- JUnit 5 and Mockito for testing
- Lombok for reducing boilerplate code
- MapStruct for object mapping

## Project Structure

The project follows standard Maven project structure:

```
juniemvc/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/vhouse/juniemvc/
│   │   │       ├── controllers/    # REST controllers
│   │   │       ├── entities/       # JPA entities
│   │   │       ├── repositories/   # Spring Data repositories
│   │   │       └── services/       # Business logic
│   │   └── resources/
│   │       └── application.properties  # Application configuration
│   └── test/
│       └── java/
│           └── com/vhouse/juniemvc/
│               ├── controllers/    # Controller tests
│               ├── repositories/   # Repository tests
│               └── services/       # Service tests
└── pom.xml                         # Maven configuration
```

### Architecture

The application follows a layered architecture:
1. **Controller Layer**: Handles HTTP requests and responses
2. **Service Layer**: Contains business logic
3. **Repository Layer**: Manages data access
4. **Entity Layer**: Represents database tables

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Steps
1. Clone the repository
2. Navigate to the project root directory
3. Run the application:
   ```
   ./mvnw spring-boot:run
   ```
4. Access the application at http://localhost:8080
5. H2 Console is available at http://localhost:8080/h2-console
   - JDBC URL: jdbc:h2:mem:testdb
   - Username: sa
   - Password: (empty)

## Running Tests

### Running All Tests
```
./mvnw test
```

### Running Specific Test Classes
```
./mvnw test -Dtest=BeerControllerTest
./mvnw test -Dtest=BeerServiceImplTest
./mvnw test -Dtest=BeerRepositoryTest
```

## Best Practices

### Code Organization
- Follow the package structure for new components
- Keep controllers thin, delegate business logic to services
- Use interfaces for services to maintain loose coupling

### Testing
- Write tests for all layers (controller, service, repository)
- Use the Given-When-Then pattern for test structure
- Mock dependencies for unit tests
- Use @DataJpaTest for repository tests
- Use @WebMvcTest for controller tests

### Database
- Use JPA entities with appropriate annotations
- Define entity relationships clearly
- Use Flyway migrations for database schema changes

### API Design
- Follow RESTful principles
- Use appropriate HTTP methods (GET, POST, PUT, DELETE)
- Return appropriate HTTP status codes
- Validate input data

### Logging
- Use SLF4J for logging
- Log important events and errors
- Avoid logging sensitive information

### Error Handling
- Use proper exception handling
- Return meaningful error messages
- Use @ControllerAdvice for global exception handling