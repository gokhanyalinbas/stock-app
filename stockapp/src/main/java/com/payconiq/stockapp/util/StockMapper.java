package com.payconiq.stockapp.util;

import com.payconiq.stockapp.dto.StockRequestDto;
import com.payconiq.stockapp.dto.StockResponseDto;
import com.payconiq.stockapp.entity.StockEntity;

import java.util.Date;

public class StockMapper {

    public static StockEntity mapRequestDTOtoEntity(StockRequestDto stockRequestDto) {
        return StockEntity.builder()
                .currentPrice(stockRequestDto.getCurrentPrice())
                .id(stockRequestDto.getId())
                .lastUpdate(new Date(System.currentTimeMillis()))
                .name(stockRequestDto.getName())
                .build();
    }

    public static StockEntity mapResponseDTOtoEntity(StockResponseDto stockResponseDto) {

        return StockEntity.builder()
                .currentPrice(stockResponseDto.getCurrentPrice())
                .id(stockResponseDto.getId())
                .lastUpdate(stockResponseDto.getLastUpdate())
                .name(stockResponseDto.getName())
                .build();
    }

    public static StockResponseDto mapEntityToResponseDto(StockEntity stockEntity) {

        return StockResponseDto.builder()
                .currentPrice(stockEntity.getCurrentPrice())
                .id(stockEntity.getId())
                .lastUpdate(stockEntity.getLastUpdate())
                .name(stockEntity.getName())
                .build();
    }

    public static StockRequestDto mapEntityToRequestDto(StockEntity stockEntity) {

        return StockRequestDto.builder()
                .currentPrice(stockEntity.getCurrentPrice())
                .id(stockEntity.getId())
                .lastUpdate(stockEntity.getLastUpdate())
                .name(stockEntity.getName())
                .build();
    }

    public static StockRequestDto mapResponseToRequestDto(StockResponseDto stockResponseDto) {

        return StockRequestDto.builder()
                .currentPrice(stockResponseDto.getCurrentPrice())
                .id(stockResponseDto.getId())
                .lastUpdate(stockResponseDto.getLastUpdate())
                .name(stockResponseDto.getName())
                .build();
    }
}
