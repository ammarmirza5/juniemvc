package com.vhouse.juniemvc.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data Transfer Object for Customer entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private Integer id;
    private Integer version;
    private String name;
    private String email;
    private String phone;
    private Set<Integer> beerOrderIds;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}