package com.spring.database.chap01.api;

import com.spring.database.chap01.entity.Book;
import com.spring.database.chap01.repository.BookJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookJdbcRepository bookJdbcRepository;

    // 전체 조회 요청
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(bookJdbcRepository.findAll());
    }

    // 생성 요청
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book book) {

        bookJdbcRepository.save(book);
        return ResponseEntity.ok("도서 등록 성공!");
    }
    // 수정 요청
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        bookJdbcRepository.updateTitleAndAuthor(book);
        return ResponseEntity.ok("도서 수정 성공!");
    }
    // 삭제 요청
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookJdbcRepository.deleteById(id);
        return ResponseEntity.ok("도서 삭제 성공!");
    }
    // 개별 조회 요청
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(bookJdbcRepository.findById(id));
    }
}
