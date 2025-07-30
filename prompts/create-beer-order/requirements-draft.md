### JPA Entity Relationship Implementation with Lombok

Based on the project context and the task to implement JPA relationships with Lombok, I'll provide detailed instructions for implementing entity relationships in a beer ordering system. Since I cannot directly view the ERD image, I'll create comprehensive instructions based on common beer ordering system relationships.

#### Entity Structure Overview

A typical beer ordering system would include these main entities with relationships:
- Beer (already exists in the project)
- Customer
- BeerOrder
- BeerOrderLine

#### Implementation Instructions

### 1. Customer Entity

```java
package com.vhouse.juniemvc.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    private Integer version;

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "customer")
    @Builder.Default
    private Set<BeerOrder> beerOrders = new HashSet<>();

    @Column(updatable = false)
    private LocalDateTime createdDate;
    
    @Column
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}
```

### 2. BeerOrder Entity

```java
package com.vhouse.juniemvc.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    private Integer version;

    private String orderStatus;
    private String orderNumber;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<BeerOrderLine> beerOrderLines = new HashSet<>();

    @Column(updatable = false)
    private LocalDateTime createdDate;
    
    @Column
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }

    // Convenience method to add beer order line
    public void addBeerOrderLine(BeerOrderLine beerOrderLine) {
        if (beerOrderLines == null) {
            beerOrderLines = new HashSet<>();
        }
        beerOrderLines.add(beerOrderLine);
        beerOrderLine.setBeerOrder(this);
    }
}
```

### 3. BeerOrderLine Entity

```java
package com.vhouse.juniemvc.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    private Integer version;

    private Integer orderQuantity;

    @ManyToOne
    private BeerOrder beerOrder;

    @ManyToOne
    private Beer beer;

    @Column(updatable = false)
    private LocalDateTime createdDate;
    
    @Column
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}
```

### 4. Update Beer Entity

Add the relationship to BeerOrderLine:

```java
package com.vhouse.juniemvc.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    private Integer version;

    private String beerName;
    private String beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;

    @OneToMany(mappedBy = "beer")
    @Builder.Default
    private Set<BeerOrderLine> beerOrderLines = new HashSet<>();

    @Column(updatable = false)
    private LocalDateTime createdDate;
    
    @Column
    private LocalDateTime updateDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}
```

### Key Implementation Notes

1. **Lombok Annotations**:
    - `@Getter` and `@Setter`: Generate getters and setters for all fields
    - `@NoArgsConstructor`: Generate a no-args constructor required by JPA
    - `@AllArgsConstructor`: Generate a constructor with all fields
    - `@Builder`: Enable the builder pattern for object creation

2. **JPA Relationship Annotations**:
    - `@OneToMany`: One-to-many relationship (e.g., one Customer has many BeerOrders)
    - `@ManyToOne`: Many-to-one relationship (e.g., many BeerOrderLines belong to one BeerOrder)
    - `mappedBy`: Indicates the field that owns the relationship in bidirectional relationships

3. **Collection Initialization**:
    - Use `@Builder.Default` to initialize collections in builder pattern
    - Initialize collections to empty sets to avoid null pointer exceptions

4. **Bidirectional Relationship Management**:
    - Add convenience methods (like `addBeerOrderLine`) to manage both sides of bidirectional relationships
    - This ensures data consistency

5. **Cascading Operations**:
    - Use `CascadeType.ALL` for parent-child relationships where the child's lifecycle depends on the parent
    - For example, BeerOrderLines are cascaded from BeerOrder

6. **Audit Fields**:
    - All entities include audit fields (createdDate, updateDate)
    - Use `@PrePersist` and `@PreUpdate` to automatically set these values

7. **Version Field**:
    - Include a version field with `@Version` for optimistic locking
    - This helps prevent concurrent modification issues

These implementations follow Spring Boot and JPA best practices while leveraging Lombok to reduce boilerplate code. The relationships are designed to maintain referential integrity and support efficient querying.