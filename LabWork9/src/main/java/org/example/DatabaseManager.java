package org.example;

import java.util.List;

public interface DatabaseManager {
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
    List<Customer> getCustomers();

    void addBook(Book book);
    List<Book> getBooks();
    void updateBook(Book book);
    void deleteBook(Book book);

    void giveBookToCustomer(Customer customer, Book book);
    void takeBookFromCustomer(Customer customer, Book book);
    List<Book> getBooksThatCustomerHas(Customer customer);
    void addBookToCustomerHistory(Customer customer, Book book);
    List<Book> getBookHistoryOfCustomer(Customer customer);
    void printDataAboutTables();
}
