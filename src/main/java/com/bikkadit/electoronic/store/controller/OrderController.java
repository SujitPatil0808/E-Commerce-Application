package com.bikkadit.electoronic.store.controller;

import com.bikkadit.electoronic.store.constanst.AppConstants;
import com.bikkadit.electoronic.store.payload.ApiResponse;
import com.bikkadit.electoronic.store.payload.CreateOrderRequest;
import com.bikkadit.electoronic.store.payload.OrderDto;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import com.bikkadit.electoronic.store.repository.OrderRepository;
import com.bikkadit.electoronic.store.service.OrderServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderServiceI orderServiceI;


    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request) {
        log.info("Enter the  request for Create Order ");
        OrderDto order = this.orderServiceI.createOrder(request);
        log.info("Completed the  request for Create Order ");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/orderId/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        log.info("Enter the  request for Remove  Order With OrderId :{} ",orderId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(AppConstants.DELETE + orderId);
        apiResponse.setStatus(true);
        apiResponse.setStatusCode(HttpStatus.OK);

        this.orderServiceI.removeOrder(orderId);
        log.info("Completed the  request for Remove  Order With OrderId :{} ",orderId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUsers(@PathVariable String userId) {
        log.info("Enter the  request for Get Order With UserId :{}",userId);
        List<OrderDto> allOrdersOfUser = this.orderServiceI.getAllOrdersOfUser(userId);
        log.info("Completed the  request for Get Order With UserId :{}",userId);
        return new ResponseEntity<>(allOrdersOfUser, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrders(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam (value = "sortBy",defaultValue = AppConstants.ORDER_SORT_BY,required = false)String sortBy,
            @RequestParam(value = "direction",defaultValue = AppConstants.SORT_DIR,required = false) String direction

    ){
        log.info("Enter the  request for Get All Order :");
        PageableResponse<OrderDto> allOrder = this.orderServiceI.getAllOrder(pageNumber, pageSize, sortBy, direction);
        log.info("Completed the  request for Get All Order :");
        return new ResponseEntity<>(allOrder,HttpStatus.OK);
    }


}
