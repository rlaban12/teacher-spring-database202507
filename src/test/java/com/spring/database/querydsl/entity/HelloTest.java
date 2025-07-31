package com.spring.database.querydsl.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.jpa.chap02.entity.QStudent;
import com.spring.database.jpa.chap02.entity.Student;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HelloTest {

    @Autowired
    EntityManager em;


    @Test
    @DisplayName("querydsl 동작 확인")
    void testExecuteQueryDsl() {
        //given
        Student student = Student.builder()
                .name("꿈돌이")
                .city("대전광역시")
                .major("국문학과")
                .build();

        em.persist(student);  // 순수 JPA의 저장기능

        //when
        JPAQueryFactory factory = new JPAQueryFactory(em);

        Student foundStudent = factory
                .selectFrom(QStudent.student)
                .where(QStudent.student.name.eq("꿈돌이"))
                .fetchOne();

        //then
        System.out.println("foundStudent = " + foundStudent);
    }

}