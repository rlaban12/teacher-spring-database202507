package com.spring.database.chap02.repository;

import com.spring.database.chap02.dto.PriceInfo;
import com.spring.database.chap02.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final JdbcTemplate template;

    // 상품의 기본적인 CRUD를 5개
    // 생성, 수정, 삭제(논리삭제), 전체조회, 단일조회

    // 전체 상품의 총액과 평균가격을 가져오는 기능

    public void save(Product product) {
        String sql = """
                INSERT INTO PRODUCTS
                    (name, price, stock_quantity, description, seller)
                VALUES
                    (?, ?, ?, ?, ?)
                """;
        template.update(sql,
                product.getName(), product.getPrice(),
                product.getStockQuantity(), product.getDescription(),
                product.getSeller()
        );
    }

    public void update(Product product) {
        String sql = """
                UPDATE PRODUCTS
                SET name = ?, price = ?, stock_quantity =?,
                      description = ?, seller = ?
                WHERE id = ?
                """;
        template.update(
                sql, product.getName(), product.getPrice()
                , product.getStockQuantity(), product.getDescription()
                , product.getSeller(), product.getId()
        );
    }

    public void deleteById(Long id) {
        String sql = """
                UPDATE PRODUCTS
                SET status = 'DELETED'
                WHERE id = ?
                """;
        template.update(sql, id);
    }

    // 전체조회 - 논리삭제된 행을 제외해야 함
    public List<Product> findAll() {
        String sql = """
                SELECT * FROM PRODUCTS
                WHERE status <> 'DELETED'
                """;

        // BeanPropertyRowMapper: 테이블의 컬럼명과 엔터티클래스의 필드명이
        // 똑같을 경우 (camel, snake 차이만 빼고) 자동 매핑해줌
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    // 전체 상품의 총액과 평균가격을 가져오는 기능
    public PriceInfo getPriceInfo() {
        String sql = """
                SELECT
                   SUM(price) AS total_price
                    , AVG(price) AS average_price
               FROM PRODUCTS
               WHERE status = 'ACTIVE'
               """;
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(PriceInfo.class));
    }

}
