package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.dtos.CustomerDto;
import com.vhouse.juniemvc.entities.Customer;
import com.vhouse.juniemvc.mappers.CustomerMapper;
import com.vhouse.juniemvc.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of CustomerService
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDto> getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDto);
    }

    @Override
    @Transactional
    public CustomerDto saveNewCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDto(savedCustomer);
    }

    @Override
    @Transactional
    public Optional<CustomerDto> updateCustomerById(Integer id, CustomerDto customerDto) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customerMapper.updateCustomerFromDto(customerDto, customer);
                    return customerMapper.customerToCustomerDto(customerRepository.save(customer));
                });
    }

    @Override
    @Transactional
    public Boolean deleteCustomerById(Integer id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}