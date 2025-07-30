package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.dtos.BeerOrderLineDto;
import com.vhouse.juniemvc.entities.BeerOrderLine;
import com.vhouse.juniemvc.mappers.BeerOrderLineMapper;
import com.vhouse.juniemvc.repositories.BeerOrderLineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of BeerOrderLineService
 */
@Service
public class BeerOrderLineServiceImpl implements BeerOrderLineService {

    private final BeerOrderLineRepository beerOrderLineRepository;
    private final BeerOrderLineMapper beerOrderLineMapper;

    public BeerOrderLineServiceImpl(BeerOrderLineRepository beerOrderLineRepository, BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineRepository = beerOrderLineRepository;
        this.beerOrderLineMapper = beerOrderLineMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeerOrderLineDto> getAllBeerOrderLines() {
        return beerOrderLineRepository.findAll()
                .stream()
                .map(beerOrderLineMapper::beerOrderLineToBeerOrderLineDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeerOrderLineDto> getBeerOrderLineById(Integer id) {
        return beerOrderLineRepository.findById(id)
                .map(beerOrderLineMapper::beerOrderLineToBeerOrderLineDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeerOrderLineDto> getBeerOrderLinesByBeerOrderId(Integer beerOrderId) {
        return beerOrderLineRepository.findAllByBeerOrderId(beerOrderId)
                .stream()
                .map(beerOrderLineMapper::beerOrderLineToBeerOrderLineDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeerOrderLineDto> getBeerOrderLinesByBeerId(Integer beerId) {
        return beerOrderLineRepository.findAllByBeerId(beerId)
                .stream()
                .map(beerOrderLineMapper::beerOrderLineToBeerOrderLineDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BeerOrderLineDto saveNewBeerOrderLine(BeerOrderLineDto beerOrderLineDto) {
        BeerOrderLine beerOrderLine = beerOrderLineMapper.beerOrderLineDtoToBeerOrderLine(beerOrderLineDto);
        BeerOrderLine savedBeerOrderLine = beerOrderLineRepository.save(beerOrderLine);
        return beerOrderLineMapper.beerOrderLineToBeerOrderLineDto(savedBeerOrderLine);
    }

    @Override
    @Transactional
    public Optional<BeerOrderLineDto> updateBeerOrderLineById(Integer id, BeerOrderLineDto beerOrderLineDto) {
        return beerOrderLineRepository.findById(id)
                .map(beerOrderLine -> {
                    beerOrderLineMapper.updateBeerOrderLineFromDto(beerOrderLineDto, beerOrderLine);
                    return beerOrderLineMapper.beerOrderLineToBeerOrderLineDto(beerOrderLineRepository.save(beerOrderLine));
                });
    }

    @Override
    @Transactional
    public Boolean deleteBeerOrderLineById(Integer id) {
        if (beerOrderLineRepository.existsById(id)) {
            beerOrderLineRepository.deleteById(id);
            return true;
        }
        return false;
    }
}