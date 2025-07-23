package com.spring.database.chap01.repository;

import com.spring.database.chap01.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
    public boolean deleteById() {

    }

}
