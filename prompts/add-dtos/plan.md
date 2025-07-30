# Implementation Plan for Adding DTOs to JunieMVC Project

## Overview
This document outlines the detailed implementation plan for adding Data Transfer Objects (DTOs) to the JunieMVC application based on the requirements specified in `requirements.md`. The plan is structured to ensure a systematic approach to implementing the changes while maintaining the application's functionality.

## Implementation Steps

### 1. Update Project Dependencies
1. Add MapStruct dependency to `pom.xml`:
   - Add `org.mapstruct:mapstruct:1.6.3` as a dependency
   
2. Configure Maven compiler plugin to process MapStruct annotations:
   - Add annotation processor paths for Lombok, MapStruct, and Lombok-MapStruct binding
   - Configure MapStruct to use Spring component model
   - Ensure proper ordering of annotation processors to avoid conflicts between Lombok and MapStruct

### 2. Create DTO Classes
1. Create a new package `com.vhouse.juniemvc.dtos`
2. Implement `BeerDto` class with all required properties:
   - Include all fields from the Beer entity: id, version, beerName, beerStyle, upc, quantityOnHand, price, createdDate, updateDate
   - Apply Lombok annotations: @Data (or @Getter/@Setter), @NoArgsConstructor, @AllArgsConstructor, @Builder
   - Ensure the DTO structure matches the entity structure for seamless mapping

### 3. Implement MapStruct Mapper
1. Create a new package `com.vhouse.juniemvc.mappers`
2. Create `BeerMapper` interface:
   - Annotate with `@Mapper(componentModel = "spring")` to enable Spring integration
   - Define methods for mapping between Beer entity and BeerDto
   - Configure the mapper to ignore id, createdDate, and updateDate when mapping from DTO to entity
   - Ensure proper type conversions for all fields

### 4. Update Service Layer
1. Modify `BeerService` interface to use DTOs:
   - Change return types and parameter types from Beer to BeerDto
   - Update method signatures to reflect the use of DTOs
   - Maintain the same method names and functionality

2. Update `BeerServiceImpl` to use the mapper:
   - Inject the BeerMapper into the service
   - Convert between DTOs and entities using the mapper
   - Update all methods to handle the conversion between DTOs and entities
   - Maintain the same business logic while working with DTOs at the service boundary

### 5. Update Controller Layer
1. Modify `BeerController` to use DTOs:
   - Update all endpoints to accept and return DTOs instead of entities
   - Update method signatures and return types
   - Maintain the same REST API contract from an external perspective
   - Ensure proper HTTP status codes and response handling

### 6. Update Tests
1. Update controller tests to use DTOs:
   - Modify `BeerControllerTest` to use `BeerDto` instead of `Beer`
   - Update test methods to reflect the changes in the controller
   - Ensure all tests pass with the new DTO implementation

2. Update service tests to use DTOs:
   - Modify `BeerServiceImplTest` to use `BeerDto` instead of `Beer`
   - Add tests for the mapper functionality
   - Update test methods to reflect the changes in the service
   - Ensure all tests pass with the new DTO implementation

3. Repository tests remain unchanged as they continue to work with entities

## Testing Strategy
1. Run unit tests for each layer to ensure proper functionality
2. Verify that all tests pass after the changes
3. Test the API endpoints manually to ensure they work as expected
4. Verify that the application maintains the same functionality from an external perspective


## Conclusion
This implementation plan provides a detailed roadmap for adding DTOs to the JunieMVC application. By following this plan, we will improve the separation between the API layer and the domain model, making the application more maintainable and flexible for future changes.