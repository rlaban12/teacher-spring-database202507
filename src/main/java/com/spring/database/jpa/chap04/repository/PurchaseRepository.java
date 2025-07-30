package com.spring.database.jpa.chap04.repository;

import com.spring.database.jpa.chap04.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
