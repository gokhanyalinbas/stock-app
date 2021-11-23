package com.payconiq.stockapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stockapp.dto.StockRequestDto;
import com.payconiq.stockapp.entity.StockEntity;
import com.payconiq.stockapp.exception.StockNotFoundException;
import com.payconiq.stockapp.service.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest({StockController.class})
class StockControllerTest {

    @MockBean
    StockServiceImpl stockService;
    StockRequestDto stockRequestDto;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        stockRequestDto = StockRequestDto.builder()
                .id(50)
                .lastUpdate(new Date(System.currentTimeMillis()))
                .currentPrice(10)
                .name("Test")
                .build();
    }

    @Test
    @DisplayName("Get all stock datas succesfully")
    void getAllStocks() throws Exception {
        StockEntity stockEntity = StockEntity.builder()
                .currentPrice(5)
                .id(50)
                .lastUpdate(new Date(System.currentTimeMillis()))
                .name("Test")
                .build();
        List<StockEntity> stockList = new ArrayList<>();
        stockList.add(stockEntity);
        stockList.add(stockEntity);
        stockList.add(stockEntity);
        stockList.add(stockEntity);
        stockList.add(stockEntity);
        stockList.add(stockEntity);
        Pageable pageable = PageRequest.of(0, 6);
        when(stockService.allStocks(pageable)).thenReturn(stockList);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/stocks").param("page", "0").param("size", "6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(content().json("[{}, {}, {},{}, {}, {}]"));
    }


    @Test
    @DisplayName("Creates stock data succesfully")
    void createStock() throws Exception {

        StockEntity stockEntity = StockEntity.builder()
                .currentPrice(5)
                .id(50)
                .name("Test")
                .build();
        when(stockService.createStock(any(StockEntity.class))).thenReturn(stockEntity);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/stocks")
                                .content(new ObjectMapper().writeValueAsString(stockEntity))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(stockEntity)));
    }

    @Test
    @DisplayName("Creates stock data with invalid arguments")
    void createStockWithInvalidArguments() throws Exception {

        StockEntity stockEntity = StockEntity.builder()
                .currentPrice(-1)
                .id(50)
                .name("Test")
                .build();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/stocks")
                                .content(new ObjectMapper().writeValueAsString(stockEntity))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
        stockEntity.setName("");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/stocks")
                                .content(new ObjectMapper().writeValueAsString(stockEntity))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Finds stock data with ID ")
    void findById() throws Exception {
        StockEntity stockEntity = StockEntity.builder()
                .currentPrice(5)
                .id(50)
                .name("Test")
                .build();
        Optional<StockEntity> stock = Optional.of(stockEntity);

        when(stockService.findById(stockEntity.getId())).thenReturn(stock);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/stocks/{id}", stockEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(stock.get())));

    }

    @Test
    @DisplayName("Finds stock data with wrong ID ")
    void findByWrongId() throws Exception {
        StockEntity stockEntity = StockEntity.builder()
                .currentPrice(5)
                .id(50)
                .name("Test")
                .build();

        when(stockService.findById(stockEntity.getId())).thenThrow(new StockNotFoundException("Not found"));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/stocks/{id}", stockEntity.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Updates stock data")
    void updateStock() throws Exception {


        StockEntity stockEntity = StockEntity.builder()
                .currentPrice(5)
                .id(50)
                .name("Test-Updated")
                .build();
        Optional<StockEntity> stock = Optional.of(stockEntity);
        when(stockService.updateStock(any(StockEntity.class))).thenReturn(stockEntity);
        when(stockService.findById(any(Integer.class))).thenReturn(stock);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/stocks/{id}", stockEntity.getId())
                                .content(new ObjectMapper().writeValueAsString(stockEntity))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("Test-Updated"));

    }

    @Test
    @DisplayName("Updates stock data with wrong id")
    void updateStockWithWrongId() throws Exception {


        StockEntity stockEntity = StockEntity.builder()
                .currentPrice(5)
                .id(50)
                .name("Test-Updated")
                .build();
        Optional<StockEntity> stock = Optional.ofNullable(null);
        when(stockService.updateStock(any(StockEntity.class))).thenReturn(stockEntity);
        when(stockService.findById(any(Integer.class))).thenReturn(stock);


        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/stocks/{id}", stockEntity.getId())
                                .content(new ObjectMapper().writeValueAsString(stockEntity))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deletes stock data")
    void deleteStock() throws Exception {
        StockEntity stockEntity = StockEntity.builder()
                .currentPrice(5)
                .id(50)
                .name("Test-Updated")
                .build();
        Optional<StockEntity> stock = Optional.of(stockEntity);
        when(stockService.findById(any(Integer.class))).thenReturn(stock);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/stocks/50")
                                .content(new ObjectMapper().writeValueAsString(stockEntity))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(stockEntity)));


    }

    @Test
    @DisplayName("Deletes stock data with wrong id")
    void deleteStockWithWrongId() throws Exception {
        StockEntity stockEntity = StockEntity.builder()
                .currentPrice(5)
                .id(50)
                .name("Test-Updated")
                .build();
        Optional<StockEntity> stock = Optional.ofNullable(null);
        when(stockService.updateStock(any(StockEntity.class))).thenReturn(stockEntity);
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/stocks/{id}", stockEntity.getId())
                                .content(new ObjectMapper().writeValueAsString(stockEntity))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
}