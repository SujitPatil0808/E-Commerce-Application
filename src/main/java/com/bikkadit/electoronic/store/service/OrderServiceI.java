package com.bikkadit.electoronic.store.service;

import com.bikkadit.electoronic.store.model.Order;
import com.bikkadit.electoronic.store.payload.CreateOrderRequest;
import com.bikkadit.electoronic.store.payload.OrderDto;
import com.bikkadit.electoronic.store.payload.PageableResponse;

import java.util.List;

public interface OrderServiceI {


    // create Order
    OrderDto createOrder(CreateOrderRequest orderDto);


    // remove Order
    void removeOrder(String orderId);

    // get Orders Of User
    List<OrderDto> getAllOrdersOfUser(String userId);

    //Get All Order(Admin)
    PageableResponse<OrderDto> getAllOrder(Integer pageNumber, Integer pageSize, String sortBy, String direction);

}
