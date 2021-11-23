package com.payconiq.stockapp.controller;

import com.payconiq.stockapp.dto.StockRequestDto;
import com.payconiq.stockapp.dto.StockResponseDto;
import com.payconiq.stockapp.entity.StockEntity;
import com.payconiq.stockapp.exception.StockNotFoundException;
import com.payconiq.stockapp.service.StockServiceImpl;
import com.payconiq.stockapp.util.StockMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class StockController {

    StockServiceImpl stockService;

    public StockController(StockServiceImpl stockService) {
        this.stockService = stockService;
    }


    @GetMapping("/stocks")
    @ApiOperation("Gets All stock data with page and size param")
    public ResponseEntity<List<StockResponseDto>> getAllStocks(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<StockResponseDto> stockResponseDtoList;
        stockResponseDtoList = stockService.allStocks(pageable).stream()
                .map(StockMapper::mapEntityToResponseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(stockResponseDtoList, HttpStatus.OK);


    }

    @PostMapping("/stocks")
    @ApiOperation("Create new stock record")
    public ResponseEntity<StockResponseDto> createStock(@Valid @RequestBody StockRequestDto stockRequestDto) {
        return new ResponseEntity<>(StockMapper.mapEntityToResponseDto(stockService.createStock(StockMapper.mapRequestDTOtoEntity(stockRequestDto))), HttpStatus.OK);
    }

    @GetMapping("/stocks/{id}")
    @ApiOperation("Gets only one stock data by id")
    public ResponseEntity<StockResponseDto> findById(@PathVariable int id) {

        StockEntity stock = stockService.findById(id).orElseThrow(() -> new StockNotFoundException("Stock not found."));
        return new ResponseEntity<>(StockMapper.mapEntityToResponseDto(stock), HttpStatus.OK);
    }

    @PatchMapping("/stocks/{id}")
    @ApiOperation("Updates the price of a single stock")
    public ResponseEntity<StockResponseDto> updateStock(@PathVariable int id, @RequestBody StockRequestDto stockRequestDto) {

        StockEntity stockEntity = stockService.findById(id).orElseThrow(() -> new StockNotFoundException("Stock not found."));
        stockEntity.setCurrentPrice(stockRequestDto.getCurrentPrice());
        stockEntity.setLastUpdate(new Date(System.currentTimeMillis()));
        StockResponseDto result = StockMapper.mapEntityToResponseDto(stockService.updateStock(stockEntity));
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/stocks/{id}")
    @ApiOperation("Deletes the stock record by id")
    public ResponseEntity<StockResponseDto> deleteStock(@PathVariable int id) {

        StockEntity stockEntity = stockService.findById(id).orElseThrow(() -> new StockNotFoundException("Stock not found."));
        stockService.deleteStock(id);
        return new ResponseEntity<>(StockMapper.mapEntityToResponseDto(stockEntity), HttpStatus.NO_CONTENT);

    }
}
