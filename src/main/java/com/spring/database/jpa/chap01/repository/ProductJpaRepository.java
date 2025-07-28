package com.spring.database.jpa.chap01.repository;

import com.spring.database.jpa.chap01.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

}
