package com.vhouse.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vhouse.juniemvc.entities.Beer;
import com.vhouse.juniemvc.services.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    List<Beer> beerList;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        beerList = new ArrayList<>();
        
        testBeer = Beer.builder()
                .id(1)
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .build();
        
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
    void testListBeers() throws Exception {
        given(beerService.findAll()).willReturn(beerList);

        mockMvc.perform(get("/api/v1/beers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].beerName", is("Test Beer")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].beerName", is("Another Beer")));
    }

    @Test
    void testGetBeerById() throws Exception {
        given(beerService.findById(1)).willReturn(Optional.of(testBeer));

        mockMvc.perform(get("/api/v1/beers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("Test Beer")));
    }

    @Test
    void testGetBeerByIdNotFound() throws Exception {
        given(beerService.findById(999)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/beers/999")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBeer() throws Exception {
        Beer newBeer = Beer.builder()
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

        given(beerService.save(any(Beer.class))).willReturn(savedBeer);

        mockMvc.perform(post("/api/v1/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBeer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.beerName", is("New Beer")));
    }
    
    @Test
    void testUpdateBeer() throws Exception {
        Beer updatedBeer = Beer.builder()
                .beerName("Updated Beer")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(75)
                .build();
        
        Beer returnedBeer = Beer.builder()
                .id(1)
                .beerName("Updated Beer")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(75)
                .build();
        
        given(beerService.exists(1)).willReturn(true);
        given(beerService.update(anyInt(), any(Beer.class))).willReturn(returnedBeer);
        
        mockMvc.perform(put("/api/v1/beers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBeer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("Updated Beer")))
                .andExpect(jsonPath("$.beerStyle", is("Pale Ale")));
    }
    
    @Test
    void testUpdateBeerNotFound() throws Exception {
        Beer updatedBeer = Beer.builder()
                .beerName("Updated Beer")
                .beerStyle("Pale Ale")
                .upc("123456")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(75)
                .build();
        
        given(beerService.exists(999)).willReturn(false);
        
        mockMvc.perform(put("/api/v1/beers/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBeer)))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testDeleteBeer() throws Exception {
        given(beerService.exists(1)).willReturn(true);
        doNothing().when(beerService).delete(1);
        
        mockMvc.perform(delete("/api/v1/beers/1"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void testDeleteBeerNotFound() throws Exception {
        given(beerService.exists(999)).willReturn(false);
        
        mockMvc.perform(delete("/api/v1/beers/999"))
                .andExpect(status().isNotFound());
    }
}