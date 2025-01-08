package com.trade.service;

import com.trade.model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(Integer page) throws Exception;

    String getMarketChart(String coinId,int days) throws Exception;

    //coinGecko Api
    String getCoinDetails(String coinId) throws Exception;

    //from our database
    Coin findById(String coinId) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50CoinsByMarketCapRank() throws Exception;

    String getTradingCoins() throws Exception;

}
