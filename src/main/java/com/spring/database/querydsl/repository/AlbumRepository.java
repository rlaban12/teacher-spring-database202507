package com.spring.database.querydsl.repository;

import com.spring.database.querydsl.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
