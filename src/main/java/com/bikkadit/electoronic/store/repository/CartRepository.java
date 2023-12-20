package com.bikkadit.electoronic.store.repository;

import com.bikkadit.electoronic.store.model.Cart;
import com.bikkadit.electoronic.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);
}
