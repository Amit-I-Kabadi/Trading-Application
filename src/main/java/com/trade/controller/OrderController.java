package com.trade.controller;

import com.trade.domain.OrderType;
import com.trade.model.Coin;
import com.trade.model.Orders;
import com.trade.model.User;
import com.trade.request.CreateOrderRequest;
import com.trade.service.CoinService;
import com.trade.service.OrderService;
import com.trade.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

//    @Autowired
//    private WalletTr

    @PostMapping("/pay")
    public ResponseEntity<Orders> payOrderPayment(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest req) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Coin coin=coinService.findById(req.getCoinId());
        Orders order=orderService.processOrder(coin,req.getQuantity(),req.getOrderType(),user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@RequestHeader("Authorization") String jwtToken,@PathVariable Long orderId) throws Exception{

        User user=userService.findUserProfileByJwt(jwtToken);

        Orders order=orderService.getOrderById(orderId);
        if (order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
        }
        else{
            throw new Exception("You Don't have Access");
        }
    }

    @GetMapping()
    public ResponseEntity<List<Orders>> getAllOrdersForUser(@RequestHeader("Authorization") String jwtToken, @RequestParam(required = false) OrderType order_type, @RequestParam(required = false) String asset_symbol) throws Exception{

        Long userId= userService.findUserProfileByJwt(jwtToken).getId();
        List<Orders> userOrders=orderService.getAllOrderOfUser(userId,order_type,asset_symbol);

        return ResponseEntity.ok(userOrders);
    }


}
