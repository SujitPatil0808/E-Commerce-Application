package com.bikkadit.electoronic.store.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "orders_items")
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;

    private Integer quantity;

    private Integer totalPrice;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    private Order order;



}
