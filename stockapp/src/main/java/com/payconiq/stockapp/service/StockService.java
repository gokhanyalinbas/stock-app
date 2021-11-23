package com.payconiq.stockapp.service;


import com.payconiq.stockapp.entity.StockEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StockService {

    StockEntity createStock(StockEntity stockRequestDto);

    StockEntity updateStock(StockEntity stockRequestDto);

    Optional<StockEntity> findById(int id);

    List<StockEntity> allStocks(Pageable pageable);

    void deleteStock(int id);

    void inMemoryDB();


}
