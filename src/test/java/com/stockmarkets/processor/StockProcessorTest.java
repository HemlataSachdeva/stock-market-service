package com.stockmarkets.processor;

import static org.mockito.Mockito.doReturn;

import com.stockmarkets.configuration.StockConfiguration;
import com.stockmarkets.configuration.StockSymbolConfiguration;
import com.stockmarkets.configuration.StockType;
import com.stockmarkets.domain.dto.StockMarketResponseDTO;
import com.stockmarkets.domain.dto.TradeDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class StockProcessorTest {

    @Mock
    private  TradeDataBaseRepository tradeDataBaseRepository;
    @Mock
    private  StockConfiguration stockConfiguration;
    @Mock
    private  TradeProcessor     tradeProcessor;
    @InjectMocks
    private StockProcessor stockProcessor;

    protected ConcurrentHashMap<String, List<TradeDTO>> map = new ConcurrentHashMap<>();
    Map<String, List<StockSymbolConfiguration>> stockconfigs = new HashMap<>();
    List<StockSymbolConfiguration> stockSymbolConfigurationList = new ArrayList<>();
    private StockSymbolConfiguration stockSymbolConfiguration = new StockSymbolConfiguration();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        stockSymbolConfiguration.setFixeddividend(new Double(10));
        stockSymbolConfiguration.setLastdividend(new Double(10));
        stockSymbolConfiguration.setType(StockType.COMMON);
        stockSymbolConfiguration.setParvalue(new Double(100));
        stockSymbolConfigurationList.add(stockSymbolConfiguration);
        stockconfigs.put("TEA",stockSymbolConfigurationList);
        stockProcessor = Mockito.spy(new StockProcessor(tradeDataBaseRepository, stockConfiguration,tradeProcessor));
        doReturn(stockconfigs)
            .when(stockConfiguration)
            .getStockconfigs();

    }

    @Test
    void getDividendYield() {
        StockMarketResponseDTO stockMarketResponseDTO = stockProcessor.getDividendYield("TEA", 10d);

        Assertions.assertNotNull(stockMarketResponseDTO);
        Assertions.assertEquals(1d, stockMarketResponseDTO.getDividendYield());
    }

    @Test
    void getPERatio() {
    }

    @Test
    void getVolWeightPrice() {
    }
}