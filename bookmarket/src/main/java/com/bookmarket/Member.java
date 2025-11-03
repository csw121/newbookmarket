package com.bookmarket; // 우리의 기본 주소

// 우리가 사용할 부품들을 가져옵니다. (Lombok, JPA)
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // Lombok: 이 클래스의 모든 필드에 대한 Getter/Setter를 자동으로 만들어줍니다.
@Entity // JPA: "이 클래스는 데이터베이스 테이블과 연결됩니다!" 라고 선언합니다.
@Table(name = "member") // "테이블 이름은 'member'로 해주세요."
public class Member {

    @Id // "이 필드가 이 테이블의 '기본 키(Primary Key)'입니다."
    @GeneratedValue(strategy = GenerationType.IDENTITY) // "DB가 알아서 번호를 1씩 자동 증가시켜주세요."
    private Long id; // 회원 고유번호 (자동으로 1, 2, 3... 증가)

    @Column(unique = true) // "이 값은 중복되면 안 됩니다. (예: ID 중복 방지)"
    private String username; // 로그인 ID

    private String password; // 비밀번호

    private String name; // 사용자 이름 (닉네임)

    // (참고: 나중에 '찜하기' 기능을 만들 때 여기에 찜 목록이 추가될 겁니다.)
}