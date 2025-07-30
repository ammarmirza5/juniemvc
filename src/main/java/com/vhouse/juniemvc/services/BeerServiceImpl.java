package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.entities.Beer;
import com.vhouse.juniemvc.repositories.BeerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeerServiceImpl implements BeerService {
    
    private final BeerRepository beerRepository;
    
    public BeerServiceImpl(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }
    
    @Override
    public List<Beer> findAll() {
        return beerRepository.findAll();
    }
    
    @Override
    public Optional<Beer> findById(Integer id) {
        return beerRepository.findById(id);
    }
    
    @Override
    public Beer save(Beer beer) {
        return beerRepository.save(beer);
    }
    
    @Override
    public Beer update(Integer id, Beer beer) {
        if (!beerRepository.existsById(id)) {
            return null;
        }
        
        beer.setId(id);
        return beerRepository.save(beer);
    }
    
    @Override
    public void delete(Integer id) {
        beerRepository.deleteById(id);
    }
    
    @Override
    public boolean exists(Integer id) {
        return beerRepository.existsById(id);
    }
}