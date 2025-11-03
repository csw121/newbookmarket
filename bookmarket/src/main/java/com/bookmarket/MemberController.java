package com.bookmarket; // 우리의 기본 주소

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor // '두뇌(Service)'를 주입받기 위한 Lombok
@Controller // [CTO 설명] Spring에게 "이 클래스는 '입구(Controller)' 역할을 합니다!" 라고 알립니다.
public class MemberController {

    // '입구(Controller)'는 '두뇌(Service)'에게 일을 넘겨야 합니다.
    private final MemberService memberService;

    // [CTO 설명]
    // GetMapping: "누군가 /member/signup 주소로 'GET' 요청(페이지 보여줘!)을 하면,"
    // "signup.html 파일을 찾아서 보여줘!" 라는 뜻입니다.
    @GetMapping("/member/signup")
    public String signup() {
        return "signup"; // 'signup.html' 템플릿을 찾으라는 의미
    }

    // [CTO 설명]
    // PostMapping: "누군가 /member/signup 주소로 'POST' 요청(데이터 처리해줘!)을 하면,"
    // "HTML 폼(form)에서 보낸 username, password, name 데이터를 받아서,"
    // "memberService의 create(회원가입) 기능을 실행시켜!" 라는 뜻입니다.
    @PostMapping("/member/signup")
    public String signup(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("name") String name) {

        // 1. '두뇌(Service)'에게 3가지 데이터를 넘겨주며 회원가입을 시킵니다.
        this.memberService.create(username, password, name);

        // 2. 회원가입이 성공하면, '메인 페이지(/)'로 돌려보냅니다. (리다이렉트)
        return "redirect:/";
    }
}