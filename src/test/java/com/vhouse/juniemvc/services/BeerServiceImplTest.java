package com.vhouse.juniemvc.services;

import com.vhouse.juniemvc.dtos.BeerDto;
import com.vhouse.juniemvc.entities.Beer;
import com.vhouse.juniemvc.mappers.BeerMapper;
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
    
    @Mock
    BeerMapper beerMapper;

    @InjectMocks
    BeerServiceImpl beerService;

    Beer testBeer;
    BeerDto testBeerDto;
    List<Beer> beerList;
    List<BeerDto> beerDtoList;

    @BeforeEach
    void setUp() {
        // Set up entity objects
        testBeer = Beer.builder()
                .id(1)
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .build();

        Beer anotherBeer = Beer.builder()
                .id(2)
                .beerName("Another Beer")
                .beerStyle("Lager")
                .upc("654321")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(200)
                .build();

        beerList = new ArrayList<>();
        beerList.add(testBeer);
        beerList.add(anotherBeer);

        // Set up DTO objects
        testBeerDto = BeerDto.builder()
                .id(1)
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .build();

        BeerDto anotherBeerDto = BeerDto.builder()
                .id(2)
                .beerName("Another Beer")
                .beerStyle("Lager")
                .upc("654321")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(200)
                .build();

        beerDtoList = new ArrayList<>();
        beerDtoList.add(testBeerDto);
        beerDtoList.add(anotherBeerDto);
    }

    @Test
    void testFindAll() {
        // given
        given(beerRepository.findAll()).willReturn(beerList);
        given(beerMapper.beerToBeerDto(testBeer)).willReturn(testBeerDto);
        given(beerMapper.beerToBeerDto(beerList.get(1))).willReturn(beerDtoList.get(1));

        // when
        List<BeerDto> foundBeers = beerService.findAll();

        // then
        assertThat(foundBeers).hasSize(2);
        assertThat(foundBeers.get(0).getBeerName()).isEqualTo("Test Beer");
        assertThat(foundBeers.get(1).getBeerName()).isEqualTo("Another Beer");
        verify(beerRepository, times(1)).findAll();
        verify(beerMapper, times(2)).beerToBeerDto(any(Beer.class));
    }

    @Test
    void testFindById() {
        // given
        given(beerRepository.findById(1)).willReturn(Optional.of(testBeer));
        given(beerMapper.beerToBeerDto(testBeer)).willReturn(testBeerDto);

        // when
        Optional<BeerDto> foundBeer = beerService.findById(1);

        // then
        assertThat(foundBeer).isPresent();
        assertThat(foundBeer.get().getId()).isEqualTo(1);
        assertThat(foundBeer.get().getBeerName()).isEqualTo("Test Beer");
        verify(beerRepository, times(1)).findById(1);
        verify(beerMapper, times(1)).beerToBeerDto(any(Beer.class));
    }

    @Test
    void testFindByIdNotFound() {
        // given
        given(beerRepository.findById(999)).willReturn(Optional.empty());

        // when
        Optional<BeerDto> foundBeer = beerService.findById(999);

        // then
        assertThat(foundBeer).isEmpty();
        verify(beerRepository, times(1)).findById(999);
        verify(beerMapper, never()).beerToBeerDto(any(Beer.class));
    }

    @Test
    void testSave() {
        // given
        BeerDto beerDtoToSave = BeerDto.builder()
                .beerName("New Beer")
                .beerStyle("Stout")
                .upc("987654")
                .price(new BigDecimal("14.99"))
                .quantityOnHand(50)
                .build();

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

        BeerDto savedBeerDto = BeerDto.builder()
                .id(3)
                .beerName("New Beer")
                .beerStyle("Stout")
                .upc("987654")
                .price(new BigDecimal("14.99"))
                .quantityOnHand(50)
                .build();

        given(beerMapper.beerDtoToBeer(beerDtoToSave)).willReturn(beerToSave);
        given(beerRepository.save(any(Beer.class))).willReturn(savedBeer);
        given(beerMapper.beerToBeerDto(savedBeer)).willReturn(savedBeerDto);

        // when
        BeerDto result = beerService.save(beerDtoToSave);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3);
        assertThat(result.getBeerName()).isEqualTo("New Beer");
        verify(beerMapper, times(1)).beerDtoToBeer(any(BeerDto.class));
        verify(beerRepository, times(1)).save(any(Beer.class));
        verify(beerMapper, times(1)).beerToBeerDto(any(Beer.class));
    }

    @Test
    void testUpdate() {
        // given
        BeerDto beerDtoToUpdate = BeerDto.builder()
                .beerName("Updated Beer")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(75)
                .build();

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

        BeerDto updatedBeerDto = BeerDto.builder()
                .id(1)
                .beerName("Updated Beer")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(75)
                .build();

        given(beerRepository.existsById(1)).willReturn(true);
        given(beerMapper.beerDtoToBeer(beerDtoToUpdate)).willReturn(beerToUpdate);
        given(beerRepository.save(any(Beer.class))).willReturn(updatedBeer);
        given(beerMapper.beerToBeerDto(updatedBeer)).willReturn(updatedBeerDto);

        // when
        BeerDto result = beerService.update(1, beerDtoToUpdate);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getBeerName()).isEqualTo("Updated Beer");
        assertThat(result.getBeerStyle()).isEqualTo("Pale Ale");
        verify(beerRepository, times(1)).existsById(1);
        verify(beerMapper, times(1)).beerDtoToBeer(any(BeerDto.class));
        verify(beerRepository, times(1)).save(any(Beer.class));
        verify(beerMapper, times(1)).beerToBeerDto(any(Beer.class));
    }

    @Test
    void testUpdateNotFound() {
        // given
        BeerDto beerDtoToUpdate = BeerDto.builder()
                .beerName("Updated Beer")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(75)
                .build();

        given(beerRepository.existsById(999)).willReturn(false);

        // when
        BeerDto result = beerService.update(999, beerDtoToUpdate);

        // then
        assertThat(result).isNull();
        verify(beerRepository, times(1)).existsById(999);
        verify(beerRepository, never()).save(any(Beer.class));
        verify(beerMapper, never()).beerDtoToBeer(any(BeerDto.class));
        verify(beerMapper, never()).beerToBeerDto(any(Beer.class));
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