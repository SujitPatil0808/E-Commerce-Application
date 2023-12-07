package com.bikkadit.electoronic.store.repository;

import com.bikkadit.electoronic.store.model.Product;
import com.bikkadit.electoronic.store.payload.PageableResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {

    //findByLiveTrue()

    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByTitleContaining(Pageable pageable,String keyword);
}
