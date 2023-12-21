package com.bikkadit.electoronic.store.repository;

import com.bikkadit.electoronic.store.model.Order;
import com.bikkadit.electoronic.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {

    List<Order> findByUser(User user);
}
