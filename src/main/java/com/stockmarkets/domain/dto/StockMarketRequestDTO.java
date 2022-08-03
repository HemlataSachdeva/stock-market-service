
package com.stockmarkets.domain.dto;

import java.math.BigDecimal;

import lombok.*;

import javax.validation.constraints.NotNull;


@Data
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class StockMarketRequestDTO implements DomainDTO {

    @NotNull
    private String stockSymbol;
    private Double stockPrice;

}

