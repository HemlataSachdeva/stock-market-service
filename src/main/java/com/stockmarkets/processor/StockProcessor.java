package com.stockmarkets.processor;

import com.stockmarkets.configuration.StockConfiguration;
import com.stockmarkets.configuration.StockSymbolConfiguration;
import com.stockmarkets.configuration.StockType;
import com.stockmarkets.domain.dto.StockMarketResponseDTO;
import com.stockmarkets.domain.dto.TradeDTO;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.stockmarkets.errorexception.CustomeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockProcessor implements StockProcessorFactory {

    private final TradeDataBaseRepository tradeDataBaseRepository;

    protected ConcurrentHashMap<String, List<TradeDTO>> getMap() {
        return tradeDataBaseRepository.getStockMarketDB();
    }

    @Autowired
    private final StockConfiguration stockConfiguration;
    @Autowired
    private final TradeProcessor tradeProcessor;

    @Override
    public StockMarketResponseDTO getDividendYield(String stockSymbol, Double stockPrice) {

        List<StockSymbolConfiguration> stockSymbolConfigurations = stockConfiguration
                .getStockconfigs()
                .get(stockSymbol);

        return stockSymbolConfigurations
                .stream()
                .map(stockSymbolConfiguration ->
                {
                    return Optional
                            .ofNullable(stockSymbolConfiguration)
                            .filter(stockSymbolConfiguration1 -> StockType.COMMON
                                    .getValue()
                                    .equalsIgnoreCase(stockSymbolConfiguration1
                                            .getType()
                                            .getValue()))
                            .map(stockConfigs -> stockConfigs
                                    .getLastdividend() / stockPrice)
                            .orElseGet(() -> (stockSymbolConfiguration
                                    .getFixeddividend() * stockSymbolConfiguration.getParvalue()) / stockPrice);
                })
                .findFirst()
                .map(value -> StockMarketResponseDTO
                        .builder()
                        .dividendYield(value)
                        .stockPrice(stockPrice)
                        .symbol(stockSymbol)
                        .build())
                .orElseThrow(null);

    }

    @Override
    public StockMarketResponseDTO getPERatio(String stockSymbol, Double stockPrice) {

        Double dividendYield = getDividendYield(stockSymbol, stockPrice).getDividendYield();
        Double peRatio = stockPrice / dividendYield;

        return StockMarketResponseDTO
                .builder()
                .peRatio(peRatio)
                .stockPrice(stockPrice)
                .symbol(stockSymbol)
                .build();
    }

    @Override
    public StockMarketResponseDTO getVolWeightPrice(String symbol) {

        List<TradeDTO> trades = tradeProcessor.getTradesInLastMinutes(symbol, stockConfiguration.getMinutes());
        Double volWeightedPrice = 0d;
        Long quantity = 0l;

        if (trades != null) {
            for (TradeDTO trade : trades) {
                quantity += trade.getSharesQuantity();
                volWeightedPrice += trade.getSharesQuantity() * trade.getTradedPrice();
            }
            volWeightedPrice /= quantity;
        }

        return StockMarketResponseDTO
                .builder()
                .volWeightPrice(volWeightedPrice)
                .symbol(symbol)
                .build();

    }

    @Override
    public StockMarketResponseDTO getGeomatricmean() throws CustomeException {
        List<TradeDTO> tradeDTOS = tradeProcessor.getAllTrades();
        Double gbce = 0d;
        Double priceProduct = 1d;

        priceProduct = Optional
                .ofNullable(tradeDTOS)
                .map(tradeDTOS1 -> tradeDTOS1
                        .stream()
                        .map(TradeDTO::getTradedPrice)
                        .reduce(1d, (a, b) -> a * b))
                .orElse(null);
        Double n = (double) tradeDTOS.size();

        gbce = Math.pow(priceProduct, 1d / n);

        return StockMarketResponseDTO.builder()
                .gbce(gbce).build();
    }

}