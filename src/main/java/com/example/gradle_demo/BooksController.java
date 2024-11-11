package com.example.gradle_demo;

import com.example.gradle_demo.model.Book;
import com.example.gradle_demo.model.BookCover;
import com.example.gradle_demo.service.BookCoverService;
import com.example.gradle_demo.service.BookService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("books")
public class BooksController {
    private final BookService bookService;
    private final BookCoverService bookCoverService;  // Добавьте BookCoverService

    // Конструктор для инъекции зависимостей
    @Autowired
    public BooksController(BookService bookService, BookCoverService bookCoverService) {
        this.bookService = bookService;
        this.bookCoverService = bookCoverService;  // Инициализация BookCoverService
    }

    @GetMapping
    public ResponseEntity findBooks(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String author,
                                    @RequestParam(required = false) String namePart) {
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(bookService.findByName(name));
        }

        if (author != null && !author.isEmpty()) {
            return ResponseEntity.ok(bookService.findByAuthor(author));
        }

        if (namePart != null && !namePart.isEmpty()) {
            return ResponseEntity.ok(bookService.findByNamePart(namePart));
        }

        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("{id}") //localhost:8080/books/1
    public ResponseEntity<Book> getBookInfo(@PathVariable Long id) {
        Book book = bookService.findBook(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping //localhost:8080/books
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping //localhost:8080/books
    public ResponseEntity<Book> editBook(@RequestBody Book book) {
        Book foundBook = bookService.editBook(book);
        if (foundBook == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(foundBook);
    }

    @DeleteMapping("{id}") //localhost:8080/books/1
    public ResponseEntity deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCover(@PathVariable Long id, @RequestParam MultipartFile cover) throws IOException {
        if (cover.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }

        bookCoverService.uploadCover(id, cover);  // Используйте bookCoverService
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/cover/preview")
    public ResponseEntity<byte[]> downloadCover(@PathVariable Long id) {
        BookCover bookCover = bookCoverService.findBookCover(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(bookCover.getMediaType()));
        headers.setContentLength(bookCover.getPreview().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bookCover.getPreview());
    }

    @GetMapping(value = "/{id}/cover")
    public void downloadCover(@PathVariable Long id, HttpServletResponse response) throws IOException {
        BookCover bookCover = bookCoverService.findBookCover(id);

        Path path = Path.of(bookCover.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(bookCover.getMediaType());
            response.setContentLength((int) bookCover.getFileSize());
            is.transferTo(os);
        }
    }
}
