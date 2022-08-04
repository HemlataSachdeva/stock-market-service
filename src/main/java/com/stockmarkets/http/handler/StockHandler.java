package com.stockmarkets.http.handler;

import com.stockmarkets.domain.dto.StockMarketRequestDTO;
import com.stockmarkets.domain.dto.StockMarketResponseDTO;
import com.stockmarkets.domain.dto.TradeDTO;
import com.stockmarkets.domain.validation.BeanValidation;
import com.stockmarkets.errorexception.ClientException;
import com.stockmarkets.errorexception.CustomeException;
import com.stockmarkets.processor.StockProcessor;
import com.stockmarkets.processor.TradeProcessor;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockHandler {

    @Autowired StockProcessor stockProcessor;
   @Autowired  BeanValidation beanValidation;
    @Autowired TradeProcessor tradeProcessor;

    //dividendyield using path param and request parameter
    @RequestMapping(value = "/{symbol}/dividendyield", method = RequestMethod.GET)
    public StockMarketResponseDTO getDividentYield(@RequestParam(required = true) Double stockPrice,
        @PathVariable(required = true, name = "symbol") String symbol) {
        return stockProcessor.getDividendYield(symbol, stockPrice);

    }

    //dividendyield using dto as request body
    @RequestMapping(
        value = "/dividendyield", method = RequestMethod.POST,
        produces = "application/json", consumes = "application/json"
    )
    public @ResponseBody ResponseEntity<StockMarketResponseDTO> getDividendYield1(
        @Valid @RequestBody final StockMarketRequestDTO stockMarketRequestDTO) {

        return Optional
            .ofNullable(stockMarketRequestDTO)
                .map(request -> beanValidation.dtoValidation(request))
            .map(requestDTO -> stockProcessor.getDividendYield(requestDTO.getStockSymbol(),
                requestDTO.getStockPrice())
            )
            .map(response -> ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response))
            .orElseThrow(() -> ClientException.builder
                .get()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withHttpRecord("Something wrong with application")
                .build());

    }

    @RequestMapping(value = "/{symbol}/peratio", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<StockMarketResponseDTO> getPERatio(@RequestParam(required = true) Double stockPrice,
        @PathVariable("symbol") String symbol) {
        StockMarketResponseDTO stockMarketResponseDTO = stockProcessor.getPERatio(symbol,
            stockPrice);
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(stockMarketResponseDTO);
    }

    @PostMapping("/trade")
    public @ResponseBody ResponseEntity<String> addTrade(@Valid @RequestBody TradeDTO tradeRequest) {
        tradeProcessor.addTrade(tradeRequest);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("success");
    }

    @RequestMapping(value = "/{symbol}/volWeightPrice", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<StockMarketResponseDTO> getvolWeightPrice(@RequestParam(required = true) BigDecimal stockPrice,
        @PathVariable("symbol") String symbol) {

        StockMarketResponseDTO stockMarketResponseDTO = stockProcessor.getVolWeightPrice(symbol);
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(stockMarketResponseDTO);
    }

    @RequestMapping(value = "/gbce", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<StockMarketResponseDTO> getGeomatricMean() {

        StockMarketResponseDTO stockMarketResponseDTO = null;
        try {
            stockMarketResponseDTO = stockProcessor.getGeomatricmean();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(stockMarketResponseDTO);
        } catch (CustomeException e) {
            log.error("somethng went wrong with application {} "+e);
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(stockMarketResponseDTO);
        }
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

/* Reactive Programming

   @RequestMapping(
        value = "/dividendyield", method = RequestMethod.POST,
        produces = "application/json", consumes = "application/json"
    )
    public @ResponseBody Mono<ServerResponse> getDividend(@RequestBody final ServerRequest serverRequest) {
       return  serverRequest.bodyToMono(StockMarketRequestDTO.class)
               .map(stockMarketRequestDTO -> beanValidation.dtoValidation(stockMarketRequestDTO))
           .map(dto -> stockProcessor.getDividendYield(dto.getStockSymbol(), dto.getStockPrice()))
            .flatMap((StockMarketResponseDTO responseDTO) ->
                ok()
                    .contentType(MediaType.APPLICATION_STREAM_JSON)
                    .body(Mono.just(responseDTO), StockMarketResponseDTO.class))
                   .onErrorResume(error -> Mono.error(error));
}*/



