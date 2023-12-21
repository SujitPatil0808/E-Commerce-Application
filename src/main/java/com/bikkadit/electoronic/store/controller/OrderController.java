package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.payload.ApiResponse;
import com.bikkadit.electoronic.store.payload.CreateOrderRequest;
import com.bikkadit.electoronic.store.payload.OrderDto;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.repository.OrderRepository;
import com.bikkadit.electoronic.store.service.OrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderServiceI orderServiceI;


    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request) {
        OrderDto order = this.orderServiceI.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/orderId/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(AppConstants.DELETE + orderId);
        apiResponse.setStatus(true);
        apiResponse.setStatusCode(HttpStatus.OK);

        this.orderServiceI.removeOrder(orderId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUsers(@PathVariable String userId) {
        List<OrderDto> allOrdersOfUser = this.orderServiceI.getAllOrdersOfUser(userId);
        return new ResponseEntity<>(allOrdersOfUser, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrders(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam (value = "sortBy",defaultValue = AppConstants.ORDER_SORT_BY,required = false)String sortBy,
            @RequestParam(value = "direction",defaultValue = AppConstants.SORT_DIR,required = false) String direction

    ){
        PageableResponse<OrderDto> allOrder = this.orderServiceI.getAllOrder(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(allOrder,HttpStatus.OK);
    }


}
