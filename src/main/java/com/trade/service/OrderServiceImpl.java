package com.trade.service;

import com.trade.Repository.OrderItemRepository;
import com.trade.Repository.OrderRepo;
import com.trade.domain.OrderStatus;
import com.trade.domain.OrderType;
import com.trade.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AssetService assetService;


    @Override
    public Orders createOrder(User user, OrderItem orderItem, OrderType orderType) {
        //BigDecimal price= BigDecimal.valueOf(orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity());
                //.multiply(BigDecimal.valueOf(orderItem.getQuantity()));

        BigDecimal price = orderItem.getCoin().getCurrentPrice()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity()));

        Orders order=new Orders();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(price);
        order.setTimeStamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepo.save(order);
    }

    @Override
    public Orders getOrderById(Long orderId) throws Exception {
        return orderRepo.findById(orderId).orElseThrow(()->new Exception("order Not Found"));
    }

    @Override
    public List<Orders> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {

        return orderRepo.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin, double quantity, BigDecimal buyPrice, BigDecimal sellPrice){
        OrderItem orderItem=new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Orders buyAsset(Coin coin,double quantity,User user) throws Exception {
        if (quantity>0){
            throw new Exception("Quantity Should be greater than 0");
        }

       // BigDecimal buyPrice= BigDecimal.valueOf(coin.getCurrentPrice());

        BigDecimal buyPrice = coin.getCurrentPrice();


        OrderItem orderItem=createOrderItem(coin,quantity,buyPrice, BigDecimal.valueOf(0));

        Orders order=createOrder(user,orderItem,OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order,user);

        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Orders savedOrder=orderRepo.save(order);

        //create Asset
        Asset oldAsset=assetService.findAssetUserIdAndCoinId(order.getUser().getId(),order.getOrderItem().getCoin().getId());
        if (oldAsset==null){
            assetService.createAsset(user,orderItem.getCoin(),orderItem.getQuantity());
        }
        else{
            assetService.updateAsset(oldAsset.getId(),quantity);
        }

        return savedOrder;

    }

    @Transactional
    public Orders sellAsset(Coin coin,double quantity,User user) throws Exception {
        if (quantity>0){
            throw new Exception("Quantity Should be greater than 0");
        }

        //BigDecimal sellPrice= BigDecimal.valueOf(coin.getCurrentPrice());
        BigDecimal sellPrice = coin.getCurrentPrice();

        Asset assetToSell=assetService.findAssetUserIdAndCoinId(user.getId(), coin.getId());
       // BigDecimal buyPrice = BigDecimal.valueOf(assetToSell.getBuyPrice());
        BigDecimal buyPrice = assetToSell.getBuyPrice();


        if (assetToSell!=null) {
            OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);



            Orders order = createOrder(user, orderItem, OrderType.SELL);


            orderItem.setOrder(order);

            if (assetToSell.getQuantity() > quantity) {
                order.setStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);
                Orders savedOrder = orderRepo.save(order);
                walletService.payOrderPayment(order, user);
                Asset updatedAsset=assetService.updateAsset(assetToSell.getId(),-quantity);

//                if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
//                    assetService.deleteAsset(updatedAsset.getId());
//                }
                if (BigDecimal.valueOf(updatedAsset.getQuantity())
                        .multiply(coin.getCurrentPrice())
                        .compareTo(BigDecimal.ONE) <= 0) {
                    assetService.deleteAsset(updatedAsset.getId());
                }
                return savedOrder;
            }
            throw new Exception("Insufficient Quntity to sell");
        }
        throw new Exception("Asset Not Found");




        //create Asset
//        return savedOrder;

    }

    @Override
    @Transactional
    public Orders processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {

        if (orderType.equals(OrderType.BUY)){
            return buyAsset(coin,quantity,user);
        }
        else if(orderType.equals(OrderType.SELL)){
            return sellAsset(coin,quantity,user);
        }
        throw new Exception("Invalid order type");

    }
}
