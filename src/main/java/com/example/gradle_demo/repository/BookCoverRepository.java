package com.example.gradle_demo.repository;

import com.example.gradle_demo.model.BookCover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookCoverRepository extends JpaRepository<BookCover, Long> {
    Optional<BookCover> findByBookId(Long bookID);
}
