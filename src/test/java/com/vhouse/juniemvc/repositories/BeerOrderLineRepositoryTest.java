package com.vhouse.juniemvc.repositories;

import com.vhouse.juniemvc.entities.Beer;
import com.vhouse.juniemvc.entities.BeerOrder;
import com.vhouse.juniemvc.entities.BeerOrderLine;
import com.vhouse.juniemvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerOrderLineRepositoryTest {

    @Autowired
    BeerOrderLineRepository beerOrderLineRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    BeerOrder testBeerOrder;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        // Create a test customer
        testCustomer = customerRepository.save(Customer.builder()
                .name("Test Customer")
                .email("test@example.com")
                .phone("123-456-7890")
                .build());

        // Create a test beer order
        testBeerOrder = beerOrderRepository.save(BeerOrder.builder()
                .orderStatus("NEW")
                .orderNumber("ORD-123")
                .customer(testCustomer)
                .build());

        // Create a test beer
        testBeer = beerRepository.save(Beer.builder()
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456789")
                .price(new BigDecimal("9.99"))
                .quantityOnHand(100)
                .build());
    }

    @Test
    void testSaveBeerOrderLine() {
        // Given
        BeerOrderLine beerOrderLine = BeerOrderLine.builder()
                .orderQuantity(10)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        // When
        BeerOrderLine savedBeerOrderLine = beerOrderLineRepository.save(beerOrderLine);

        // Then
        assertThat(savedBeerOrderLine).isNotNull();
        assertThat(savedBeerOrderLine.getId()).isNotNull();
        assertThat(savedBeerOrderLine.getOrderQuantity()).isEqualTo(10);
        assertThat(savedBeerOrderLine.getBeerOrder()).isEqualTo(testBeerOrder);
        assertThat(savedBeerOrderLine.getBeer()).isEqualTo(testBeer);
        assertThat(savedBeerOrderLine.getCreatedDate()).isNotNull();
        assertThat(savedBeerOrderLine.getUpdateDate()).isNotNull();
    }

    @Test
    void testFindAllBeerOrderLines() {
        // Given
        BeerOrderLine beerOrderLine1 = BeerOrderLine.builder()
                .orderQuantity(10)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        BeerOrderLine beerOrderLine2 = BeerOrderLine.builder()
                .orderQuantity(20)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        beerOrderLineRepository.save(beerOrderLine1);
        beerOrderLineRepository.save(beerOrderLine2);

        // When
        List<BeerOrderLine> beerOrderLines = beerOrderLineRepository.findAll();

        // Then
        assertThat(beerOrderLines).isNotNull();
        assertThat(beerOrderLines.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testFindBeerOrderLineById() {
        // Given
        BeerOrderLine beerOrderLine = BeerOrderLine.builder()
                .orderQuantity(10)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        BeerOrderLine savedBeerOrderLine = beerOrderLineRepository.save(beerOrderLine);

        // When
        Optional<BeerOrderLine> foundBeerOrderLine = beerOrderLineRepository.findById(savedBeerOrderLine.getId());

        // Then
        assertThat(foundBeerOrderLine).isPresent();
        assertThat(foundBeerOrderLine.get().getOrderQuantity()).isEqualTo(10);
        assertThat(foundBeerOrderLine.get().getBeerOrder()).isEqualTo(testBeerOrder);
        assertThat(foundBeerOrderLine.get().getBeer()).isEqualTo(testBeer);
    }

    @Test
    void testUpdateBeerOrderLine() {
        // Given
        BeerOrderLine beerOrderLine = BeerOrderLine.builder()
                .orderQuantity(10)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        BeerOrderLine savedBeerOrderLine = beerOrderLineRepository.save(beerOrderLine);

        // When
        savedBeerOrderLine.setOrderQuantity(15);
        BeerOrderLine updatedBeerOrderLine = beerOrderLineRepository.save(savedBeerOrderLine);

        // Then
        assertThat(updatedBeerOrderLine.getOrderQuantity()).isEqualTo(15);
        assertThat(updatedBeerOrderLine.getBeerOrder()).isEqualTo(testBeerOrder);
        assertThat(updatedBeerOrderLine.getBeer()).isEqualTo(testBeer);
    }

    @Test
    void testDeleteBeerOrderLine() {
        // Given
        BeerOrderLine beerOrderLine = BeerOrderLine.builder()
                .orderQuantity(10)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        BeerOrderLine savedBeerOrderLine = beerOrderLineRepository.save(beerOrderLine);

        // When
        beerOrderLineRepository.deleteById(savedBeerOrderLine.getId());
        Optional<BeerOrderLine> deletedBeerOrderLine = beerOrderLineRepository.findById(savedBeerOrderLine.getId());

        // Then
        assertThat(deletedBeerOrderLine).isEmpty();
    }

    @Test
    void testFindAllByBeerOrder() {
        // Given
        BeerOrderLine beerOrderLine1 = BeerOrderLine.builder()
                .orderQuantity(10)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        BeerOrderLine beerOrderLine2 = BeerOrderLine.builder()
                .orderQuantity(20)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        beerOrderLineRepository.save(beerOrderLine1);
        beerOrderLineRepository.save(beerOrderLine2);

        // When
        List<BeerOrderLine> orderLines = beerOrderLineRepository.findAllByBeerOrder(testBeerOrder);

        // Then
        assertThat(orderLines).isNotNull();
        assertThat(orderLines.size()).isGreaterThanOrEqualTo(2);
        assertThat(orderLines).allMatch(line -> line.getBeerOrder().equals(testBeerOrder));
    }

    @Test
    void testFindAllByBeerOrderId() {
        // Given
        BeerOrderLine beerOrderLine1 = BeerOrderLine.builder()
                .orderQuantity(10)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        BeerOrderLine beerOrderLine2 = BeerOrderLine.builder()
                .orderQuantity(20)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        beerOrderLineRepository.save(beerOrderLine1);
        beerOrderLineRepository.save(beerOrderLine2);

        // When
        List<BeerOrderLine> orderLines = beerOrderLineRepository.findAllByBeerOrderId(testBeerOrder.getId());

        // Then
        assertThat(orderLines).isNotNull();
        assertThat(orderLines.size()).isGreaterThanOrEqualTo(2);
        assertThat(orderLines).allMatch(line -> line.getBeerOrder().getId().equals(testBeerOrder.getId()));
    }

    @Test
    void testFindAllByBeer() {
        // Given
        BeerOrderLine beerOrderLine1 = BeerOrderLine.builder()
                .orderQuantity(10)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        BeerOrderLine beerOrderLine2 = BeerOrderLine.builder()
                .orderQuantity(20)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        beerOrderLineRepository.save(beerOrderLine1);
        beerOrderLineRepository.save(beerOrderLine2);

        // When
        List<BeerOrderLine> beerLines = beerOrderLineRepository.findAllByBeer(testBeer);

        // Then
        assertThat(beerLines).isNotNull();
        assertThat(beerLines.size()).isGreaterThanOrEqualTo(2);
        assertThat(beerLines).allMatch(line -> line.getBeer().equals(testBeer));
    }

    @Test
    void testFindAllByBeerId() {
        // Given
        BeerOrderLine beerOrderLine1 = BeerOrderLine.builder()
                .orderQuantity(10)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        BeerOrderLine beerOrderLine2 = BeerOrderLine.builder()
                .orderQuantity(20)
                .beerOrder(testBeerOrder)
                .beer(testBeer)
                .build();

        beerOrderLineRepository.save(beerOrderLine1);
        beerOrderLineRepository.save(beerOrderLine2);

        // When
        List<BeerOrderLine> beerLines = beerOrderLineRepository.findAllByBeerId(testBeer.getId());

        // Then
        assertThat(beerLines).isNotNull();
        assertThat(beerLines.size()).isGreaterThanOrEqualTo(2);
        assertThat(beerLines).allMatch(line -> line.getBeer().getId().equals(testBeer.getId()));
    }
}