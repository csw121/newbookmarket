package com.bookmarket; // 우리의 기본 주소

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // [CTO 추가] 여러 개의 List를 가져오는 부품

// "Book(도서) 테이블을, Long(도서 id) 타입으로 관리하는 JpaRepository를 쓰겠다"
// 이렇게만 하면 '저장(save)', '전체 조회(findAll)' 등이 자동으로 완성됩니다.
public interface BookRepository extends JpaRepository<Book, Long> {

    // [CTO 추가]
    // 이것이 '검색' 기능의 핵심입니다.
    // Spring Data JPA가 "findByTitleContaining"라는 메소드 이름을 분석해서,
    // "책 제목(Title)에, 특정 키워드가 포함(Containing)된" 책들을
    // '모두(List)' 찾아주는 SQL을 '자동으로' 만들어줍니다!
    List<Book> findByTitleContaining(String keyword);
}