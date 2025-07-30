package com.vhouse.juniemvc.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for BeerOrderLine entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerOrderLineDto {
    private Integer id;
    private Integer version;
    private Integer orderQuantity;
    private Integer beerOrderId;
    private Integer beerId;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}