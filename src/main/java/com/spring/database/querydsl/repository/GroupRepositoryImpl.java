package com.spring.database.querydsl.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.database.querydsl.dto.GroupAverageAge;
import com.spring.database.querydsl.entity.QIdol;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.spring.database.querydsl.entity.QIdol.*;

@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    // queryDsl을 사용하기 위한 의존객체
    private final JPAQueryFactory factory;

    @Override
    public List<GroupAverageAge> groupAverage() {
        return factory
                .select(
                        Projections.constructor(
                                // 사용할 DTO를 명시
                                GroupAverageAge.class
                                , idol.group.groupName
                                , idol.age.avg()
                        )
                )
                .from(idol)
                .groupBy(idol.group)
                .fetch();
    }
}
