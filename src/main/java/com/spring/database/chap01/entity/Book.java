package com.spring.database.chap01.entity;

/*
CREATE TABLE IF NOT EXISTS books (
     id BIGINT AUTO_INCREMENT,
     title VARCHAR(200) NOT NULL COMMENT '도서 제목',
     author VARCHAR(100) NOT NULL COMMENT '저자',
     isbn VARCHAR(13) NOT NULL COMMENT 'ISBN',
     available BOOLEAN DEFAULT true COMMENT '대출 가능 여부',
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
     PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='도서 정보';
*/

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private boolean available;
    private LocalDateTime createdAt;

    public Book(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.title = rs.getString("title");
        this.author = rs.getString("author");
        this.isbn = rs.getString("isbn");
        this.available = rs.getBoolean("available");
        this.createdAt = rs.getTimestamp("created_at").toLocalDateTime();
    }
}
