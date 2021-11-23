package com.payconiq.stockapp.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@Data
public class StockResponseDto implements Serializable {

    private static final long serialVersionUID = 8317676219297719709L;
    private int id;
    private String name;
    private double currentPrice;
    private Date lastUpdate;


}
