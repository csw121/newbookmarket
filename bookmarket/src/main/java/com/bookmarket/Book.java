package com.bookmarket; // 우리의 기본 주소

import jakarta.persistence.*; // JPA 부품
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // JPA: "이 클래스는 'book' 테이블과 연결됩니다!"
public class Book { // 테이블 이름은 클래스 이름을 따라 'book'이 됩니다.

    @Id // 기본 키(Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 알아서 1씩 자동 증가
    private Long id; // 도서 고유번호

    @Column(length = 200, nullable = false) // 200자, 비어있으면 안 됨
    private String title; // 책 제목

    @Column(length = 100, nullable = false)
    private String author; // 저자

    @Column(length = 100, nullable = false)
    private String publisher; // 출판사

    private int price; // 정가

    @Column(columnDefinition = "TEXT") // TEXT: 아주 긴 텍스트를 저장할 수 있음
    private String description; // 책 설명 (길어질 수 있으니)

    private String coverImgUrl; // 책 표지 이미지 URL

    // (참고: 나중에 '가격 비교' 기능을 만들 때,
    //  여기에 '알라딘 URL', 'Yes24 URL' 등을 추가하게 됩니다.)
}