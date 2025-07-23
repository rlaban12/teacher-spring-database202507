package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 역할: 데이터베이스에 접근해서 CRUD를 수행하는 객체
@RequiredArgsConstructor
@Repository
public class BookRepository {

    private final DataSource dataSource;

    // INSERT 기능 - 도서 생성
    public boolean save(Book book) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                        INSERT INTO BOOKS
                            (title, author, isbn)
                        VALUES
                            (?, ?, ?)
                        """;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());

            int result = pstmt.executeUpdate();
            return result == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 도서 제목, 저자 수정
    public boolean updateTitleAndAuthor(Book book) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = """
                    UPDATE BOOKS
                    SET author = ?, title = ?
                    WHERE id = ?
                    """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, book.getAuthor());
            pstmt.setString(2, book.getTitle());
            pstmt.setLong(3, book.getId());

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 도서 정보 삭제
    public boolean deleteById(Long id) {
        try (Connection conn = dataSource.getConnection()) {

            String sql = """
                    DELETE FROM BOOKS
                    WHERE id = ?
                    """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 전체 조회 - ORM (Object Relational Mapping)
    public List<Book> findAll() {

        List<Book> bookList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            String sql = """
                        SELECT * FROM books
                    """;

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) bookList.add(new Book(rs));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    // id로 단일조회 메서드
    public Book findById() {
        return null;
    }

}
