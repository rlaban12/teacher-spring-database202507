package com.spring.database.querydsl.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.querydsl.entity.Group;
import com.spring.database.querydsl.entity.Idol;
import com.spring.database.querydsl.entity.QIdol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.spring.database.querydsl.entity.QIdol.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class QueryDslGroupingTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    JPAQueryFactory factory;

    @BeforeEach
    void testInsertData() {
        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");
        Group bts = new Group("방탄소년단");
        Group newjeans = new Group("newjeans");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);
        groupRepository.save(bts);
        groupRepository.save(newjeans);

        Idol idol1 = new Idol("김채원", 24, "여", leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, "여", leSserafim);
        Idol idol3 = new Idol("가을", 22, "여", ive);
        Idol idol4 = new Idol("리즈", 20, "여", ive);
        Idol idol5 = new Idol("장원영", 20, "여", ive);
        Idol idol6 = new Idol("안유진", 21, "여", ive);
        Idol idol7 = new Idol("카즈하", 21, "여", leSserafim);
        Idol idol8 = new Idol("RM", 29, "남", bts);
        Idol idol9 = new Idol("정국", 26, "남", bts);
        Idol idol10 = new Idol("해린", 18, "여", newjeans);
        Idol idol11 = new Idol("혜인", 16, "여", newjeans);

        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
        idolRepository.save(idol5);
        idolRepository.save(idol6);
        idolRepository.save(idol7);
        idolRepository.save(idol8);
        idolRepository.save(idol9);
        idolRepository.save(idol10);
        idolRepository.save(idol11);
    }


    @Test
    @DisplayName("SELECT절에서 원하는 컬럼만 조회하는 방법")
    void tupleTest() {
        //given

        //when
        List<Tuple> idolList = factory
                .select(idol.idolName, idol.age)
                .from(idol)
                .fetch();

        //then
        for (Tuple tuple : idolList) {
            String name = tuple.get(idol.idolName);
            Integer age = tuple.get(idol.age);

            System.out.printf("이름: %s, 나이: %d세\n", name, age);
        }
    }


    @Test
    @DisplayName("전체 그룹화하기 - GROUP BY없이 집계함수를 사용하는 것")
    void groupByTest() {
        //given

        //when
        Integer sumAge = factory
                .select(idol.age.sum()) // SUM(age)
                .from(idol)
                .fetchOne();
        //then
        System.out.println("sumAge = " + sumAge);
    }


    @Test
    @DisplayName("그룹별 인원수 세기")
    void groupByCountTest() {
        /*
            SELECT G.group_name, COUNT(*)
            FROM tbl_idol I
            INNER JOIN tbl_group G
            ON I.group_id = G.group_id
            GROUP BY I.group_id
         */
        //given

        //when
        List<Tuple> idolCounts = factory
                .select(idol.group.groupName, idol.count())
                .from(idol)
                .groupBy(idol.group.id)
                .fetch();
        //then
        for (Tuple tuple : idolCounts) {
            String groupName = tuple.get(idol.group.groupName);
            Long count = tuple.get(idol.count());
            System.out.printf("그룹명: %s, 인원수: %d명\n", groupName, count);
        }
    }



    @Test
    @DisplayName("성별별 아이돌 인원수 세기")
    void groupByGenderTest() {
        /*
            SELECT gender, COUNT(*)
            FROM tbl_idol
            GROUP BY gender
         */
        //given

        //when
        List<Tuple> tuples = factory
                .select(idol.gender, idol.count())
                .from(idol)
                .groupBy(idol.gender)
                .fetch();

        //then
        tuples.forEach(t -> {
            String gender = t.get(idol.gender);
            Long count = t.get(idol.count());
            System.out.printf("[ %s ] : %d\n", gender, count);
        });
    }

}

