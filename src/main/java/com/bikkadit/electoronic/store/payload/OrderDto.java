package com.bikkadit.electoronic.store.payload;

import com.bikkadit.electoronic.store.model.OrderItem;
import com.bikkadit.electoronic.store.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private String orderId;

    private String orderStatus="PENDING";

    private String paymentStatus="NOT_PAID";

    private Integer totalAmount;

    private String billingAddress;

    private String billingPhoneNo;

    private String billingName;

    private Date orderDate=new Date();

    private Date deliveredDate;
//    private UserDto user;
    private List<OrderItemDto> orderItems=new ArrayList<>();

}
