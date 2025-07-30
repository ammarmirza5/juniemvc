package com.vhouse.juniemvc.repositories;

import com.vhouse.juniemvc.entities.BeerOrder;
import com.vhouse.juniemvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    Customer testCustomer;

    @BeforeEach
    void setUp() {
        // Create a test customer for all tests
        testCustomer = customerRepository.save(Customer.builder()
                .name("Test Customer")
                .email("test@example.com")
                .phone("123-456-7890")
                .build());
    }

    @Test
    void testSaveBeerOrder() {
        // Given
        BeerOrder beerOrder = BeerOrder.builder()
                .orderStatus("NEW")
                .orderNumber("ORD-123")
                .customer(testCustomer)
                .build();

        // When
        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);

        // Then
        assertThat(savedBeerOrder).isNotNull();
        assertThat(savedBeerOrder.getId()).isNotNull();
        assertThat(savedBeerOrder.getOrderStatus()).isEqualTo("NEW");
        assertThat(savedBeerOrder.getOrderNumber()).isEqualTo("ORD-123");
        assertThat(savedBeerOrder.getCustomer()).isEqualTo(testCustomer);
        assertThat(savedBeerOrder.getCreatedDate()).isNotNull();
        assertThat(savedBeerOrder.getUpdateDate()).isNotNull();
    }

    @Test
    void testFindAllBeerOrders() {
        // Given
        BeerOrder beerOrder1 = BeerOrder.builder()
                .orderStatus("NEW")
                .orderNumber("ORD-111")
                .customer(testCustomer)
                .build();

        BeerOrder beerOrder2 = BeerOrder.builder()
                .orderStatus("PROCESSING")
                .orderNumber("ORD-222")
                .customer(testCustomer)
                .build();

        beerOrderRepository.save(beerOrder1);
        beerOrderRepository.save(beerOrder2);

        // When
        List<BeerOrder> beerOrders = beerOrderRepository.findAll();

        // Then
        assertThat(beerOrders).isNotNull();
        assertThat(beerOrders.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testFindBeerOrderById() {
        // Given
        BeerOrder beerOrder = BeerOrder.builder()
                .orderStatus("NEW")
                .orderNumber("ORD-333")
                .customer(testCustomer)
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);

        // When
        Optional<BeerOrder> foundBeerOrder = beerOrderRepository.findById(savedBeerOrder.getId());

        // Then
        assertThat(foundBeerOrder).isPresent();
        assertThat(foundBeerOrder.get().getOrderStatus()).isEqualTo("NEW");
        assertThat(foundBeerOrder.get().getOrderNumber()).isEqualTo("ORD-333");
        assertThat(foundBeerOrder.get().getCustomer()).isEqualTo(testCustomer);
    }

    @Test
    void testUpdateBeerOrder() {
        // Given
        BeerOrder beerOrder = BeerOrder.builder()
                .orderStatus("NEW")
                .orderNumber("ORD-444")
                .customer(testCustomer)
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);

        // When
        savedBeerOrder.setOrderStatus("COMPLETED");
        BeerOrder updatedBeerOrder = beerOrderRepository.save(savedBeerOrder);

        // Then
        assertThat(updatedBeerOrder.getOrderStatus()).isEqualTo("COMPLETED");
        assertThat(updatedBeerOrder.getOrderNumber()).isEqualTo("ORD-444");
        assertThat(updatedBeerOrder.getCustomer()).isEqualTo(testCustomer);
    }

    @Test
    void testDeleteBeerOrder() {
        // Given
        BeerOrder beerOrder = BeerOrder.builder()
                .orderStatus("NEW")
                .orderNumber("ORD-555")
                .customer(testCustomer)
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);

        // When
        beerOrderRepository.deleteById(savedBeerOrder.getId());
        Optional<BeerOrder> deletedBeerOrder = beerOrderRepository.findById(savedBeerOrder.getId());

        // Then
        assertThat(deletedBeerOrder).isEmpty();
    }

    @Test
    void testFindAllByCustomer() {
        // Given
        BeerOrder beerOrder1 = BeerOrder.builder()
                .orderStatus("NEW")
                .orderNumber("ORD-666")
                .customer(testCustomer)
                .build();

        BeerOrder beerOrder2 = BeerOrder.builder()
                .orderStatus("PROCESSING")
                .orderNumber("ORD-777")
                .customer(testCustomer)
                .build();

        beerOrderRepository.save(beerOrder1);
        beerOrderRepository.save(beerOrder2);

        // When
        List<BeerOrder> customerOrders = beerOrderRepository.findAllByCustomer(testCustomer);

        // Then
        assertThat(customerOrders).isNotNull();
        assertThat(customerOrders.size()).isGreaterThanOrEqualTo(2);
        assertThat(customerOrders).allMatch(order -> order.getCustomer().equals(testCustomer));
    }

    @Test
    void testFindAllByCustomerId() {
        // Given
        BeerOrder beerOrder1 = BeerOrder.builder()
                .orderStatus("NEW")
                .orderNumber("ORD-888")
                .customer(testCustomer)
                .build();

        BeerOrder beerOrder2 = BeerOrder.builder()
                .orderStatus("PROCESSING")
                .orderNumber("ORD-999")
                .customer(testCustomer)
                .build();

        beerOrderRepository.save(beerOrder1);
        beerOrderRepository.save(beerOrder2);

        // When
        List<BeerOrder> customerOrders = beerOrderRepository.findAllByCustomerId(testCustomer.getId());

        // Then
        assertThat(customerOrders).isNotNull();
        assertThat(customerOrders.size()).isGreaterThanOrEqualTo(2);
        assertThat(customerOrders).allMatch(order -> order.getCustomer().getId().equals(testCustomer.getId()));
    }
}