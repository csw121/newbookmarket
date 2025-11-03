package com.bookmarket; // 우리의 기본 주소

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // '암호화 기계' import
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // [CTO 추가] '암호화 기계(PasswordEncoder)'를 '경호원(SecurityConfig)'으로부터 주입받습니다.
    private final PasswordEncoder passwordEncoder;

    public Member create(String username, String password, String name) {
        Member member = new Member();
        member.setUsername(username);

        // [CTO 수정]
        // 비밀번호를 그냥 저장(member.setPassword(password))하는 대신,
        // '암호화 기계'로 암호화(encode)해서 저장합니다.
        member.setPassword(passwordEncoder.encode(password));

        member.setName(name);
        this.memberRepository.save(member);
        return member;
    }
}