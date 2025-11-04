package com.bookmarket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // [CTO 추가] 데이터를 HTML로 넘겨줄 '모델' 부품
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List; // [CTO 추가] 'List' 부품

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final BookService bookService; // [CTO 추가] '도서 두뇌(BookService)'도 주입받습니다.

    // ... (signup, login 메소드는 동일) ...
    @GetMapping("/member/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/member/signup")
    public String signup(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("name") String name) {

        this.memberService.create(username, password, name);
        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String login() {
        return "login";
    }

    // [CTO 수정]
    // 메인 페이지(/)를 보여줄 때, 'Model'이라는 '바구니'에 '도서 목록'을 담아서 갑니다.
    @GetMapping("/")
    public String mainPage(Model model) {
        // 1. '도서 두뇌(BookService)'에게 "모든 책 목록 줘!" 라고 시킵니다.
        List<Book> bookList = this.bookService.getAllBooks();

        // 2. '모델 바구니'에 "bookList"라는 이름표로 '도서 목록'을 담습니다.
        model.addAttribute("bookList", bookList);

        // 3. "main.html" 템플릿을 찾아서 '모델 바구니'를 전달합니다.
        return "main";
    }
}