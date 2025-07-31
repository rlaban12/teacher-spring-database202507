package com.spring.database.querydsl.repository;

import com.spring.database.querydsl.entity.Idol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IdolRepository extends JpaRepository<Idol, Long> {

}
