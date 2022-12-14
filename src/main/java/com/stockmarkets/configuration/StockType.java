package com.stockmarkets.configuration;

public enum StockType {

    COMMON("common"),
    PREFERED("prefered");

    private final String value;

    StockType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
