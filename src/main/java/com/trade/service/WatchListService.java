package com.trade.service;

import com.trade.model.Coin;
import com.trade.model.User;
import com.trade.model.WatchList;

public interface WatchListService {

    WatchList findUserWatchList(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long id) throws Exception;
    Coin addItemToWatchList(Coin coin,User user) throws Exception;

}
