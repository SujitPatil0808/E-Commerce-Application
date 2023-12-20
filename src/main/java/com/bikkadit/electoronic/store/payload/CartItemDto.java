package com.bikkadit.electoronic.store.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private Integer cartItemId;

    private Integer quantity;

    private Integer totalPrice;

    private ProductDto product;
}
