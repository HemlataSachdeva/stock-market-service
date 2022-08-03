package com.stockmarkets.http.handler;

import com.stockmarkets.domain.dto.StockMarketRequestDTO;
import com.stockmarkets.domain.dto.StockMarketResponseDTO;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockHandlerTest {
    @LocalServerPort
    int randomServerPort;
    StockMarketRequestDTO stockMarketRequestDTO = new StockMarketRequestDTO();
    String baseUrl;

    @BeforeEach
    void setup(){
        baseUrl =  "http://localhost:"+randomServerPort+"/stocks/";
    }
    @Test
    public void shouldReturnDividendYield() throws URISyntaxException {
        stockMarketRequestDTO.setStockPrice(100d);
        stockMarketRequestDTO.setStockSymbol("POP");
        RestTemplate restTemplate = new RestTemplate();
        baseUrl += "dividendyield?stockPrice=10";
        URI uri = new URI(baseUrl);

        ResponseEntity<StockMarketResponseDTO> result = restTemplate.postForEntity(uri, stockMarketRequestDTO, StockMarketResponseDTO.class);

        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertEquals(new Double(0.08), result.getBody().getDividendYield());
    }
}
