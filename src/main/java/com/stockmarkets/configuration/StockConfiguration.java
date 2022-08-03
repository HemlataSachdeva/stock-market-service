package com.stockmarkets.configuration;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "service")
public class StockConfiguration {
    private Map<String, List<StockSymbolConfiguration>> stockconfigs;
    private Integer minutes;
}
