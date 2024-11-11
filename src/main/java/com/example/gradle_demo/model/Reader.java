package com.example.gradle_demo.model;

import com.example.gradle_demo.model.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Collection;
import java.util.Objects;


@Entity
public class Reader {
    @Id
    @GeneratedValue
    private long id;

    private int personalNumber;
    private String firstName;
    private String secondName;
    private String middleName;

    @OneToMany(mappedBy = "reader")
    private Collection<Book> books;

    public Reader(int personalNumber, String firstName, String secondName, String middleName, Collection<Book> books) {
        this.personalNumber = personalNumber;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
    }

    public Reader() {

    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", personalNumber=" + personalNumber +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", middleName='" + middleName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return id == reader.id && personalNumber == reader.personalNumber && Objects.equals(firstName, reader.firstName) && Objects.equals(secondName, reader.secondName) && Objects.equals(middleName, reader.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personalNumber, firstName, secondName, middleName);
    }
}
