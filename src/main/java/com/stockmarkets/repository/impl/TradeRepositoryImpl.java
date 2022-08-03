package com.stockmarkets.repository.impl;

import com.stockmarkets.domain.dto.TradeDTO;
import com.stockmarkets.processor.TradeDataBaseRepository;
import com.stockmarkets.repository.TradeRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeRepositoryImpl extends CommonRepositoryImpl<String, List<TradeDTO>> implements TradeRepository {
	
	@Autowired
	private TradeDataBaseRepository tradeDataBaseRepository;

	@Override
	protected ConcurrentHashMap<String, List<TradeDTO>> getMap() {
		return tradeDataBaseRepository.getStockMarketDB();
	}

	@Override
	public TradeDTO create(String key, TradeDTO value) {
		List<TradeDTO> trades = load(key);
		if(trades == null){
			trades = new ArrayList<TradeDTO>();
		}
		trades.add(value);
		create(key, trades);
		return value;
	}	
	
    @Override
    public List<TradeDTO> tradesInlastMinutes(String key, int minutes) {

        Date date = new Date();
        long time = date.getTime() - (minutes*60*1000);
        List<TradeDTO> listTrades = load(key);
        List<TradeDTO> tradesInLastMinutes = null;
        
        if(listTrades!=null)
        	tradesInLastMinutes = listTrades
            .stream()
            .filter(entity -> entity.getTimestamp().getTime() >= time)
            .collect(Collectors.toList());
        
        return tradesInLastMinutes;
    }

	@Override
	public List<TradeDTO> getAllTrades() {
		List<TradeDTO> trades = list().stream()
			        .flatMap(List::stream)
			        .collect(Collectors.toList());
		return trades;
	}
    
}
