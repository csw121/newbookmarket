package com.bookmarket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // [CTO 추가] 데이터를 HTML로 넘겨줄 '모델' 부품
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    // [CTO 추가]
    // 4-5단계: '검색' 요청을 처리할 '안내원'
    // 1. @GetMapping("/book/search"): "/book/search" (GET) 요청을 이 메소드가 받습니다.
    // 2. @RequestParam("keyword"): '검색창'에 입력된 'keyword' 값을 받습니다.
    // 3. Model model: 검색 결과를 담을 '바구니'입니다.
    @GetMapping("/book/search")
    public String searchBooks(@RequestParam("keyword") String keyword, Model model) {

        // 1. '도서 두뇌(BookService)'에게 "키워드로 책을 '검색(searchBooks)'해!"라고 시킵니다.
        //    (BookService와 BookRepository에는 우리가 이미 이 기능을 만들어 뒀습니다.)
        List<Book> searchResult = this.bookService.searchBooks(keyword);

        // 2. '바구니(model)'에 2가지를 담습니다.
        // (1) "bookList"라는 이름으로 '검색된 책 목록'을 담습니다.
        //     (이제 '바구니'가 비어있지 않으므로, 'null.isEmpty()' 에러가 안 날 겁니다!)
        model.addAttribute("bookList", searchResult);

        // (2) "keyword"라는 이름으로 '사용자가 입력한 검색어'를 담습니다.
        //     (search_result.html 페이지에서 "[[${keyword}]]" (으)로 검색한 결과... 라고 표시할 때 씁니다.)
        model.addAttribute("keyword", keyword);

        // 3. 'search_result.html' 페이지로 이동시킵니다.
        return "search_result";
    }
    @GetMapping("/book/detail/{id}")
    public String bookDetail(@PathVariable("id") Long id, Model model) {

        // 1. '도서 두뇌(BookService)'에게 "DB에서 'id'로 책을 '찾아(getBookById)'줘!"라고 시킵니다.
        //    (이 기능은 BookService에 아직 없으니, '곧' 만들 겁니다.)
        Book book = this.bookService.getBookById(id);

        // 2. '바구니(model)'에 "book"이라는 이름으로 '찾은 책'을 담습니다.
        model.addAttribute("book", book);

        // 3. (임시) 'book_detail.html' 이라는 '새 페이지'로 이동시킵니다.
        return "book_detail";
    }

} // (이것이 클래스의 '맨 마지막' 닫는 괄호여야 합니다)