package com.stockmarkets.processor;

import com.stockmarkets.domain.dto.TradeDTO;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class TradeDataBaseRepository {

	private ConcurrentHashMap<String, List<TradeDTO>> tradeMap;

	private void initialize() {
		if (tradeMap == null) {
			tradeMap = new ConcurrentHashMap<>();
		}
	}

	public ConcurrentHashMap<String, List<TradeDTO>> getStockMarketDB() {
		if (tradeMap == null) {
			initialize();
		}
		return tradeMap;
	}
}
