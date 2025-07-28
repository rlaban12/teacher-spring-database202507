package com.spring.database.jpa.chap01.repository;

import com.spring.database.jpa.chap01.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.spring.database.jpa.chap01.entity.Product.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링이 관리하는 모든 Bean을 로딩
//@Transactional
//@Rollback // 테스트가 끝나면 DML을 취소

//@DataJpaTest // @Repository 빈만 로딩, 롤백옵션 자동포함, 내장 디비만 사용가능 (H2 Database)
class ProductJpaRepositoryTest {

    @Autowired
    ProductJpaRepository productJpaRepository;

    // 테스트 전에 자동으로 실행될 코드
    @BeforeEach
    void insertBefore() {
        Product p1 = Product.builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(2000000)
                .build();
        Product p2 = Product.builder()
                .name("탕수육")
                .category(FOOD)
                .price(20000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(FASHION)
                .price(300000)
                .build();
        Product p4 = Product.builder()
                .name("주먹밥")
                .category(FOOD)
                .price(1500)
                .build();

        // 전부 insert하고 commit까지 (확정)
        productJpaRepository.saveAllAndFlush(
                List.of(p1, p2, p3, p4)
        );
    }

    @Test
    @DisplayName("상품정보를 데이터베이스에 저장한다.")
    void saveTest() {
        //given
        Product newProduct = Product.builder()
                .name("정장재킷")
                .price(300000)
                .category(FASHION)
                .build();
        //when
        Product saved = productJpaRepository.save(newProduct);

        //then
        assertNotNull(saved);
    }


    @Test
    @DisplayName("1번 상품을 삭제한다.")
    void deleteTest() {
        //given
        Long id = 1L;
        //when
        productJpaRepository.deleteById(id);
        //then
    }


    @Test
    @DisplayName("3번 상품을 단일조회하면 그 상품명은 구두이다.")
    void findOneTest() {
        //given
        Long id = 4L;
        //when
        Optional<Product> foundProduct = productJpaRepository.findById(id);
        //then
        System.out.println("상품명 = " + foundProduct.map(Product::getName).orElse("상품명없음"));
    }



    @Test
    @DisplayName("2번 상품의 이름과 가격을 수정한다")
    void updateTest() {
        //given
        Long id = 2L;
        String newName = "청소기";
        int newPrice = 150000;
        Product.Category newCategory = ELECTRONIC;
        //when
        /*
            JPA에서는 수정메서드를 따로 제공하지 않습니다.
            단일 조회를 수행한 후 setter를 통해 값을 변경하고
            다시 save를 하면 INSERT문 대신에 UPDATE문이 나갑니다.
         */
        Product foundProduct = productJpaRepository.findById(id).orElseThrow();
//        foundProduct.setName(newName);
//        foundProduct.setPrice(newPrice);
//        foundProduct.setCategory(newCategory);

        foundProduct.changeProduct(newName, newPrice, newCategory);

        productJpaRepository.save(foundProduct);

        //then
    }


    @Test
    @DisplayName("상품을 전체조회하면 총 4개의 상품이 조회된다")
    void findAllTest() {
        //given

        //when
        List<Product> productList = productJpaRepository.findAll();
        long count = productJpaRepository.count();
        //then
        productList.forEach(System.out::println);
        System.out.println("\n# count = " + count);

        assertEquals(4, productList.size());
    }



}