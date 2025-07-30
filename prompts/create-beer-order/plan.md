# Beer Order System Implementation Plan

## 1. Entity Implementation

### 1.1 Update Beer Entity
- Add a one-to-many relationship with BeerOrderLine
- Add a collection of BeerOrderLine objects with appropriate JPA annotations
- Ensure bidirectional relationship is properly configured

### 1.2 Create Customer Entity
- Implement all required fields (id, version, name, email, phone, etc.)
- Add JPA annotations for entity mapping
- Add Lombok annotations (@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, @Builder)
- Implement audit fields with @PrePersist and @PreUpdate methods
- Configure one-to-many relationship with BeerOrder
- Initialize collections to empty sets to avoid null pointer exceptions

### 1.3 Create BeerOrder Entity
- Implement all required fields (id, version, orderStatus, orderNumber, etc.)
- Add JPA annotations for entity mapping
- Add Lombok annotations
- Implement audit fields with @PrePersist and @PreUpdate methods
- Configure many-to-one relationship with Customer
- Configure one-to-many relationship with BeerOrderLine
- Implement convenience method to add BeerOrderLine items
- Initialize collections to empty sets

### 1.4 Create BeerOrderLine Entity
- Implement all required fields (id, version, orderQuantity, etc.)
- Add JPA annotations for entity mapping
- Add Lombok annotations
- Implement audit fields with @PrePersist and @PreUpdate methods
- Configure many-to-one relationship with BeerOrder
- Configure many-to-one relationship with Beer

## 2. Repository Layer Implementation

### 2.1 Create CustomerRepository
- Extend JpaRepository<Customer, Integer>
- Add any custom query methods if needed

### 2.2 Create BeerOrderRepository
- Extend JpaRepository<BeerOrder, Integer>
- Add methods to find orders by customer
- Add any other custom query methods if needed

### 2.3 Create BeerOrderLineRepository
- Extend JpaRepository<BeerOrderLine, Integer>
- Add methods to find order lines by beer order
- Add any other custom query methods if needed

## 3. DTO Implementation

### 3.1 Create CustomerDto
- Include all relevant fields from Customer entity
- Use Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor, @Builder)
- Exclude complex relationship objects (use IDs instead)

### 3.2 Create BeerOrderDto
- Include all relevant fields from BeerOrder entity
- Use Lombok annotations
- Include customerId instead of full Customer object
- Include collection of BeerOrderLineDto objects or their IDs

### 3.3 Create BeerOrderLineDto
- Include all relevant fields from BeerOrderLine entity
- Use Lombok annotations
- Include beerId and beerOrderId instead of full objects

## 4. Mapper Implementation

### 4.1 Create CustomerMapper
- Use MapStruct with @Mapper annotation
- Implement bidirectional mapping between Customer and CustomerDto
- Ignore appropriate fields during entity creation (id, createdDate, updateDate)

### 4.2 Create BeerOrderMapper
- Use MapStruct with @Mapper annotation
- Implement bidirectional mapping between BeerOrder and BeerOrderDto
- Handle relationship with Customer using customer ID
- Handle relationship with BeerOrderLines
- Ignore appropriate fields during entity creation

### 4.3 Create BeerOrderLineMapper
- Use MapStruct with @Mapper annotation
- Implement bidirectional mapping between BeerOrderLine and BeerOrderLineDto
- Handle relationships with BeerOrder and Beer using their IDs
- Ignore appropriate fields during entity creation

## 5. Service Layer Implementation

### 5.1 Create CustomerService and CustomerServiceImpl
- Define interface with CRUD operations
- Implement service using repository and mapper
- Work with DTOs rather than entities
- Implement proper business logic and validation

### 5.2 Create BeerOrderService and BeerOrderServiceImpl
- Define interface with CRUD operations
- Implement service using repository and mapper
- Include methods for order management
- Implement proper business logic and validation
- Handle relationships with Customer and BeerOrderLines

### 5.3 Create BeerOrderLineService and BeerOrderLineServiceImpl
- Define interface with CRUD operations
- Implement service using repository and mapper
- Include methods for order line management
- Implement proper business logic and validation
- Handle relationships with BeerOrder and Beer

## 6. Controller Layer Implementation

### 6.1 Create CustomerController
- Implement REST endpoints for CRUD operations
- Follow RESTful principles
- Return proper HTTP status codes
- Handle validation and errors appropriately

### 6.2 Create BeerOrderController
- Implement REST endpoints for CRUD operations
- Include endpoints for order management
- Follow RESTful principles
- Return proper HTTP status codes
- Handle validation and errors appropriately

### 6.3 Create BeerOrderLineController
- Implement REST endpoints for CRUD operations
- Include endpoints for order line management
- Follow RESTful principles
- Return proper HTTP status codes
- Handle validation and errors appropriately

## 7. Testing Strategy

### 7.1 Repository Tests
- Create tests for CustomerRepository using @DataJpaTest
- Create tests for BeerOrderRepository using @DataJpaTest
- Create tests for BeerOrderLineRepository using @DataJpaTest
- Verify CRUD operations and custom queries

### 7.2 Service Tests
- Create tests for CustomerService with mocked dependencies
- Create tests for BeerOrderService with mocked dependencies
- Create tests for BeerOrderLineService with mocked dependencies
- Verify business logic and validation

### 7.3 Controller Tests
- Create tests for CustomerController using @WebMvcTest
- Create tests for BeerOrderController using @WebMvcTest
- Create tests for BeerOrderLineController using @WebMvcTest
- Verify REST endpoints and HTTP status codes

### 7.4 Integration Tests
- Create integration tests for key workflows
- Test the complete order creation process
- Verify relationships are properly maintained

## 8. Implementation Sequence

To ensure a smooth implementation process, the following sequence is recommended:

1. Update Beer entity with the new relationship
2. Implement Customer entity
3. Implement BeerOrder entity
4. Implement BeerOrderLine entity
5. Implement repositories for all new entities
6. Implement DTOs for all new entities
7. Implement Mappers for all new entities
8. Implement Services for all new entities
9. Implement Controllers for all new entities
10. Write Tests for all layers

This sequence ensures that dependencies are available when needed and minimizes refactoring.