package com.example.gradle_demo.service;

import com.example.gradle_demo.model.Book;
import com.example.gradle_demo.model.BookCover;
import com.example.gradle_demo.repository.BookCoverRepository;
import com.example.gradle_demo.service.BookService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
@Transactional
public class BookCoverService {

    @Value("${books.cover.dir.path}")
    private String coversDir;

    private final BookService bookService;
    private final BookCoverRepository bookCoverRepository;

    public BookCoverService(BookService bookService, BookCoverRepository bookCoverRepository) {
        this.bookService = bookService;
        this.bookCoverRepository = bookCoverRepository;
    }

    public void uploadCover(Long bookId, MultipartFile file) throws IOException {
        Book book = bookService.findBook(bookId);

        // Получаем расширение файла
        String fileExtension = getExtension(file.getOriginalFilename());
        if (fileExtension == null) {
            throw new IllegalArgumentException("Файл не имеет расширения.");
        }

        // Путь для сохранения файла
        Path filePath = Path.of(coversDir, bookId + "." + fileExtension);
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        // Сохраняем файл
        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024))
        {
            bis.transferTo(bos);
        }

        // Находим обложку или создаем новую, если ее нет
        BookCover bookCover = findBookCover(bookId);
//        if (bookCover == null) {
//            bookCover = new BookCover();
//        }

        // Устанавливаем свойства обложки
        bookCover.setBook(book);
        bookCover.setFilePath(filePath.toString());
        bookCover.setFileSize(file.getSize());
        bookCover.setMediaType(file.getContentType());
        bookCover.setPreview(generateImagePreview(filePath));

        // Сохраняем обложку
        bookCoverRepository.save(bookCover);
    }

    public BookCover findBookCover(Long bookId) {
        return bookCoverRepository.findByBookId(bookId).orElse(new BookCover());  // Возвращаем найденную обложку или null
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
