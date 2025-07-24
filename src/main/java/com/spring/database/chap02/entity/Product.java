package com.spring.database.chap02.entity;

import lombok.*;

import java.time.LocalDateTime;

/*
CREATE TABLE products (
      id BIGINT AUTO_INCREMENT,
      name VARCHAR(100) NOT NULL COMMENT '상품명',
      price INT NOT NULL COMMENT '가격',
      stock_quantity INT NOT NULL COMMENT '재고수량',
      description TEXT COMMENT '상품설명',
      seller VARCHAR(50) NOT NULL COMMENT '판매자',
      status VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '상품상태',
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='상품';
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String description;
    private String seller;

    // ACTIVE : 삭제되지 않은 상품, DELETED : 제거된 상품 (논리적 삭제)
    private String status;
    private LocalDateTime createdAt;
}
