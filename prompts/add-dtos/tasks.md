# Task List for Adding DTOs to JunieMVC Project

## 1. Update Project Dependencies
1. [x] Add MapStruct dependency to `pom.xml`
   - [x] Add `org.mapstruct:mapstruct:1.6.3` as a dependency
2. [x] Configure Maven compiler plugin for MapStruct
   - [x] Add annotation processor paths for Lombok
   - [x] Add annotation processor paths for MapStruct
   - [x] Add annotation processor paths for Lombok-MapStruct binding
   - [x] Configure MapStruct to use Spring component model
   - [x] Ensure proper ordering of annotation processors

## 2. Create DTO Classes
1. [x] Create a new package `com.vhouse.juniemvc.dtos`
2. [x] Implement `BeerDto` class
   - [x] Add all required fields (id, version, beerName, beerStyle, upc, quantityOnHand, price, createdDate, updateDate)
   - [x] Apply Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor, @Builder)
   - [x] Ensure DTO structure matches entity structure

## 3. Implement MapStruct Mapper
1. [x] Create a new package `com.vhouse.juniemvc.mappers`
2. [x] Create `BeerMapper` interface
   - [x] Add `@Mapper(componentModel = "spring")` annotation
   - [x] Define method to map from Beer entity to BeerDto
   - [x] Define method to map from BeerDto to Beer entity
   - [x] Configure mapper to ignore id, createdDate, and updateDate when mapping from DTO to entity
   - [x] Ensure proper type conversions for all fields

## 4. Update Service Layer
1. [x] Modify `BeerService` interface
   - [x] Change return types from Beer to BeerDto
   - [x] Change parameter types from Beer to BeerDto
   - [x] Update method signatures
2. [x] Update `BeerServiceImpl` implementation
   - [x] Inject BeerMapper into the service
   - [x] Update methods to convert between DTOs and entities
   - [x] Maintain existing business logic

## 5. Update Controller Layer
1. [x] Modify `BeerController`
   - [x] Update endpoints to accept and return DTOs
   - [x] Update method signatures and return types
   - [x] Maintain the same REST API contract
   - [x] Ensure proper HTTP status codes and response handling

## 6. Update Tests
1. [x] Update controller tests
   - [x] Modify `BeerControllerTest` to use `BeerDto`
   - [x] Update test methods to reflect controller changes
   - [x] Ensure all tests pass
2. [x] Update service tests
   - [x] Modify `BeerServiceImplTest` to use `BeerDto`
   - [x] Add tests for mapper functionality
   - [x] Update test methods to reflect service changes
   - [x] Ensure all tests pass

## 7. Testing and Verification
1. [x] Run unit tests for each layer
2. [x] Verify all tests pass after changes
3. [x] Test API endpoints manually
4. [x] Verify application maintains the same functionality