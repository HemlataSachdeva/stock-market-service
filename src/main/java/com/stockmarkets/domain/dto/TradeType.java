package com.stockmarkets.domain.dto;

import lombok.Getter;

public enum TradeType {
        BUY("Buy"),
        SELL("Sell");
    @Getter
    private final String value;

    TradeType(final String value) {
        this.value = value;
    }
}
