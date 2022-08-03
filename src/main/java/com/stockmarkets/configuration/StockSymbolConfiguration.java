package com.stockmarkets.configuration;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
public class StockSymbolConfiguration {
    private StockType  type;
    private Double lastdividend;
    private Double     fixeddividend;
    private Double parvalue;
}
