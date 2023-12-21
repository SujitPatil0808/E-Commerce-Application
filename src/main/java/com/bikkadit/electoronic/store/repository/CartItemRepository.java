package com.bikkadit.electoronic.store.repository;

import com.bikkadit.electoronic.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
}
