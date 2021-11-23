package com.payconiq.stockapp.service;

import com.payconiq.stockapp.entity.StockEntity;
import com.payconiq.stockapp.repository.StockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public StockEntity createStock(StockEntity stockRequestDto) {

        return stockRepository.save(stockRequestDto);
    }

    @Override
    public StockEntity updateStock(StockEntity stockRequestDto) {

        return stockRepository.save(stockRequestDto);
    }

    @Override
    public Optional<StockEntity> findById(int id) {

        Optional<StockEntity> stockEntity = stockRepository.findById(id);
        if (stockEntity.isPresent()) {
            return stockEntity;
        }
        return Optional.empty();
    }

    @Override
    public List<StockEntity> allStocks(Pageable pageable) {


        Page<StockEntity> page = stockRepository.findAll(pageable);
        List<StockEntity> stockResponseDtoList = page.getContent().stream().collect(Collectors.toList());
        return stockResponseDtoList;
    }

    @Override
    public void deleteStock(int id) {

        stockRepository.deleteById(id);
    }

    @Override
    public void inMemoryDB() {
        stockRepository.deleteAll();
        StockEntity stockEntity = StockEntity.builder()
                .name("ACD")
                .currentPrice(1)
                .lastUpdate(new Date(System.currentTimeMillis()))
                .id(1)
                .build();
        stockRepository.save(stockEntity);
        stockEntity = StockEntity.builder()
                .name("ASD")
                .currentPrice(2)
                .lastUpdate(new Date(System.currentTimeMillis()))
                .id(2)
                .build();
        stockRepository.save(stockEntity);
        stockEntity = StockEntity.builder()
                .name("DFF")
                .currentPrice(0.75)
                .lastUpdate(new Date(System.currentTimeMillis()))
                .id(3)
                .build();
        stockRepository.save(stockEntity);
        stockEntity = StockEntity.builder()
                .name("CVD")
                .currentPrice(4)
                .lastUpdate(new Date(System.currentTimeMillis()))
                .id(4)
                .build();
        stockRepository.save(stockEntity);
        stockEntity = StockEntity.builder()
                .name("RTF")
                .currentPrice(6.5)
                .lastUpdate(new Date(System.currentTimeMillis()))
                .id(5)
                .build();
        stockRepository.save(stockEntity);
    }
}
