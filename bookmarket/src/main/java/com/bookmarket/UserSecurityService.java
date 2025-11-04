package com.bookmarket; // 우리의 기본 주소

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority; // '권한' (나중에 쓸 부품)
import org.springframework.security.core.authority.SimpleGrantedAuthority; // '권한' (나중에 쓸 부품)
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // '있을 수도, 없을 수도'를 다루는 부품

@RequiredArgsConstructor
@Service // [CTO 설명] "이것도 '두뇌(Service)'입니다. Spring이 관리해주세요."
public class UserSecurityService implements UserDetailsService { // [CTO 설명] "UserDetailsService: Spring Security의 '사용자 조회' 규격입니다."

    private final MemberRepository memberRepository; // '손발(Repository)' 주입

    // [CTO 설명]
    // '경호원'이 로그인 요청을 받으면, 이 'loadUserByUsername' 메소드를 자동으로 호출합니다.
    // "username(ID)이 'csw'인 녀석 찾아줘!"
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. '손발(Repository)'에게 "DB에서 'username'으로 회원을 찾아봐줘" 라고 시킵니다.
        //    (아직 MemberRepository에 이 기능이 없으니, 곧 만들 겁니다.)
        Optional<Member> _member = this.memberRepository.findByUsername(username);

        // 2. 만약 DB에 회원이 없다면...
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다."); // "그런 사람 없어요!" 에러 발생
        }

        // 3. DB에 회원이 있다면...
        Member member = _member.get();

        // 4. [CTO 설명]
        //    '권한'을 설정합니다. 지금은 모든 사용자가 'ROLE_USER'라는 기본 권한을 갖도록 합니다.
        //    (나중에 '관리자(ADMIN)' 기능을 만들 때 여기서 분기 처리를 합니다.)
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) { // 만약 ID가 'admin'이면
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // '관리자' 권한을 주고
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // 그 외엔 '사용자' 권한을 줍니다.
        }

        // 5. '경호원'에게 "찾았습니다! 이 사람이 'csw'이고, 암호화된 비번은 '...$2a$...' 이거고, 권한은 'ROLE_USER'입니다!"
        //    라고 최종 보고합니다. '경호원'은 이 정보를 받아서, 사용자가 입력한 비밀번호와 DB의 암호화된 비번을 비교해줍니다.
        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}