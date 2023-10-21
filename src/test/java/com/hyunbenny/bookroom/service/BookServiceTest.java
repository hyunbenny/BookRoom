package com.hyunbenny.bookroom.service;

import com.hyunbenny.bookroom.dto.BookDto;
import com.hyunbenny.bookroom.entity.Book;
import com.hyunbenny.bookroom.exception.book.BookNotFoundException;
import com.hyunbenny.bookroom.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("책 비지니스 로직")
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @DisplayName("책 등록 성공")
    @Test
    void given_bookDto_when_requestSave_then_saveBook() {
        // given
        BookDto bookDto = createBookDto();
        Book book = createBook();
        given(bookRepository.findByIsbn(bookDto.isbn())).willReturn(Optional.empty());
        given(bookRepository.save(any())).willReturn(book);

        // when
        BookDto savedBook = bookService.saveBook(bookDto);

        // then
        assertThat(savedBook.title()).isEqualTo(bookDto.title());
        assertThat(savedBook.author()).isEqualTo(bookDto.author());
        assertThat(savedBook.isbn()).isEqualTo(bookDto.isbn());
    }

    @DisplayName("책 등록 시, 이미 등록된 책이 존재하면 조회 결과를 반환한다.")
    @Test
    void given_bookDtoWithExistIsbn_when_requestSave_then_returnBookDto() {
        // given
        BookDto bookDto = createBookDto();
        Book book = createBook();
        given(bookRepository.findByIsbn(bookDto.isbn())).willReturn(Optional.of(book));

        // when
        BookDto findBook = bookService.saveBook(bookDto);

        // then
        assertThat(findBook.isbn()).isEqualTo(book.getIsbn());
    }

    @DisplayName("책 조회 성공: title")
    @Test
    void given_title_when_requestSearchBook_then_returnBookDto() {
        // given
        String title = "Learning";
        given(bookRepository.findByTitleContains(title)).willReturn(createBookList());

        // when
        List<BookDto> bookList = bookService.getBooksByTitle(title);

        // then
        assertThat(bookList.size()).isEqualTo(4);
        assertThat(bookList.get(0).title()).isEqualTo("LearningSQL1");
        assertThat(bookList.get(1).title()).isEqualTo("LearningSQL2");
        assertThat(bookList.get(2).title()).isEqualTo("LearningSQL3");
        assertThat(bookList.get(3).title()).isEqualTo("LearningSQL4");
    }

    @DisplayName("책 조회 실패: title로 조회 결과 리스트의 사이즈가 0인 경우 예외를 반환한다.")
    @Test
    void given_titleNotExist_when_requestSearchBook_then_returnException() {
        // given
        String title = "Learning";
        given(bookRepository.findByTitleContains(title)).willReturn(List.of());

        // when & then
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getBooksByTitle(title));
    }

    @DisplayName("책 조회 성공: isbn")
    @Test
    void given_isbn_when_requestSearchBook_then_returnBookDto() {
        // given
        String isbn = "9791162244074";
        Book entity = createBook();
        given(bookRepository.findByIsbn(isbn)).willReturn(Optional.of(entity));

        // when
        BookDto book = bookService.getBookByIsbn(isbn);

        // when
        assertThat(book.isbn()).isEqualTo(isbn);
    }

    @DisplayName("책 조회 실패: isbn으로 조회 결과가 없는 경우 예외를 반환한다.")
    @Test
    void given_IsbnNotExist_when_requestSearchBook_then_returnException() {
        // given
        String isbn = "9791162244074";
        given(bookRepository.findByIsbn(isbn)).willReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getBookByIsbn(isbn));
    }


    private static BookDto createBookDto() {
        return BookDto.of("LearningSQL", "앨런 볼리외", "9791162244074", "한빛미디어", LocalDate.of(2021, 03, 30), "http://localhost:8080");
    }


    private static Book createBook() {
        return Book.of("LearningSQL", "앨런 볼리외", "9791162244074", "한빛미디어", LocalDate.of(2021, 03, 30), "http://localhost:8080");
    }

    private static List<Book> createBookList() {
        return List.of(
                Book.of("LearningSQL1", "앨런 볼리외", "9791162244071", "한빛미디어", LocalDate.of(2021, 01, 30), "http://localhost:8080"),
                Book.of("LearningSQL2", "앨런 볼리외", "9791162244072", "한빛미디어", LocalDate.of(2021, 03, 30), "http://localhost:8080"),
                Book.of("LearningSQL3", "앨런 볼리외", "9791162244073", "한빛미디어", LocalDate.of(2021, 04, 30), "http://localhost:8080"),
                Book.of("LearningSQL4", "앨런 볼리외", "9791162244074", "한빛미디어", LocalDate.of(2021, 05, 30), "http://localhost:8080")
        );

    }


}
