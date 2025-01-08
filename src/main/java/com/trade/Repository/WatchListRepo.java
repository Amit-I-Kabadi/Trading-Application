package com.trade.Repository;

import com.trade.model.User;
import com.trade.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepo extends JpaRepository<WatchList,Long> {
    WatchList findByUserId(Long userId);
}
