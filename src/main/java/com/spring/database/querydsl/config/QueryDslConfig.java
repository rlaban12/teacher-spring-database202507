package com.spring.database.querydsl.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// QueryDsl의 핵심객체 JPAQueryFactory를 스프링에게 맡기는 설정 (빈 수동 등록)
@Configuration
@RequiredArgsConstructor
public class QueryDslConfig {

    private final EntityManager em;

    @Bean
    public JPAQueryFactory factory() {
        return new JPAQueryFactory(em);
    }
}
