package com.stockmarkets.processor;

import com.stockmarkets.domain.dto.TradeDTO;
import com.stockmarkets.repository.TradeRepository;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class TradeProcessorImpl implements TradeProcessor {
	@Autowired
	private TradeRepository tradeRepository;

	@Override
	public void addTrade(TradeDTO tradeRequest) {
		log.info("Adding trade {} ", tradeRequest);
		TradeDTO trade = TradeDTO.builder().symbol(tradeRequest.getSymbol()).type(tradeRequest.getType())
				.tradedPrice(tradeRequest.getTradedPrice()).sharesQuantity(tradeRequest.getSharesQuantity()).timestamp(new Date()).build();
		
		tradeRepository.create(tradeRequest.getSymbol(), trade);
		log.info("Added trade Successfully");
		
	}
	@Override
	public List<TradeDTO> getTradesInLastMinutes(String symbol, int minutes) {
		log.info("Last Trades for symbol {} in last minutes {} ", symbol, minutes);
		List<TradeDTO> trades = tradeRepository.tradesInlastMinutes(symbol, minutes);
		
		log.info("Last Trades [{}] for symbol {} in last minutes {} ", trades, symbol, minutes);
		return trades;
	}

	@Override
	public List<TradeDTO> getAllTrades() {
		log.debug("All trades");
		List<TradeDTO> trades = tradeRepository.getAllTrades();
		
		log.debug("All Trades [{}]", trades);
		return trades;
	}
}
