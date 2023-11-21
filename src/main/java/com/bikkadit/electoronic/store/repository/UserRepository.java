package com.bikkadit.electoronic.store.repository;

import com.bikkadit.electoronic.store.model.User;
import com.bikkadit.electoronic.store.payload.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {


    Optional<User> findByEmail(String email);

    List<User> findByNameContaining(String keyword);

    Optional<User> findByEmailAndPassword(String email,String password);

}
