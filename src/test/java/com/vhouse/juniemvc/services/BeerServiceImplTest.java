package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.entities.Beer;
import com.vhouse.juniemvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeerServiceImplTest {

    @Mock
    BeerRepository beerRepository;

    @InjectMocks
    BeerServiceImpl beerService;

    Beer testBeer;
    List<Beer> beerList;

    @BeforeEach
    void setUp() {
        testBeer = Beer.builder()
                .id(1)
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .build();

        beerList = new ArrayList<>();
        beerList.add(testBeer);

        Beer anotherBeer = Beer.builder()
                .id(2)
                .beerName("Another Beer")
                .beerStyle("Lager")
                .upc("654321")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(200)
                .build();

        beerList.add(anotherBeer);
    }

    @Test
    void testFindAll() {
        // given
        given(beerRepository.findAll()).willReturn(beerList);

        // when
        List<Beer> foundBeers = beerService.findAll();
        System.out.println("Find out all beers");
        // then
        assertThat(foundBeers).hasSize(2);
        assertThat(foundBeers.get(0).getBeerName()).isEqualTo("Test Beer");
        assertThat(foundBeers.get(1).getBeerName()).isEqualTo("Another Beer");
        verify(beerRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // given
        given(beerRepository.findById(1)).willReturn(Optional.of(testBeer));

        // when
        Optional<Beer> foundBeer = beerService.findById(1);

        // then
        assertThat(foundBeer).isPresent();
        assertThat(foundBeer.get().getId()).isEqualTo(1);
        assertThat(foundBeer.get().getBeerName()).isEqualTo("Test Beer");
        verify(beerRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        // given
        given(beerRepository.findById(999)).willReturn(Optional.empty());

        // when
        Optional<Beer> foundBeer = beerService.findById(999);

        // then
        assertThat(foundBeer).isEmpty();
        verify(beerRepository, times(1)).findById(999);
    }

    @Test
    void testSave() {
        // given
        Beer beerToSave = Beer.builder()
                .beerName("New Beer")
                .beerStyle("Stout")
                .upc("987654")
                .price(new BigDecimal("14.99"))
                .quantityOnHand(50)
                .build();

        Beer savedBeer = Beer.builder()
                .id(3)
                .beerName("New Beer")
                .beerStyle("Stout")
                .upc("987654")
                .price(new BigDecimal("14.99"))
                .quantityOnHand(50)
                .build();

        given(beerRepository.save(any(Beer.class))).willReturn(savedBeer);

        // when
        Beer result = beerService.save(beerToSave);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3);
        assertThat(result.getBeerName()).isEqualTo("New Beer");
        verify(beerRepository, times(1)).save(any(Beer.class));
    }

    @Test
    void testUpdate() {
        // given
        Beer beerToUpdate = Beer.builder()
                .beerName("Updated Beer")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(75)
                .build();

        Beer updatedBeer = Beer.builder()
                .id(1)
                .beerName("Updated Beer")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(75)
                .build();

        given(beerRepository.existsById(1)).willReturn(true);
        given(beerRepository.save(any(Beer.class))).willReturn(updatedBeer);

        // when
        Beer result = beerService.update(1, beerToUpdate);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getBeerName()).isEqualTo("Updated Beer");
        assertThat(result.getBeerStyle()).isEqualTo("Pale Ale");
        verify(beerRepository, times(1)).existsById(1);
        verify(beerRepository, times(1)).save(any(Beer.class));
    }

    @Test
    void testUpdateNotFound() {
        // given
        Beer beerToUpdate = Beer.builder()
                .beerName("Updated Beer")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(75)
                .build();

        given(beerRepository.existsById(999)).willReturn(false);

        // when
        Beer result = beerService.update(999, beerToUpdate);

        // then
        assertThat(result).isNull();
        verify(beerRepository, times(1)).existsById(999);
        verify(beerRepository, never()).save(any(Beer.class));
    }

    @Test
    void testDelete() {
        // given
        doNothing().when(beerRepository).deleteById(anyInt());

        // when
        beerService.delete(1);

        // then
        verify(beerRepository, times(1)).deleteById(1);
    }

    @Test
    void testExists() {
        // given
        given(beerRepository.existsById(1)).willReturn(true);
        given(beerRepository.existsById(999)).willReturn(false);

        // when
        boolean existsId1 = beerService.exists(1);
        boolean existsId999 = beerService.exists(999);

        // then
        assertThat(existsId1).isTrue();
        assertThat(existsId999).isFalse();
        verify(beerRepository, times(1)).existsById(1);
        verify(beerRepository, times(1)).existsById(999);
    }
}