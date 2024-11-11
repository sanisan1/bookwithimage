package com.example.gradle_demo.model;

import jakarta.persistence.*;

import com.example.gradle_demo.model.Reader;

import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String author;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;


    // Конструктор с параметрами
    public Book(long id, String name, String author, Reader reader) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    // Конструктор без параметров
    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId() {
        return id; // Убедитесь, что этот метод существует
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(name, book.name) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author);
    }
}
