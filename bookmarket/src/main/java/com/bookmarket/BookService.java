package com.bookmarket; // 우리의 기본 주소

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List; // 'List' 부품 가져오기

@RequiredArgsConstructor // '손발(Repository)'을 주입받기 위한 Lombok
@Service // Spring에게 "이것도 '두뇌(Service)'입니다!" 라고 알림
public class BookService {

    // '두뇌(Service)'가 '손발(Repository)'에게 일을 시킵니다.
    private final BookRepository bookRepository;

    // [CTO 추가]
    // 1. '모든' 책 목록을 가져오는 기능 (메인 페이지용)
    public List<Book> getAllBooks() {
        // '손발'에게 "DB에 있는 책 '전부(findAll)' 가져와!" 라고 시킵니다.
        return this.bookRepository.findAll();
    }

    // [CTO 추가]
    // 2. '검색' 기능 (검색 페이지용)
    public List<Book> searchBooks(String keyword) {
        // '손발'에게 "제목에 'keyword'가 포함된 책을 '전부' 가져와!" 라고 시킵니다.
        return this.bookRepository.findByTitleContaining(keyword);
    }

    // (참고: 나중에 '관리자' 페이지를 만들면,
    //  여기에 '책 등록(save)'하는 기능을 추가하게 됩니다.)
}