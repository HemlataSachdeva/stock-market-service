package com.stockmarkets.processor;

import com.stockmarkets.domain.dto.TradeDTO;
import java.util.List;

public interface TradeProcessor {

	void addTrade(TradeDTO tradeRequest);
	
	List<TradeDTO> getTradesInLastMinutes(String symbol, int minutes);

	List<TradeDTO> getAllTrades();

}
