package com.spring.database.chap01.routes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 타임리프 html 뷰를 포워딩하는 클래스
@Controller
public class BookRouteController {

    @GetMapping("/book-page")
    public String bookPage() {
        return "book-page";
    }
}
