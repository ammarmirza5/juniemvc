# Requirements for Adding DTOs to JunieMVC Project

## Overview
This document outlines the requirements for implementing Data Transfer Objects (DTOs) in the JunieMVC application to improve the separation between the API layer and the domain model.

## Background
Currently, the JunieMVC application directly exposes JPA entities through its REST API. This approach has several drawbacks:
- It tightly couples the API contract to the database schema
- It may expose sensitive or unnecessary data
- It makes API versioning more difficult
- It doesn't provide a clear place for input validation

## Requirements

### 1. Create DTO Classes
1. Create a new package `com.vhouse.juniemvc.dtos` to contain all DTO classes
2. Implement a `BeerDto` class with the following properties:
   - Integer id
   - Integer version
   - String beerName
   - String beerStyle
   - String upc
   - Integer quantityOnHand
   - BigDecimal price
   - LocalDateTime createdDate
   - LocalDateTime updateDate
3. Apply appropriate Lombok annotations to the DTO:
   - `@Data` or `@Getter`/`@Setter`
   - `@NoArgsConstructor`
   - `@AllArgsConstructor`
   - `@Builder`

### 2. Implement MapStruct Mapper
1. Create a new package `com.vhouse.juniemvc.mappers` for mapper interfaces
2. Create a `BeerMapper` interface using MapStruct:
   ```java
   @Mapper(componentModel = "spring")
   public interface BeerMapper {
       BeerDto beerToBeerDto(Beer beer);
       Beer beerDtoToBeer(BeerDto beerDto);
   }
   ```
3. When mapping from `BeerDto` to `Beer`, ignore the following properties:
   - id
   - createdDate
   - updateDate
4. Configure the Maven compiler plugin to properly process MapStruct annotations

### 3. Update Service Layer
1. Modify the `BeerService` interface to accept and return DTOs instead of entities:
   ```java
   public interface BeerService {
       List<BeerDto> findAll();
       Optional<BeerDto> findById(Integer id);
       BeerDto save(BeerDto beerDto);
       BeerDto update(Integer id, BeerDto beerDto);
       void delete(Integer id);
       boolean exists(Integer id);
   }
   ```
2. Update the `BeerServiceImpl` class to:
   - Inject the `BeerMapper`
   - Convert between DTOs and entities using the mapper
   - Maintain the same business logic

### 4. Update Controller Layer
1. Modify the `BeerController` to use DTOs in all endpoints:
   - GET `/api/v1/beers` should return `List<BeerDto>`
   - GET `/api/v1/beers/{beerId}` should return `BeerDto`
   - POST `/api/v1/beers` should accept and return `BeerDto`
   - PUT `/api/v1/beers/{beerId}` should accept and return `BeerDto`
   - DELETE `/api/v1/beers/{beerId}` remains unchanged

### 5. Update Tests
1. Update all controller tests to use DTOs instead of entities
2. Update all service tests to use DTOs instead of entities
3. Repository tests should continue to use entities

## Technical Constraints
1. Use MapStruct 1.6.3 for object mapping
2. Use Lombok for reducing boilerplate code in DTOs
3. Ensure proper configuration of annotation processors in the Maven build

## Acceptance Criteria
1. All API endpoints should use DTOs instead of entities
2. All tests should pass
3. The application should maintain the same functionality from an external perspective
4. The service layer should handle the conversion between DTOs and entities
5. The repository layer should continue to work with entities