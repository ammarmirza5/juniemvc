package com.vhouse.juniemvc.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data Transfer Object for BeerOrder entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrderDto {
    private Integer id;
    private Integer version;
    private String orderStatus;
    private String orderNumber;
    private Integer customerId;
    private Set<BeerOrderLineDto> beerOrderLines;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}