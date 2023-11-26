package com.bikkadit.electoronic.store.repository;

import com.bikkadit.electoronic.store.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}
