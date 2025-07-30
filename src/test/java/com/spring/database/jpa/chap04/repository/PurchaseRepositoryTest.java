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
import java.util.stream.Collectors;

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


    @Test
    @DisplayName("특정 유저의 모든 구매 정보 목록을 조회한다")
    void findPurchasesTest() {
        //given
        Purchase p1 = Purchase.builder()
                .user(user1)
                .goods(goods1)
                .build();
        Purchase p2 = Purchase.builder()
                .user(user1)
                .goods(goods3)
                .build();

        purchaseRepository.save(p1);
        purchaseRepository.save(p2);

        em.flush();
        em.clear();

        //when

        // 1번 유저는 무슨 상품들을 구매했을까?
        User user = userRepository.findById(1L).orElseThrow();
        List<Purchase> purchases = user.getPurchases();

        //then
        for (Purchase p : purchases) {
            System.out.printf("%s가 구매한 상품명: %s\n"
            , user.getName(), p.getGoods().getName());
        }
    }


    @Test
    @DisplayName("특정 상품을 구매한 유저의 목록을 조회한다.")
    void findGoodsListTest() {
        //given
        Purchase p1 = Purchase.builder()
                .user(user2)
                .goods(goods2)
                .build();
        Purchase p2 = Purchase.builder()
                .user(user3)
                .goods(goods2)
                .build();

        purchaseRepository.save(p1);
        purchaseRepository.save(p2);

        em.flush();
        em.clear();
        //when
        // 2번 상품을 누가 샀는가?
        Goods goods = goodsRepository.findById(2L).orElseThrow();
        List<Purchase> purchases = goods.getPurchases();

        //then
        List<String> names = purchases.stream()
                .map(p -> p.getUser().getName())
                .collect(Collectors.toList());

        System.out.println("names = " + names);
    }


    @Test
    @DisplayName("회원이 탈퇴하면 구매기록이 모두 사라져야 한다.")
    void cascadeTest() {
        //given
        Purchase p1 = Purchase.builder()
                .user(user1)
                .goods(goods2)
                .build();
        Purchase p2 = Purchase.builder()
                .user(user1)
                .goods(goods3)
                .build();

        Purchase p3 = Purchase.builder()
                .user(user2)
                .goods(goods3)
                .build();

        purchaseRepository.save(p1);
        purchaseRepository.save(p2);
        purchaseRepository.save(p3);

        em.flush();
        em.clear();
        //when

        User user = userRepository.findById(1L).orElseThrow();
        List<Purchase> purchases = user.getPurchases();

        // user1의 구매기록 리스트 확인
        System.out.println("\n\nuser1's purchases = " + purchases + "\n\n");
        // 모든 회원의 구매기록
        System.out.println("\n\npurchaseRepository.findAll() = "
                + purchaseRepository.findAll() + "\n\n");


        //then
        userRepository.delete(user);

        em.flush();
        em.clear();

        // 탈퇴 후 다시 구매기록 전체조회
        List<Purchase> purchaseList = purchaseRepository.findAll();

        System.out.println("\n\nafter user deletion purchaseList = " + purchaseList + "\n\n");
    }



}