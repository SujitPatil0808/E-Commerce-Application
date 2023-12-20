package com.bikkadit.electoronic.store.payload;

import com.bikkadit.electoronic.store.model.CartItem;
import com.bikkadit.electoronic.store.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDto {

    private String cartId;

    private Date createdAt;

    @OneToOne
    private UserDto user;


    private List<CartItem> items=new ArrayList<>();
}
