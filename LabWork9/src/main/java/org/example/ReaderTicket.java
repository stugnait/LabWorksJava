package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

@Setter
@Getter
public class ReaderTicket implements Serializable {
    @JsonIgnore
    private int id;
    @JsonIgnore
    private Customer owner;
    private ArrayList<Book> booksTaken;
    private ArrayList<Book> booksRead;
    @JsonIgnore
    private StringBuilder bookHistory;
    public ReaderTicket() {
        bookHistory = new StringBuilder();
    }
    public ReaderTicket(Customer owner) {
        this.owner = owner;
        booksTaken = new ArrayList<Book>();
        booksRead = new ArrayList<Book>();
        bookHistory = new StringBuilder();
    }

    public void addBook(Book book) {
        booksTaken.add(book);
    }

    public void removeBook(Book book) {
        booksTaken.remove(book);
        booksRead.add(book);
        book.setDateReturned(new Date());
        bookHistory.append(String.format("%s. Book taken on: %s. Book returned on: %s", book.toString(),
                book.getDateTaken().getTime(), book.getDateReturned().getTime()));
    }
}
