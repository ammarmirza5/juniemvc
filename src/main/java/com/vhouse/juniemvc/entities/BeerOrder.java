package com.vhouse.juniemvc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "beerOrder")
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

    /**
     * Convenience method to add a beer order line to this order
     * @param beerOrderLine The beer order line to add
     */
    public void addBeerOrderLine(BeerOrderLine beerOrderLine) {
        if (beerOrderLine != null) {
            if (beerOrderLines == null) {
                beerOrderLines = new HashSet<>();
            }
            beerOrderLines.add(beerOrderLine);
            beerOrderLine.setBeerOrder(this);
        }
    }
}