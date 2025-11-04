package com.bookmarket; // 우리의 기본 주소

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List; // 'List' 부품 가져오기
import java.util.Optional;

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
    public Book getBookById(Long id) {

        // 1. '손발(Repository)'에게 "DB에서 'id'로 책을 '찾아(findById)'줘!" 라고 시킵니다.
        //    (findById는 JpaRepository에 '이미' 들어있는 '기본' 기능입니다.)
        //    (결과는 '있을 수도, 없을 수도' 있으니 'Optional<Book>'에 담겨 옵니다.)
        Optional<Book> _book = this.bookRepository.findById(id);

        // 2. 만약 'Optional' 바구니 안에 '책이 있다면(isPresent)',
        if (_book.isPresent()) {
            return _book.get(); // '바구니'에서 '책'을 꺼내서 '반환'합니다.
        } else {
            // 3. 만약 '바구니'가 '비어있다면' (예: 99번 책처럼 없는 책을 찾으면),
            //    '에러'를 발생시킵니다.
            throw new RuntimeException("Book not found for id: " + id);
        }
    }

} // (이것이 클래스의 '맨 마지막' 닫는 괄호여야 합니다)