# Task List for Adding DTOs to JunieMVC Project

## 1. Update Project Dependencies
1. [ ] Add MapStruct dependency to `pom.xml`
   - [ ] Add `org.mapstruct:mapstruct:1.6.3` as a dependency
2. [ ] Configure Maven compiler plugin for MapStruct
   - [ ] Add annotation processor paths for Lombok
   - [ ] Add annotation processor paths for MapStruct
   - [ ] Add annotation processor paths for Lombok-MapStruct binding
   - [ ] Configure MapStruct to use Spring component model
   - [ ] Ensure proper ordering of annotation processors

## 2. Create DTO Classes
1. [ ] Create a new package `com.vhouse.juniemvc.dtos`
2. [ ] Implement `BeerDto` class
   - [ ] Add all required fields (id, version, beerName, beerStyle, upc, quantityOnHand, price, createdDate, updateDate)
   - [ ] Apply Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor, @Builder)
   - [ ] Ensure DTO structure matches entity structure

## 3. Implement MapStruct Mapper
1. [ ] Create a new package `com.vhouse.juniemvc.mappers`
2. [ ] Create `BeerMapper` interface
   - [ ] Add `@Mapper(componentModel = "spring")` annotation
   - [ ] Define method to map from Beer entity to BeerDto
   - [ ] Define method to map from BeerDto to Beer entity
   - [ ] Configure mapper to ignore id, createdDate, and updateDate when mapping from DTO to entity
   - [ ] Ensure proper type conversions for all fields

## 4. Update Service Layer
1. [ ] Modify `BeerService` interface
   - [ ] Change return types from Beer to BeerDto
   - [ ] Change parameter types from Beer to BeerDto
   - [ ] Update method signatures
2. [ ] Update `BeerServiceImpl` implementation
   - [ ] Inject BeerMapper into the service
   - [ ] Update methods to convert between DTOs and entities
   - [ ] Maintain existing business logic

## 5. Update Controller Layer
1. [ ] Modify `BeerController`
   - [ ] Update endpoints to accept and return DTOs
   - [ ] Update method signatures and return types
   - [ ] Maintain the same REST API contract
   - [ ] Ensure proper HTTP status codes and response handling

## 6. Update Tests
1. [ ] Update controller tests
   - [ ] Modify `BeerControllerTest` to use `BeerDto`
   - [ ] Update test methods to reflect controller changes
   - [ ] Ensure all tests pass
2. [ ] Update service tests
   - [ ] Modify `BeerServiceImplTest` to use `BeerDto`
   - [ ] Add tests for mapper functionality
   - [ ] Update test methods to reflect service changes
   - [ ] Ensure all tests pass

## 7. Testing and Verification
1. [ ] Run unit tests for each layer
2. [ ] Verify all tests pass after changes
3. [ ] Test API endpoints manually
4. [ ] Verify application maintains the same functionality