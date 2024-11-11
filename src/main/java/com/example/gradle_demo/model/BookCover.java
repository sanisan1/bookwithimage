package com.example.gradle_demo.model;

import jakarta.persistence.*;

import com.example.gradle_demo.model.Book;


@Entity
public class BookCover {

    @Id
    @GeneratedValue
    private long id;

    private String filePath;
    private long fileSize;
    private String mediaType;

    @Lob
    private byte[] preview;

    @OneToOne
    private Book book;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getMediaType() { return mediaType; }

    public void setMediaType(String mediaType) { this.mediaType = mediaType; }

    public byte[] getPreview() { return preview; }

    public void setPreview(byte[] preview) { this.preview = preview; }

    public Book getBook() { return book; }

    public void setBook(Book book) { this.book = book; }

    public long getFileSize() { return fileSize; }

    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
}
