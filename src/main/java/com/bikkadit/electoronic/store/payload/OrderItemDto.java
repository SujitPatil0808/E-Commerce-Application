package com.bikkadit.electoronic.store.payload;

import com.bikkadit.electoronic.store.model.Order;
import com.bikkadit.electoronic.store.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Integer orderItemId;

    private Integer quantity;

    private Integer totalPrice;

    private ProductDto product;

}
