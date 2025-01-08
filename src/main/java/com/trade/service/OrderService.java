package com.trade.service;

import com.trade.domain.OrderType;
import com.trade.model.Coin;
import com.trade.model.OrderItem;
import com.trade.model.Orders;
import com.trade.model.User;

import java.util.List;

public interface OrderService {
    Orders createOrder(User user, OrderItem orderItem, OrderType orderType);
    Orders getOrderById(Long orderId) throws Exception;
    List<Orders> getAllOrderOfUser(Long userId,OrderType orderType,String assetSymbol);
    Orders processOrder(Coin coin,double quantity,OrderType orderType,User user) throws Exception;
}
