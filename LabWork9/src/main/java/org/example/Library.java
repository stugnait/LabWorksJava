package org.example;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

@Getter
@Setter
public class Library implements Serializable {
    private ArrayList<Book> availableBooks;
    private ArrayList<Book> takenBooks;
    private ArrayList<Customer> customers;
    private LibraryManager libraryManager;
    private String libraryName;

    public Library(LibraryManager libraryManager, String libraryName) {
        this.libraryName = libraryName;
        this.libraryManager = libraryManager;
        this.availableBooks = new ArrayList<Book>();
        this.takenBooks = new ArrayList<Book>();
        this.customers = new ArrayList<Customer>();
    }

    public Library(){
        customers = new ArrayList<>();
        takenBooks = new ArrayList<>();
        availableBooks = new ArrayList<>();
    }

    public void setAvailableBooks(ArrayList<Book> availableBooks) {
        this.availableBooks = availableBooks;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public void setTakenBooks(ArrayList<Book> takenBooks) {
        this.takenBooks = takenBooks;
    }

    public void setLibraryManager(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
    }

    public void addNewBook(String name, String author, String type, Date dateWritten) {
        Book newBook = new Book(type, author, name, dateWritten);
        availableBooks.add(newBook);
    }

    public void addNewBook(Book book) {
        availableBooks.add(book);
    }

    public void giveBookToCustomer(Customer customer, Book book) {
        availableBooks.remove(book);
        takenBooks.add(book);
        book.setDateTaken(new Date());
        customer.getReaderTicket().addBook(book);
    }

    public void removeBook(Integer indexOfBook) {
        Book bookToDelete = availableBooks.get(indexOfBook);
        availableBooks.remove(bookToDelete);
    }

    public void removeCustomer(Integer indexOfCustomer) {
        Customer customerToDelete = customers.get(indexOfCustomer);
        customers.remove(customerToDelete);
    }

    public void takeBookFromTheCustomer(Customer customer, Book book) {
        customer.getReaderTicket().removeBook(book);
        book.setDateReturned(new Date());
        availableBooks.add(book);
        takenBooks.remove(book);
    }

    public Book get_book(String name, String author) {
        return availableBooks.stream().filter(book -> book.getName().equalsIgnoreCase(name)
                && book.getAuthor().equalsIgnoreCase(author)).findFirst().orElse(null);
    }

    public void registerCustomer(String fullName, Date dateOfBirth) {
        customers.add(new Customer(fullName, (java.sql.Date) dateOfBirth));
    }
    public void removeCustomer(String fullName) {
        Customer actualCustomer = customers.stream().filter(customer -> customer.getFullName().equalsIgnoreCase(fullName)).findFirst().orElse(null);
        if(actualCustomer == null) {
            System.out.println("Such customer does not exist");
        }
        customers.remove(actualCustomer);
    }

    public ArrayList<Customer> get_customers() {
        return customers;
    }

    public ArrayList<Book> get_availableBooks() {
        return availableBooks;
    }

    public String serialize() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        return String.format("""
                Library manager name: %s
                Library manager date of birth: %s
                Library name: %s
                """, libraryManager.getFullName(), dateFormat.format(libraryManager.getDateOfBirth()), libraryName);
    }
}
