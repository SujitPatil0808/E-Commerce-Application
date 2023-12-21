package com.bikkadit.electoronic.store.repository;

import com.bikkadit.electoronic.store.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
