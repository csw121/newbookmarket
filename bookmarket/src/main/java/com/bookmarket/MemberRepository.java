package com.bookmarket; // 우리의 기본 주소

import org.springframework.data.jpa.repository.JpaRepository;

// [CTO 설명]
// 우리는 JpaRepository<Member, Long> 라는 부품을 '상속' 받습니다.
// "Member(회원) 테이블을, Long(회원id) 타입으로 관리하는 JpaRepository를 쓰겠다"
//
// 이렇게만 하면, Spring이 알아서 '저장(save)', '조회(findById)', '삭제(delete)' 같은
// 모든 손발 기능을 '자동으로' 다 만들어줍니다. 우리는 코드를 짤 필요가 전혀 없습니다!
public interface MemberRepository extends JpaRepository<Member, Long> {

    // (참고: 나중에 '로그인' 기능을 만들 때, "username(로그인ID)으로 회원을 찾아줘" 라는
    //  특별한 기능이 필요하면 여기에 한 줄만 추가하면 됩니다. 지금은 비워둡니다.)
}