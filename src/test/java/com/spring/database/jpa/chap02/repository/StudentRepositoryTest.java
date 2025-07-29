package com.spring.database.jpa.chap02.repository;

import com.spring.database.jpa.chap02.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class StudentRepositoryTest {

    @Autowired StudentRepository studentRepository;

    @BeforeEach
    void bulkSave() {
        Student s1 = Student.builder()
                .name("쿠로미")
                .city("청양군")
                .major("경제학")
                .build();

        Student s2 = Student.builder()
                .name("춘식이")
                .city("서울시")
                .major("컴퓨터공학")
                .build();

        Student s3 = Student.builder()
                .name("어피치")
                .city("제주도")
                .major("화학공학")
                .build();

        studentRepository.saveAllAndFlush(
                List.of(s1, s2, s3)
        );
    }


    @Test
    @DisplayName("이름이 춘식이인 학생의 모든 정보를 조회한다.")
    void test1() {
        //given
        String name = "춘식이";
        //when
        List<Student> students = studentRepository.findByName(name);

        //then
        assertEquals(1, students.size());
        assertEquals("컴퓨터공학", students.get(0).getMajor());

        System.out.println("students.get(0) = " + students.get(0));

    }


    @Test
    @DisplayName("도시명과 전공명으로 조회")
    void test2() {
        //given
        String city = "제주도";
        String major = "화학공학";
        //when
        // WHERE city = ? AND major = ?
        List<Student> students = studentRepository.findByCityAndMajor(city, major);

        //then
        students.forEach(System.out::println);
    }



    @Test
    @DisplayName("전공에 공학이 포함된 학생들 조회")
    void test3() {
        //given
        String containingMajor = "공학";
        //when
        List<Student> students = studentRepository.findByMajorContaining(containingMajor);

        //then
        students.forEach(System.out::println);
    }


    @Test
    @DisplayName("JPQL로 조회해보기")
    void jpqlTest() {
        //given
        String city = "서울시";
        //when
        List<Student> students = studentRepository.getStudentByCity(city);
        //then
        students.forEach(System.out::println);
    }


    @Test
    @DisplayName("순수 SQL로 조회하기")
    void nativeSQLTest() {
        //given
        String name = "어피치";
        String city = "제주도";
        //when
        List<Student> students = studentRepository.getStudents(name, city);
        //then
        students.forEach(System.out::println);
    }



}