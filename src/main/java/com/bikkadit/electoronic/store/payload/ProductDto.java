package com.bikkadit.electoronic.store.payload;

import javax.persistence.Column;
import java.util.Date;

public class ProductDto {


    private String productId;

    private String title;

    private String description;

    private Double price;

    private Double discountedPrice;

    private Integer quantity;

    private Date addedDate;

    private Boolean live;

    private Boolean stock;
}
