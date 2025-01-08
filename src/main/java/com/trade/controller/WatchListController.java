package com.trade.controller;

import com.trade.model.Coin;
import com.trade.model.User;
import com.trade.model.WatchList;
import com.trade.service.CoinService;
import com.trade.service.UserService;
import com.trade.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        WatchList watchList=watchListService.findUserWatchList(user.getId());
        return ResponseEntity.ok(watchList);
    }

//    @PostMapping ("/create")
//    public ResponseEntity<WatchList> createUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
//        User user=userService.findUserProfileByJwt(jwt);
//        WatchList createWatchList=watchListService.findUserWatchList(user.getId());
//        return ResponseEntity.status(HttpStatus.CREATED).body(createWatchList);
//    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchlistId) throws Exception {
        WatchList watchList=watchListService.findById(watchlistId);
        return ResponseEntity.ok(watchList);
    }

    @GetMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(@PathVariable String coinId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Coin coin=coinService.findById(coinId);
        Coin addedCoin=watchListService.addItemToWatchList(coin,user);
        return ResponseEntity.ok(addedCoin);
    }

}
