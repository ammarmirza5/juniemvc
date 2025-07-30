package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.dtos.BeerDto;
import com.vhouse.juniemvc.entities.Beer;
import com.vhouse.juniemvc.mappers.BeerMapper;
import com.vhouse.juniemvc.repositories.BeerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeerServiceImpl implements BeerService {
    
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    
    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }
    
    @Override
    public List<BeerDto> findAll() {
        return beerRepository.findAll().stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<BeerDto> findById(Integer id) {
        return beerRepository.findById(id)
                .map(beerMapper::beerToBeerDto);
    }
    
    @Override
    public BeerDto save(BeerDto beerDto) {
        Beer beer = beerMapper.beerDtoToBeer(beerDto);
        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.beerToBeerDto(savedBeer);
    }
    
    @Override
    public BeerDto update(Integer id, BeerDto beerDto) {
        if (!beerRepository.existsById(id)) {
            return null;
        }
        
        Beer beer = beerMapper.beerDtoToBeer(beerDto);
        beer.setId(id);
        Beer updatedBeer = beerRepository.save(beer);
        return beerMapper.beerToBeerDto(updatedBeer);
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