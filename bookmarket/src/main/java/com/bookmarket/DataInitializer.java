package com.bookmarket;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner; // [CTO 설명] Spring Boot가 '부팅'된 '직후'에 실행되는 부품
import org.springframework.stereotype.Component; // Spring이 이 파일도 관리하도록 등록

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    // '손발(Repository)'을 직접 주입받습니다.
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        // [CTO 설명]
        // "만약 '손발'을 시켜 '모든 책'을 세어봤는데 0권이면 (창고가 비어있으면),"
        // "아래 샘플 책 2권을 '창고'에 저장(save)해줘!"
        if (bookRepository.count() == 0) {
            // 샘플 책 1
            Book book1 = new Book();
            book1.setTitle("JPA 프로그래밍 입문");
            book1.setAuthor("김영한");
            book1.setPublisher("에이콘");
            book1.setPrice(38000);
            book1.setDescription("자바 개발자를 위한 JPA 완벽 가이드");
            book1.setCoverImgUrl("https://img.aladin.co.kr/img/cover/s/896077926x.jpg"); // (임의의 이미지 URL)

            bookRepository.save(book1); // 1번 책 저장

            // 샘플 책 2
            Book book2 = new Book();
            book2.setTitle("스프링 부트 완벽 가이드");
            book2.setAuthor("박성철");
            book2.setPublisher("로드북");
            book2.setPrice(35000);
            book2.setDescription("스프링 부트로 시작하는 웹 개발");
            book2.setCoverImgUrl("https://img.aladin.co.kr/img/cover/s/k042535390_1.jpg"); // (임의의 이미지 URL)

            bookRepository.save(book2); // 2번 책 저장
        }
    }
}