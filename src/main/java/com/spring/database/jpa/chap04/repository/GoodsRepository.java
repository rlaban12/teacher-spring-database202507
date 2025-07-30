package com.spring.database.jpa.chap04.repository;

import com.spring.database.jpa.chap04.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
}
