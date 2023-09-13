package com.hyunbenny.bookroom.service;

import com.hyunbenny.bookroom.dto.BookDto;
import com.hyunbenny.bookroom.entity.Book;
import com.hyunbenny.bookroom.exception.book.BookAlreadyExistException;
import com.hyunbenny.bookroom.exception.book.BookNotFoundException;
import com.hyunbenny.bookroom.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BookDto saveBook(BookDto bookDto) {
        Book book = bookRepository.findByIsbn(bookDto.isbn()).orElse(bookRepository.save(bookDto.toEntity()));

//        bookRepository.findByIsbn(bookDto.isbn()).ifPresent(b -> {
//            throw new BookAlreadyExistException();
//        });
//        Book book = bookRepository.save(bookDto.toEntity());

        return BookDto.from(book);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByTitle(String title) {
        List<Book> entities = bookRepository.findByTitleContains(title);
        if(entities.size() < 1) throw new BookNotFoundException();

        List<BookDto> bookDtoList = new ArrayList<>();
        entities.stream().forEach(b -> bookDtoList.add(BookDto.from(b)));
        return bookDtoList;
    }

    @Transactional(readOnly = true)
    public BookDto getBookByIsbn(String isbn) {
        Book entity = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException());
        return BookDto.from(entity);
    }
}
