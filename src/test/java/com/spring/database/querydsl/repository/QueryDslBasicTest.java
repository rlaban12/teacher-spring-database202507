package com.spring.database.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.querydsl.entity.Group;
import com.spring.database.querydsl.entity.Idol;
import com.spring.database.querydsl.entity.QIdol;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static com.spring.database.querydsl.entity.QIdol.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QueryDslBasicTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    EntityManager em;  // 순수 JPA의 핵심객체

    @Autowired
    JdbcTemplate jdbcTemplate;  // JDBC의 핵심객체

    @BeforeEach
    void setUp() {

        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);

        Idol idol1 = new Idol("김채원", 24, leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, leSserafim);
        Idol idol3 = new Idol("가을", 22, ive);
        Idol idol4 = new Idol("리즈", 20, ive);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);

        em.flush();
        em.clear();
    }


    @Test
    @DisplayName("순수 JPQL로 특정 이름의 아이돌 조회하기")
    void rawJpqlTest() {
        //given
        String jpql = """
                SELECT i
                FROM Idol i
                WHERE i.idolName = ?1
                """;

        //when
        Idol foundIdol = em.createQuery(jpql, Idol.class)
                .setParameter(1, "가을")
                .getSingleResult();

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());

    }


    @Test
    @DisplayName("순수 SQL로 특정 이름의 아이돌 조회하기")
    void nativeSqlTest() {
        //given
        String sql = """
                SELECT *
                FROM tbl_idol
                WHERE idol_name = ?1
                """;
        //when
        Idol foundIdol = (Idol) em.createNativeQuery(sql, Idol.class)
                .setParameter(1, "리즈")
                .getSingleResult();

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }


    @Test
    @DisplayName("JDBC로 특정이름의 아이돌 조회하기")
    void jdbcTest() {
        //given
        String sql = """
                SELECT *
                FROM tbl_idol
                WHERE idol_name = ?
                """;
        //when
        Idol foundIdol = jdbcTemplate.queryForObject(sql,
                (rs, n) -> new Idol(
                        rs.getLong("idol_id")
                        , rs.getString("idol_name")
                        , rs.getInt("age")
                        , null
                ),
                "김채원"
        );

        Group group = jdbcTemplate.queryForObject("""
                        SELECT * FROM tbl_group WHERE group_id = ?
                        """,
                (rs, n) -> new Group(
                        rs.getLong("group_id")
                        , rs.getString("group_name")
                        , null
                ),
                1
        );

        foundIdol.setGroup(group);

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }


    @Test
    @DisplayName("QueryDsl로 특정 이름의 아이돌 조회하기")
    void queryDslTest() {
        //given
        JPAQueryFactory factory = new JPAQueryFactory(em);

        //when
        Idol foundIdol = factory
                .selectFrom(idol)
                .where(idol.idolName.eq("사쿠라"))
                .fetchOne();

        //then
        System.out.println("\n\nfoundIdol = " + foundIdol);
        System.out.println("foundIdol.getGroup() = " + foundIdol.getGroup());
    }


}