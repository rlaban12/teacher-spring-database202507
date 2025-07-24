package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;

import java.util.List;

public interface BookRepository {

    // 도서 생성 기능
    boolean save(Book book);
    // 도서 제목, 저자 수정 기능
    boolean updateTitleAndAuthor(Book book);
    // 도서 삭제 기능
    boolean deleteById(Long id);
    // 도서 전체 조회 기능
    List<Book> findAll();
    // 도서 개별 조회 기능
    Book findById(Long id);

}
