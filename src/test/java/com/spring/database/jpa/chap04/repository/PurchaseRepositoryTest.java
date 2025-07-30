package com.spring.database.jpa.chap04.repository;

import com.spring.database.jpa.chap04.entity.Goods;
import com.spring.database.jpa.chap04.entity.Purchase;
import com.spring.database.jpa.chap04.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class PurchaseRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    EntityManager em;

    private User user1;
    private User user2;
    private User user3;
    private Goods goods1;
    private Goods goods2;
    private Goods goods3;

    @BeforeEach
    void setUp() {
        user1 = User.builder().name("망곰이").build();
        user2 = User.builder().name("하츄핑").build();
        user3 = User.builder().name("쿠로미").build();

        goods1 = Goods.builder().name("뚜비모자").build();
        goods2 = Goods.builder().name("닭갈비").build();
        goods3 = Goods.builder().name("중식도").build();

        userRepository.saveAllAndFlush(
                List.of(user1, user2, user3)
        );

        goodsRepository.saveAllAndFlush(
                List.of(goods1, goods2, goods3)
        );
    }


    @Test
    @DisplayName("회원과 상품을 연결한 구매기록 생성 테스트")
    void createPurchaseTest() {
        //given
        Purchase purchase = Purchase.builder()
                .user(user2)
                .goods(goods1)
                .build();
        //when
        Purchase saved = purchaseRepository.save(purchase);

        em.flush();
        em.clear();

        //then
        // SELECT가 안나가는 이유는? 영속성 컨텍스트 때문
        User user = userRepository.findById(saved.getUser().getId()).orElseThrow();
        Goods goods = goodsRepository.findById(saved.getGoods().getId()).orElseThrow();

        System.out.println("user = " + user);
        System.out.println("goods = " + goods);
    }

}