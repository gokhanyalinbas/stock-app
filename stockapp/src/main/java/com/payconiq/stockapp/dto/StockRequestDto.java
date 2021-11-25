package com.payconiq.stockapp.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@Data
public class StockRequestDto implements Serializable {

    private static final long serialVersionUID = 8317676219297719109L;
    @DecimalMin(value = "1")
    private int id;
    @NotNull(message = "Stock name can not be null value.")
    @NotBlank(message = "Stock name is mandatory.")
    private String name;
    @DecimalMin(value = "0.0", message = "Stock price must be higher than 0")
    private double currentPrice;
    private Date lastUpdate;


}
