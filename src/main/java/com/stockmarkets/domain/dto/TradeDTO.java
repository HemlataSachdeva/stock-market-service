package com.stockmarkets.domain.dto;

import java.util.Date;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TradeDTO {

    @NotNull(message = "Symbol can not be null")
    @Size(min = 1, max = 3, message = "Symbol must be between 1 and 3 characters long")
    private  String    symbol;
    private  TradeType type;
    private  Double    tradedPrice;
    private  Long      sharesQuantity;
    private  Date      timestamp;
}
