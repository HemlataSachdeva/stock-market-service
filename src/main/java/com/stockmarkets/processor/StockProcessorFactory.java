package com.stockmarkets.processor;

import com.stockmarkets.domain.dto.StockMarketResponseDTO;
import com.stockmarkets.errorexception.CustomeException;

public interface StockProcessorFactory {

    StockMarketResponseDTO getDividendYield(String stockSymbol, Double stockPrice);

    StockMarketResponseDTO getPERatio(String symbol, Double stockPrice);

    StockMarketResponseDTO getVolWeightPrice(String symbol);

    StockMarketResponseDTO getGeomatricmean() throws CustomeException;


}