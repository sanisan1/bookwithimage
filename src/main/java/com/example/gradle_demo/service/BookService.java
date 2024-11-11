package com.example.gradle_demo.service;

import com.example.gradle_demo.model.Book; // Убедитесь, что импортируете правильный класс
import com.example.gradle_demo.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@Service
public class BookService {

    private final BookRepository bookRepository;
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book findBook(long id) {
        return bookRepository.findById(id).get();
    }

    public Book editBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    public Collection<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book findByName(String name) {
        return bookRepository.findByNameContainsIgnoreCase(name);
    }

    public Collection<Book> findByAuthor(String author) {
        return bookRepository.findBooksByAuthorContainsIgnoreCase(author);
    }

    public Collection<Book> findByNamePart(String part) {
        return bookRepository.findAllByNameContainsIgnoreCase(part);
    }


}
