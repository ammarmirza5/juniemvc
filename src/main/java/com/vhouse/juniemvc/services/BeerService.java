package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.dtos.BeerDto;

import java.util.List;
import java.util.Optional;

public interface BeerService {
    List<BeerDto> findAll();
    Optional<BeerDto> findById(Integer id);
    BeerDto save(BeerDto beerDto);
    BeerDto update(Integer id, BeerDto beerDto);
    void delete(Integer id);
    boolean exists(Integer id);
}