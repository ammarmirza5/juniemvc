package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.dtos.CustomerDto;
import com.vhouse.juniemvc.entities.Customer;
import com.vhouse.juniemvc.mappers.CustomerMapper;
import com.vhouse.juniemvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerMapper customerMapper;

    @InjectMocks
    CustomerServiceImpl customerService;

    Customer testCustomer;
    CustomerDto testCustomerDto;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.builder()
                .id(1)
                .name("Test Customer")
                .email("test@example.com")
                .phone("123-456-7890")
                .build();

        testCustomerDto = CustomerDto.builder()
                .id(1)
                .name("Test Customer")
                .email("test@example.com")
                .phone("123-456-7890")
                .build();
    }

    @Test
    void getAllCustomers() {
        // Given
        List<Customer> customers = new ArrayList<>();
        customers.add(testCustomer);
        customers.add(Customer.builder().id(2).name("Another Customer").build());

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerMapper.customerToCustomerDto(any(Customer.class))).thenReturn(testCustomerDto);

        // When
        List<CustomerDto> result = customerService.getAllCustomers();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        verify(customerRepository, times(1)).findAll();
        verify(customerMapper, times(2)).customerToCustomerDto(any(Customer.class));
    }

    @Test
    void getCustomerById() {
        // Given
        when(customerRepository.findById(1)).thenReturn(Optional.of(testCustomer));
        when(customerMapper.customerToCustomerDto(testCustomer)).thenReturn(testCustomerDto);

        // When
        Optional<CustomerDto> result = customerService.getCustomerById(1);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1);
        assertThat(result.get().getName()).isEqualTo("Test Customer");
        verify(customerRepository, times(1)).findById(1);
        verify(customerMapper, times(1)).customerToCustomerDto(testCustomer);
    }

    @Test
    void getCustomerByIdNotFound() {
        // Given
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<CustomerDto> result = customerService.getCustomerById(999);

        // Then
        assertThat(result).isEmpty();
        verify(customerRepository, times(1)).findById(999);
        verify(customerMapper, never()).customerToCustomerDto(any(Customer.class));
    }

    @Test
    void saveNewCustomer() {
        // Given
        when(customerMapper.customerDtoToCustomer(testCustomerDto)).thenReturn(testCustomer);
        when(customerRepository.save(testCustomer)).thenReturn(testCustomer);
        when(customerMapper.customerToCustomerDto(testCustomer)).thenReturn(testCustomerDto);

        // When
        CustomerDto result = customerService.saveNewCustomer(testCustomerDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Test Customer");
        verify(customerMapper, times(1)).customerDtoToCustomer(testCustomerDto);
        verify(customerRepository, times(1)).save(testCustomer);
        verify(customerMapper, times(1)).customerToCustomerDto(testCustomer);
    }

    @Test
    void updateCustomerById() {
        // Given
        CustomerDto updatedDto = CustomerDto.builder()
                .name("Updated Name")
                .email("updated@example.com")
                .phone("987-654-3210")
                .build();

        when(customerRepository.findById(1)).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(testCustomer)).thenReturn(testCustomer);
        when(customerMapper.customerToCustomerDto(testCustomer)).thenReturn(testCustomerDto);

        // When
        Optional<CustomerDto> result = customerService.updateCustomerById(1, updatedDto);

        // Then
        assertThat(result).isPresent();
        verify(customerRepository, times(1)).findById(1);
        verify(customerMapper, times(1)).updateCustomerFromDto(updatedDto, testCustomer);
        verify(customerRepository, times(1)).save(testCustomer);
        verify(customerMapper, times(1)).customerToCustomerDto(testCustomer);
    }

    @Test
    void updateCustomerByIdNotFound() {
        // Given
        CustomerDto updatedDto = CustomerDto.builder()
                .name("Updated Name")
                .email("updated@example.com")
                .phone("987-654-3210")
                .build();

        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<CustomerDto> result = customerService.updateCustomerById(999, updatedDto);

        // Then
        assertThat(result).isEmpty();
        verify(customerRepository, times(1)).findById(999);
        verify(customerMapper, never()).updateCustomerFromDto(any(CustomerDto.class), any(Customer.class));
        verify(customerRepository, never()).save(any(Customer.class));
        verify(customerMapper, never()).customerToCustomerDto(any(Customer.class));
    }

    @Test
    void deleteCustomerById() {
        // Given
        when(customerRepository.existsById(1)).thenReturn(true);

        // When
        Boolean result = customerService.deleteCustomerById(1);

        // Then
        assertThat(result).isTrue();
        verify(customerRepository, times(1)).existsById(1);
        verify(customerRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteCustomerByIdNotFound() {
        // Given
        when(customerRepository.existsById(999)).thenReturn(false);

        // When
        Boolean result = customerService.deleteCustomerById(999);

        // Then
        assertThat(result).isFalse();
        verify(customerRepository, times(1)).existsById(999);
        verify(customerRepository, never()).deleteById(999);
    }
}