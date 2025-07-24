package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

// Spring JDBC로 도서 CRUD를 관리
@Repository
@RequiredArgsConstructor
public class BookSpringRepository implements BookRepository {

    private final JdbcTemplate template;

    @Override
    public boolean save(Book book) {
        String sql = """
                        INSERT INTO BOOKS
                            (title, author, isbn)
                        VALUES
                            (?, ?, ?)
                        """;
        return template.update(
                sql,
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn()
        ) == 1;
    }

    @Override
    public boolean updateTitleAndAuthor(Book book) {
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public List<Book> findAll() {
        return List.of();
    }

    @Override
    public Book findById(Long id) {
        return null;
    }
}
