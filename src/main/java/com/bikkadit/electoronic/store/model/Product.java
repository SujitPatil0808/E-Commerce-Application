package com.bikkadit.electoronic.store.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Product {

    @Id
    private String productId;

    @Column(name = "product_title")
    private String title;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_price")
    private Integer price;

    @Column(name = "product_discountedPrice")
    private Integer discountedPrice;

    @Column(name = "product_quantity")
    private Integer quantity;

    @Column(name = "product_addedDate")
    private Date addedDate;

    @Column(name = "product_live")
    private Boolean live;

    @Column(name = "product_stock")
    private Boolean stock;

    @Column(name = "product_image")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category categories;



}
