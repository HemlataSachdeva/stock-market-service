package com.stockmarkets.repository;

import com.stockmarkets.domain.dto.TradeDTO;
import java.util.List;

public interface TradeRepository extends CommonRepository<String, List<TradeDTO>> {
	TradeDTO create(String key, TradeDTO value);

	List<TradeDTO> tradesInlastMinutes(String key, int minutes);
	
	List<TradeDTO> getAllTrades();
	
}
