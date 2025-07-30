package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.entities.Beer;

import java.util.List;
import java.util.Optional;

public interface BeerService {
    List<Beer> findAll();
    Optional<Beer> findById(Integer id);
    Beer save(Beer beer);
    Beer update(Integer id, Beer beer);
    void delete(Integer id);
    boolean exists(Integer id);
}