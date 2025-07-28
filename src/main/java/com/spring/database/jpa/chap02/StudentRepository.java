package com.spring.database.jpa.chap02;

import com.spring.database.jpa.chap02.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository의 제네릭에는 첫번째 엔터티, 두번째 ID 타입
public interface StudentRepository extends JpaRepository<Student, String> {

    // Query Method: 메서드에 특별한 이름규칙을 사용해서 SQL을 생성
    List<Student> findByName(String name);

    // WHERE city = ? AND major = ?
    List<Student> findByCityAndMajor(String city, String major);

    // WHERE major LIKE '%?%'
    List<Student> findByMajorContaining(String major);

    // WHERE major LIKE '?%'
    List<Student> findByMajorStartingWith(String major);

    // WHERE major LIKE '%?'
    List<Student> findByMajorEndingWith(String major);

    // WHERE age <= ?
//    List<Student> findByAgeLessThanEqual(int age);

}
