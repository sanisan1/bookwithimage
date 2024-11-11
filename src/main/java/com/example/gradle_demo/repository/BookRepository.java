package com.example.gradle_demo.repository;

import com.example.gradle_demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BookRepository extends JpaRepository<Book, Long> {


        Collection<Book> findBooksByAuthorContainsIgnoreCase(String author);

        Collection<Book> findAllByNameContainsIgnoreCase(String part);

        Book findByNameContainsIgnoreCase(String name);

        Collection<Book> findBooksByNameOrAuthor(String name, String author);
}
