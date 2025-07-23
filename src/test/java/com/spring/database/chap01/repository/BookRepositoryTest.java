package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest  // 스프링 컨텍스트에서 관리되는 빈을 꺼내올 수 있음
class BookRepositoryTest {

    // 테스트 프레임워크 : JUnit
    // 5버전에서부터는 생성자주입을 막아놈 - 필드주입 해야함
    @Autowired
    BookRepository bookRepository;

    // 테스트 메서드
    @Test
    // 테스트의 목적을 주석처럼 적는다. 여기서 문장표현은 단언(Assertion)을 사용한다.
    @DisplayName("도서 정보를 주면 데이터베이스 book테이블에 저장된다.")
    void saveTest() {
        // GWT 패턴
        // given - 테스트를 위해 필요한 데이터
        Book givenBook = Book.builder()
                .title("디아블로4")
                .author("블리자드")
                .isbn("D004")
                .build();

        // when - 실제 테스트가 벌어지는 상황
        boolean flag = bookRepository.save(givenBook);

        // then - 테스트 결과 (단언)
        System.out.println("flag = " + flag);
        assertTrue(flag);
    }


    @Test
    @DisplayName("도서의 제목과 저자명을 수정해야 한다.")
    void updateTest() {
        //given
        Book updatedBook = Book.builder()
                .title("수정된 책")
                .author("수정된 저자명")
                .id(6L)
                .build();
        //when
        boolean flag = bookRepository.updateTitleAndAuthor(updatedBook);
        //then
        assertFalse(!flag);
    }


    @Test
    @DisplayName("id를 주면 book테이블에서 해당 id를 가진 행이 삭제된다.")
    void deleteTest() {
        //given
        Long givenId = 5L;
        //when
        boolean flag = bookRepository.deleteById(givenId);
        //then
        assertTrue(flag);
    }


    @Test
    @DisplayName("전체조회를 하면 도서의 리스트가 반환된다.")
    void findAllTest() {
        //given

        //when
        List<Book> bookList = bookRepository.findAll();
        //then
        bookList.forEach(System.out::println);

        assertEquals(4, bookList.size());
        assertNotNull(bookList.get(0));
        assertEquals("반지의제왕", bookList.get(0).getTitle());
    }

    @Test
    @DisplayName("적합한 id를 통해 개별조회를 하면 도서 1개의 객체가 반환된다.")
    void findOneTest() {
        //given
        Long givenId = 4L;
        //when
        Book foundBook = bookRepository.findById(givenId);
        //then
        System.out.println("foundBook = " + foundBook);
        assertNotNull(foundBook);
        assertTrue(foundBook.isAvailable());
        assertEquals("꿀잼책", foundBook.getTitle());
    }


}