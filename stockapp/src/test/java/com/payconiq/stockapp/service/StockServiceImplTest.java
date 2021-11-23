package com.payconiq.stockapp.service;

import com.payconiq.stockapp.entity.StockEntity;
import com.payconiq.stockapp.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {


    StockEntity stockEntity;
    private StockServiceImpl stockService;
    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    void init() {
        stockService = new StockServiceImpl(stockRepository);
        stockEntity = StockEntity.builder()
                .id(50)
                .lastUpdate(new Date(System.currentTimeMillis()))
                .currentPrice(10)
                .name("Test")
                .build();
    }

    @Test
    @DisplayName("Creating stock record service layer test")
    void createStock() {
        when(stockRepository.save(any(StockEntity.class))).thenReturn(stockEntity);
        StockEntity result = stockService.createStock(stockEntity);
        assertThat(result).isNotNull();
        verify(stockRepository, times(1)).save(any(StockEntity.class));

    }

    @Test
    @DisplayName("updates stock record service layer test")
    void updateStock() {
        when(stockRepository.save(any(StockEntity.class))).thenReturn(stockEntity);
        stockEntity.setCurrentPrice(100);
        StockEntity result = stockService.updateStock(stockEntity);
        assertThat(result.getCurrentPrice()).isEqualTo(100);
        verify(stockRepository, times(1)).save(any(StockEntity.class));
    }

    @Test
    @DisplayName("Finds by id service layer test")
    void findById() {
        when(stockRepository.findById(any(Integer.class))).thenReturn(Optional.of(stockEntity));
        Optional<StockEntity> result = stockService.findById(stockEntity.getId());
        assertThat(result.get().getId()).isEqualTo(50);
        verify(stockRepository, times(1)).findById(any(Integer.class));
    }

    @Test
    @DisplayName("Finds all data with page and size")
    void allStocks() {

        List<StockEntity> stockList = new ArrayList<>();
        stockList.add(stockEntity);
        stockList.add(stockEntity);
        stockList.add(stockEntity);
        stockList.add(stockEntity);
        stockList.add(stockEntity);
        stockList.add(stockEntity);
        Page<StockEntity> page = new PageImpl<>(stockList);
        Pageable pageable = PageRequest.of(0, 6);
        when(stockRepository.findAll(pageable)).thenReturn(page);

        List<StockEntity> result = stockService.allStocks(pageable);
        assertThat(result.size()).isEqualTo(6);
        assertThat(result.get(5).getId()).isEqualTo(50);

        verify(stockRepository, times(1)).findAll(any(Pageable.class));


    }

    @Test
    void deleteStock() {
        doNothing().when(stockRepository).deleteById(stockEntity.getId());
        stockService.deleteStock(stockEntity.getId());
        stockService.deleteStock(stockEntity.getId());
        verify(stockRepository, times(2)).deleteById(any(Integer.class));
    }
}