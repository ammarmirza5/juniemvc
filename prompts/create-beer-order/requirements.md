# Beer Order System Implementation Requirements

## Overview

This document outlines the requirements for implementing a beer ordering system within the JunieMVC application. The implementation will extend the existing Beer entity management to include Customer, BeerOrder, and BeerOrderLine entities with appropriate relationships.

## Entity Relationships

The system will implement the following entity relationships:

```
Customer (1) --- (N) BeerOrder (1) --- (N) BeerOrderLine (N) --- (1) Beer
```

- A Customer can have multiple BeerOrders
- A BeerOrder belongs to one Customer
- A BeerOrder can have multiple BeerOrderLines
- A BeerOrderLine belongs to one BeerOrder
- A BeerOrderLine references one Beer
- A Beer can be referenced by multiple BeerOrderLines

## Entity Specifications

### 1. Customer Entity

**Fields:**
- id (Integer): Primary key
- version (Integer): For optimistic locking
- name (String): Customer name
- email (String): Customer email address
- phone (String): Customer phone number
- beerOrders (Set<BeerOrder>): Collection of customer's orders
- createdDate (LocalDateTime): When the record was created
- updateDate (LocalDateTime): When the record was last updated

**Requirements:**
- Must use JPA annotations for entity mapping
- Must use Lombok annotations to reduce boilerplate code
- Must implement audit fields with automatic timestamp setting
- Must implement bidirectional relationship with BeerOrder

### 2. BeerOrder Entity

**Fields:**
- id (Integer): Primary key
- version (Integer): For optimistic locking
- orderStatus (String): Current status of the order
- orderNumber (String): Unique identifier for the order
- customer (Customer): Reference to the customer who placed the order
- beerOrderLines (Set<BeerOrderLine>): Collection of order line items
- createdDate (LocalDateTime): When the record was created
- updateDate (LocalDateTime): When the record was last updated

**Requirements:**
- Must use JPA annotations for entity mapping
- Must use Lombok annotations to reduce boilerplate code
- Must implement audit fields with automatic timestamp setting
- Must implement bidirectional relationships with Customer and BeerOrderLine
- Must include a convenience method to add BeerOrderLine items

### 3. BeerOrderLine Entity

**Fields:**
- id (Integer): Primary key
- version (Integer): For optimistic locking
- orderQuantity (Integer): Quantity of beer ordered
- beerOrder (BeerOrder): Reference to the parent order
- beer (Beer): Reference to the beer being ordered
- createdDate (LocalDateTime): When the record was created
- updateDate (LocalDateTime): When the record was last updated

**Requirements:**
- Must use JPA annotations for entity mapping
- Must use Lombok annotations to reduce boilerplate code
- Must implement audit fields with automatic timestamp setting
- Must implement relationships with BeerOrder and Beer

### 4. Beer Entity Updates

**Additional Fields:**
- beerOrderLines (Set<BeerOrderLine>): Collection of order lines referencing this beer

**Requirements:**
- Update the existing Beer entity to include the relationship with BeerOrderLine
- Maintain all existing fields and functionality

## Implementation Guidelines

### JPA Annotations

- Use `@Entity` for entity classes
- Use `@Id` and `@GeneratedValue` for primary keys
- Use `@Version` for optimistic locking
- Use `@OneToMany` and `@ManyToOne` for relationships
- Use `@Column` for column-specific configurations
- Use `@PrePersist` and `@PreUpdate` for audit fields

### Lombok Annotations

- Use `@Getter` and `@Setter` for accessors
- Use `@NoArgsConstructor` and `@AllArgsConstructor` for constructors
- Use `@Builder` for builder pattern
- Use `@Builder.Default` for initializing collections

### Relationship Management

- Initialize collections to empty sets to avoid null pointer exceptions
- Use `mappedBy` to specify the owning side of bidirectional relationships
- Implement convenience methods for managing bidirectional relationships
- Use `CascadeType.ALL` for parent-child relationships where appropriate

### Audit Fields

- All entities should include createdDate and updateDate fields
- Implement `@PrePersist` and `@PreUpdate` methods to automatically set these values

## Data Transfer Objects (DTOs)

For each entity, create corresponding DTOs:

1. CustomerDto
2. BeerOrderDto
3. BeerOrderLineDto

Each DTO should:
- Include all relevant fields from the entity
- Exclude complex relationship objects (use IDs instead)
- Use Lombok annotations for clean code

## Mappers

Create MapStruct mappers for each entity-DTO pair:

1. CustomerMapper
2. BeerOrderMapper
3. BeerOrderLineMapper

Each mapper should:
- Handle conversion between entity and DTO in both directions
- Properly manage relationship mappings
- Ignore appropriate fields during entity creation

## Service Layer

Implement service interfaces and implementations for each new entity:

1. CustomerService and CustomerServiceImpl
2. BeerOrderService and BeerOrderServiceImpl
3. BeerOrderLineService and BeerOrderLineServiceImpl

Each service should:
- Define CRUD operations
- Work with DTOs rather than entities
- Use mappers for entity-DTO conversion
- Implement proper business logic and validation

## Controller Layer

Implement REST controllers for each new entity:

1. CustomerController
2. BeerOrderController
3. BeerOrderLineController

Each controller should:
- Follow RESTful principles
- Implement appropriate endpoints for CRUD operations
- Return proper HTTP status codes
- Handle validation and errors appropriately

## Testing

Write comprehensive tests for all layers:

1. Repository tests using @DataJpaTest
2. Service tests with mocked dependencies
3. Controller tests using @WebMvcTest
4. Integration tests for key workflows

Tests should verify:
- CRUD operations work correctly
- Relationships are properly maintained
- Business rules are enforced
- Error handling works as expected

## Implementation Sequence

It is recommended to implement the entities in this order:

1. Update Beer entity with the new relationship
2. Implement Customer entity
3. Implement BeerOrder entity
4. Implement BeerOrderLine entity
5. Implement DTOs and Mappers
6. Implement Services
7. Implement Controllers
8. Write Tests

This sequence ensures that dependencies are available when needed and minimizes refactoring.