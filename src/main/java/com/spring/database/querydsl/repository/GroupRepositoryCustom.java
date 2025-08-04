package com.spring.database.querydsl.repository;

import com.spring.database.querydsl.dto.GroupAverageAge;

import java.util.List;

// queryDSL이나 다른 ORM라이브러리를 JPA와 결합하기 위한 추가 인터페이스
public interface GroupRepositoryCustom {

    // 그룹별 평균 나이 조회
    List<GroupAverageAge> groupAverage();
}
