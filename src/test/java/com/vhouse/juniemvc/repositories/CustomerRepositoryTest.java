package com.vhouse.juniemvc.repositories;

import com.vhouse.juniemvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        // Given
        Customer customer = Customer.builder()
                .name("Test Customer")
                .email("test@example.com")
                .phone("123-456-7890")
                .build();

        // When
        Customer savedCustomer = customerRepository.save(customer);

        // Then
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("Test Customer");
        assertThat(savedCustomer.getEmail()).isEqualTo("test@example.com");
        assertThat(savedCustomer.getPhone()).isEqualTo("123-456-7890");
        assertThat(savedCustomer.getCreatedDate()).isNotNull();
        assertThat(savedCustomer.getUpdateDate()).isNotNull();
    }

    @Test
    void testFindAllCustomers() {
        // Given
        Customer customer1 = Customer.builder()
                .name("Customer 1")
                .email("customer1@example.com")
                .phone("111-111-1111")
                .build();

        Customer customer2 = Customer.builder()
                .name("Customer 2")
                .email("customer2@example.com")
                .phone("222-222-2222")
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // When
        List<Customer> customers = customerRepository.findAll();

        // Then
        assertThat(customers).isNotNull();
        assertThat(customers.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testFindCustomerById() {
        // Given
        Customer customer = Customer.builder()
                .name("Find Me")
                .email("findme@example.com")
                .phone("333-333-3333")
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        // When
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());

        // Then
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getName()).isEqualTo("Find Me");
        assertThat(foundCustomer.get().getEmail()).isEqualTo("findme@example.com");
        assertThat(foundCustomer.get().getPhone()).isEqualTo("333-333-3333");
    }

    @Test
    void testUpdateCustomer() {
        // Given
        Customer customer = Customer.builder()
                .name("Original Name")
                .email("original@example.com")
                .phone("444-444-4444")
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        // When
        savedCustomer.setName("Updated Name");
        savedCustomer.setEmail("updated@example.com");
        Customer updatedCustomer = customerRepository.save(savedCustomer);

        // Then
        assertThat(updatedCustomer.getName()).isEqualTo("Updated Name");
        assertThat(updatedCustomer.getEmail()).isEqualTo("updated@example.com");
        assertThat(updatedCustomer.getPhone()).isEqualTo("444-444-4444");
    }

    @Test
    void testDeleteCustomer() {
        // Given
        Customer customer = Customer.builder()
                .name("Delete Me")
                .email("deleteme@example.com")
                .phone("555-555-5555")
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        // When
        customerRepository.deleteById(savedCustomer.getId());
        Optional<Customer> deletedCustomer = customerRepository.findById(savedCustomer.getId());

        // Then
        assertThat(deletedCustomer).isEmpty();
    }
}