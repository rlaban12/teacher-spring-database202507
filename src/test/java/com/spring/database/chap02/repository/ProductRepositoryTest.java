package com.spring.database.chap02.repository;

import com.spring.database.chap02.dto.PriceInfo;
import com.spring.database.chap02.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired ProductRepository productRepository;


    @Test
    @DisplayName("save Test")
    void saveTest() {
        //given
        List<Product> productList = List.of(
                Product.builder()
                        .name("세탁기")
                        .price(1000000)
                        .description("좋은 세탁기입니다.")
                        .stockQuantity(140)
                        .seller("춘식이")
                        .build(),
                Product.builder()
                        .name("에어컨")
                        .price(2400000)
                        .description("좋은 에어컨입니다.")
                        .stockQuantity(120)
                        .seller("라이언")
                        .build(),
                Product.builder()
                        .name("선풍기")
                        .price(120000)
                        .description("좋은 선풍기입니다.")
                        .stockQuantity(90)
                        .seller("춘식이")
                        .build()
        );

        //when
        for (Product product : productList) {
            productRepository.save(product);
        }

        //then
    }


    @Test
    @DisplayName("논리삭제 테스트")
    void deleteTest() {
        //given
        Long id = 1L;
        //when
        productRepository.deleteById(id);
        //then
    }


    @Test
    @DisplayName("findAll test")
    void findAllTest() {
        //given

        //when
        List<Product> productList = productRepository.findAll();
        //then
        productList.forEach(System.out::println);
        assertEquals(2, productList.size());
    }



    @Test
    @DisplayName("총액과 평균")
    void sumAvgTest() {
        //given

        //when
        PriceInfo priceInfo = productRepository.getPriceInfo();
        //then
        System.out.println("총액: " + priceInfo.getTotalPrice());
        System.out.println("평균: " + priceInfo.getAveragePrice());
    }

}