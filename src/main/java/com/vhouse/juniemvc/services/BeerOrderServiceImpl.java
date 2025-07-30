package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.dtos.BeerOrderDto;
import com.vhouse.juniemvc.entities.BeerOrder;
import com.vhouse.juniemvc.mappers.BeerOrderMapper;
import com.vhouse.juniemvc.repositories.BeerOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of BeerOrderService
 */
@Service
public class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    public BeerOrderServiceImpl(BeerOrderRepository beerOrderRepository, BeerOrderMapper beerOrderMapper) {
        this.beerOrderRepository = beerOrderRepository;
        this.beerOrderMapper = beerOrderMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeerOrderDto> getAllBeerOrders() {
        return beerOrderRepository.findAll()
                .stream()
                .map(beerOrderMapper::beerOrderToBeerOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BeerOrderDto> getBeerOrderById(Integer id) {
        return beerOrderRepository.findById(id)
                .map(beerOrderMapper::beerOrderToBeerOrderDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeerOrderDto> getBeerOrdersByCustomerId(Integer customerId) {
        return beerOrderRepository.findAllByCustomerId(customerId)
                .stream()
                .map(beerOrderMapper::beerOrderToBeerOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BeerOrderDto saveNewBeerOrder(BeerOrderDto beerOrderDto) {
        BeerOrder beerOrder = beerOrderMapper.beerOrderDtoToBeerOrder(beerOrderDto);
        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
        return beerOrderMapper.beerOrderToBeerOrderDto(savedBeerOrder);
    }

    @Override
    @Transactional
    public Optional<BeerOrderDto> updateBeerOrderById(Integer id, BeerOrderDto beerOrderDto) {
        return beerOrderRepository.findById(id)
                .map(beerOrder -> {
                    beerOrderMapper.updateBeerOrderFromDto(beerOrderDto, beerOrder);
                    return beerOrderMapper.beerOrderToBeerOrderDto(beerOrderRepository.save(beerOrder));
                });
    }

    @Override
    @Transactional
    public Boolean deleteBeerOrderById(Integer id) {
        if (beerOrderRepository.existsById(id)) {
            beerOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}