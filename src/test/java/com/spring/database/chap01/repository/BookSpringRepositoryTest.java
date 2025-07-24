package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookSpringRepositoryTest {

    @Autowired
    BookSpringRepository bookSpringRepository;

    @Test
    @DisplayName("스프링 JDBC로 도서를 생성한다.")
    void saveTest() {
        //given
        Book newBook = Book.builder()
                .title("스프링 JDBC")
                .author("자바왕")
                .isbn("S001")
                .build();
        //when
        boolean flag = bookSpringRepository.save(newBook);
        //then
        assertTrue(flag);
    }

}