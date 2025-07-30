# Beer Order System Implementation Tasks

## 1. Entity Implementation

### 1.1 Update Beer Entity
- [x] Add a one-to-many relationship with BeerOrderLine
- [x] Add a collection of BeerOrderLine objects with appropriate JPA annotations
- [x] Ensure bidirectional relationship is properly configured
- [x] Initialize collections to empty sets to avoid null pointer exceptions

### 1.2 Create Customer Entity
- [x] Implement all required fields (id, version, name, email, phone, etc.)
- [x] Add JPA annotations for entity mapping
- [x] Add Lombok annotations (@Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, @Builder)
- [x] Implement audit fields with @PrePersist and @PreUpdate methods
- [x] Configure one-to-many relationship with BeerOrder
- [x] Initialize collections to empty sets to avoid null pointer exceptions

### 1.3 Create BeerOrder Entity
- [x] Implement all required fields (id, version, orderStatus, orderNumber, etc.)
- [x] Add JPA annotations for entity mapping
- [x] Add Lombok annotations
- [x] Implement audit fields with @PrePersist and @PreUpdate methods
- [x] Configure many-to-one relationship with Customer
- [x] Configure one-to-many relationship with BeerOrderLine
- [x] Implement convenience method to add BeerOrderLine items
- [x] Initialize collections to empty sets

### 1.4 Create BeerOrderLine Entity
- [x] Implement all required fields (id, version, orderQuantity, etc.)
- [x] Add JPA annotations for entity mapping
- [x] Add Lombok annotations
- [x] Implement audit fields with @PrePersist and @PreUpdate methods
- [x] Configure many-to-one relationship with BeerOrder
- [x] Configure many-to-one relationship with Beer

## 2. Repository Layer Implementation

### 2.1 Create CustomerRepository
- [x] Extend JpaRepository<Customer, Integer>
- [x] Add any custom query methods if needed

### 2.2 Create BeerOrderRepository
- [x] Extend JpaRepository<BeerOrder, Integer>
- [x] Add methods to find orders by customer
- [x] Add any other custom query methods if needed

### 2.3 Create BeerOrderLineRepository
- [x] Extend JpaRepository<BeerOrderLine, Integer>
- [x] Add methods to find order lines by beer order
- [x] Add any other custom query methods if needed

## 3. DTO Implementation

### 3.1 Create CustomerDto
- [x] Include all relevant fields from Customer entity
- [x] Use Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor, @Builder)
- [x] Exclude complex relationship objects (use IDs instead)

### 3.2 Create BeerOrderDto
- [x] Include all relevant fields from BeerOrder entity
- [x] Use Lombok annotations
- [x] Include customerId instead of full Customer object
- [x] Include collection of BeerOrderLineDto objects or their IDs

### 3.3 Create BeerOrderLineDto
- [x] Include all relevant fields from BeerOrderLine entity
- [x] Use Lombok annotations
- [x] Include beerId and beerOrderId instead of full objects

## 4. Mapper Implementation

### 4.1 Create CustomerMapper
- [x] Use MapStruct with @Mapper annotation
- [x] Implement bidirectional mapping between Customer and CustomerDto
- [x] Ignore appropriate fields during entity creation (id, createdDate, updateDate)

### 4.2 Create BeerOrderMapper
- [x] Use MapStruct with @Mapper annotation
- [x] Implement bidirectional mapping between BeerOrder and BeerOrderDto
- [x] Handle relationship with Customer using customer ID
- [x] Handle relationship with BeerOrderLines
- [x] Ignore appropriate fields during entity creation

### 4.3 Create BeerOrderLineMapper
- [x] Use MapStruct with @Mapper annotation
- [x] Implement bidirectional mapping between BeerOrderLine and BeerOrderLineDto
- [x] Handle relationships with BeerOrder and Beer using their IDs
- [x] Ignore appropriate fields during entity creation

## 5. Service Layer Implementation

### 5.1 Create CustomerService and CustomerServiceImpl
- [x] Define interface with CRUD operations
- [x] Implement service using repository and mapper
- [x] Work with DTOs rather than entities
- [x] Implement proper business logic and validation

### 5.2 Create BeerOrderService and BeerOrderServiceImpl
- [x] Define interface with CRUD operations
- [x] Implement service using repository and mapper
- [x] Include methods for order management
- [x] Implement proper business logic and validation
- [x] Handle relationships with Customer and BeerOrderLines

### 5.3 Create BeerOrderLineService and BeerOrderLineServiceImpl
- [x] Define interface with CRUD operations
- [x] Implement service using repository and mapper
- [x] Include methods for order line management
- [x] Implement proper business logic and validation
- [x] Handle relationships with BeerOrder and Beer

## 6. Controller Layer Implementation

### 6.1 Create CustomerController
- [x] Implement REST endpoints for CRUD operations
- [x] Follow RESTful principles
- [x] Return proper HTTP status codes
- [x] Handle validation and errors appropriately

### 6.2 Create BeerOrderController
- [x] Implement REST endpoints for CRUD operations
- [x] Include endpoints for order management
- [x] Follow RESTful principles
- [x] Return proper HTTP status codes
- [x] Handle validation and errors appropriately

### 6.3 Create BeerOrderLineController
- [x] Implement REST endpoints for CRUD operations
- [x] Include endpoints for order line management
- [x] Follow RESTful principles
- [x] Return proper HTTP status codes
- [x] Handle validation and errors appropriately

## 7. Testing Strategy

### 7.1 Repository Tests
- [x] Create tests for CustomerRepository using @DataJpaTest
- [x] Create tests for BeerOrderRepository using @DataJpaTest
- [x] Create tests for BeerOrderLineRepository using @DataJpaTest
- [x] Verify CRUD operations and custom queries

### 7.2 Service Tests
- [ ] Create tests for CustomerService with mocked dependencies
- [ ] Create tests for BeerOrderService with mocked dependencies
- [ ] Create tests for BeerOrderLineService with mocked dependencies
- [ ] Verify business logic and validation

### 7.3 Controller Tests
- [ ] Create tests for CustomerController using @WebMvcTest
- [ ] Create tests for BeerOrderController using @WebMvcTest
- [ ] Create tests for BeerOrderLineController using @WebMvcTest
- [ ] Verify REST endpoints and HTTP status codes

### 7.4 Integration Tests
- [ ] Create integration tests for key workflows
- [ ] Test the complete order creation process
- [ ] Verify relationships are properly maintained