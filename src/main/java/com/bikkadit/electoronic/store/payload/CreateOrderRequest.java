package com.bikkadit.electoronic.store.payload;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {


    private String cartId;

    private String userId;

    private String orderStatus = "PENDING";

    private String paymentStatus = "NOT_PAID";

    private String billingAddress;

    private String billingPhoneNo;

    private String billingName;


}
