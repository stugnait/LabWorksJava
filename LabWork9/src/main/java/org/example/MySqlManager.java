package org.example;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlManager implements DatabaseManager {

    private Logger logger = LogManager.getLogger(MySqlManager.class);
    private Connection connection;

    public MySqlManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            logger.debug("Invoking the method 'addCustomer' of class 'MySqlManager'");
            String sqlToInsertCustomer = "insert into customers(fullName, dateOfBirth)" +
                    "values (?, ?)";
            PreparedStatement preparedStatementForCustomers = connection.prepareStatement(sqlToInsertCustomer, Statement.RETURN_GENERATED_KEYS);
            preparedStatementForCustomers.setObject(1, customer.getFullName(), Types.VARCHAR);
            preparedStatementForCustomers.setObject(2, customer.getDateOfBirth(), Types.VARCHAR);
            preparedStatementForCustomers.executeUpdate();
            ResultSet resultSet = preparedStatementForCustomers.getGeneratedKeys();
            Integer latestAddedCustomerId = resultSet.next() ? resultSet.getInt(1) : -1;


            String sqlToReaderTicket = "insert into readertickets(customerId) values (?)";
            PreparedStatement preparedStatementForReaderTickets = connection.prepareStatement(sqlToReaderTicket, Statement.RETURN_GENERATED_KEYS);
            preparedStatementForReaderTickets.setObject(1, latestAddedCustomerId, Types.INTEGER);
            preparedStatementForReaderTickets.executeUpdate();
            ResultSet resultSetForReaderTickets = preparedStatementForReaderTickets.getGeneratedKeys();
            int idOfReaderTicket = resultSetForReaderTickets.next() ? resultSetForReaderTickets.getInt(1) : -1;

            PreparedStatement preparedStatementForUpdatingReaderTicketId = connection.prepareStatement(
                    "update customers set readerTicketId=? where id=?", Statement.RETURN_GENERATED_KEYS);
            preparedStatementForUpdatingReaderTicketId.setObject(1, idOfReaderTicket, Types.INTEGER);
            preparedStatementForUpdatingReaderTicketId.setObject(2, latestAddedCustomerId);
            preparedStatementForUpdatingReaderTicketId.executeUpdate();

            preparedStatementForCustomers.close();
            preparedStatementForReaderTickets.close();
            preparedStatementForUpdatingReaderTicketId.close();
            logger.debug("Finished invoking the method 'addCustomer' of class 'MySqlManager'");
        } catch (SQLException e) {
            logger.error("Error occured when adding a customer");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            logger.debug("Invoking the method 'updateCustomer' of class 'MySqlManager'");
            PreparedStatement preparedStatementForUpdatingCustomer = connection.prepareStatement
                    ("update customers set fullName=?,dateOfBirth=? where id=?");
            preparedStatementForUpdatingCustomer.setObject(1, customer.getFullName(), Types.VARCHAR);
            preparedStatementForUpdatingCustomer.setObject(2, customer.getDateOfBirth(), Types.DATE);
            preparedStatementForUpdatingCustomer.setObject(3, customer.getId(), Types.INTEGER);
            preparedStatementForUpdatingCustomer.executeUpdate();
            preparedStatementForUpdatingCustomer.close();
            logger.debug("Finished invoking the method 'updateCustomer' of class 'MySqlManager'");

        } catch (SQLException e) {
            logger.error("Error occured when updating the person");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {
        try {
            logger.debug("Invoking the method 'deleteCustomer' of class 'MySqlManager'");
            PreparedStatement preparedStatementForDeletingCustomer = connection.prepareStatement
                    (" delete from customers where id=?");
            preparedStatementForDeletingCustomer.setObject(1, customer.getId(), Types.INTEGER);
            preparedStatementForDeletingCustomer.executeUpdate();
            preparedStatementForDeletingCustomer.close();
            logger.debug("Finished invoking the method 'deleteCustomer' of class 'MySqlManager'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getCustomers() {
        try {
            logger.debug("Invoking method 'getCustomers' of class 'MySqlManager'");
            List<Customer> customers = new ArrayList<>();
            Statement statementToGetCustomers = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet customersResultSet = statementToGetCustomers.executeQuery("select * from customers");
            int countOfCustomers = 0;
            if (customersResultSet.last()) {
                countOfCustomers = customersResultSet.getRow();
                customersResultSet.beforeFirst();
            }

            List<ReaderTicket> readerTickets = new ArrayList<>();

            Statement statementToGetReaderTickets = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet readerTicketsResultSet = statementToGetReaderTickets.executeQuery("select * from readerTickets");

            int countOfReaderTickets = 0;
            if (readerTicketsResultSet.last()) {
                countOfReaderTickets = readerTicketsResultSet.getRow();
                readerTicketsResultSet.beforeFirst();
            }

            for (int i = 1; i <= countOfReaderTickets; ++i) {
                if (readerTicketsResultSet.absolute(i)) {
                    ReaderTicket readerTicket = new ReaderTicket();
                    readerTicket.setId(readerTicketsResultSet.getInt(1));
                    readerTickets.add(readerTicket);
                }
            }

            for (int i = 1; i <= countOfCustomers; ++i) {
                if (customersResultSet.absolute(i)) {
                    Integer id = customersResultSet.getInt(1);
                    String fullName = customersResultSet.getString(2);
                    Date dateOfBirth = customersResultSet.getDate(3);
                    Integer readerTicketId = customersResultSet.getInt(4);
                    Customer customer = new Customer(fullName, dateOfBirth);
                    customer.setId(id);
                    customer.setFullName(fullName);
                    customer.setDateOfBirth(dateOfBirth);
                    customers.add(customer);
                    ReaderTicket readerTicket = readerTickets.stream()
                            .filter(readerTicketTemp -> readerTicketTemp.getId() == readerTicketId).toList().get(0);
                    readerTicket.setOwner(customer);
                    customer.setReaderTicket(readerTicket);
                }
            }
            statementToGetCustomers.close();
            statementToGetReaderTickets.close();
            logger.debug("Finished invoking method 'getCustomers' of class 'MySqlManager'");
            return customers;
        } catch (SQLException e) {
            logger.error("Errow executing method 'getCustomers' of class 'MySqlManager'");
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addBook(Book book) {
        try {
            logger.debug("Invoking method 'addBook' of class 'MySqlManager'");
            PreparedStatement preparedStatementForAddingBook = connection.prepareStatement
                    ("insert into availablebooks(type, author, name, dateWritten, dateTaken, dateReturned)" +
                            "values (?, ?, ?, ?, ?, ?)");
            preparedStatementForAddingBook.setObject(1, book.getType(), Types.VARCHAR);
            preparedStatementForAddingBook.setObject(2, book.getAuthor(), Types.VARCHAR);
            preparedStatementForAddingBook.setObject(3, book.getName(), Types.VARCHAR);
            preparedStatementForAddingBook.setObject(4, book.getDateWritten(), Types.DATE);
            preparedStatementForAddingBook.setObject(5, book.getDateTaken(), Types.DATE);
            preparedStatementForAddingBook.setObject(6, book.getDateReturned(), Types.DATE);
            preparedStatementForAddingBook.executeUpdate();
            preparedStatementForAddingBook.close();
            logger.debug("Finished invoking method 'addBook' of class 'MySqlManager'");
        } catch (SQLException e) {
            logger.error("Error occured when executing method 'addBook' of class 'MySqlManager'");
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Book> getBooks() {
        try {
            logger.debug("Invoking method 'getBooks' of class 'MySqlManager'");
            Statement statementForRetrievingAllBooks = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultOfQuery = statementForRetrievingAllBooks.executeQuery("select * from availablebooks");
            int countOfBooks = 0;
            if (resultOfQuery.last()) {
                countOfBooks = resultOfQuery.getRow();
                resultOfQuery.beforeFirst();
            }
            List<Book> books = new ArrayList<>();
            for (int i = 1; i <= countOfBooks; ++i) {
                if (resultOfQuery.absolute(i)) {
                    int id = resultOfQuery.getInt(1);
                    String type = resultOfQuery.getString(2);
                    String author = resultOfQuery.getString(3);
                    String name = resultOfQuery.getString(4);
                    Date dateWritten = resultOfQuery.getDate(5);
                    Book book = new Book(type, author, name, dateWritten);
                    book.setId(id);
                    book.setDateTaken(resultOfQuery.getDate(6));
                    book.setDateReturned(resultOfQuery.getDate(7));
                    books.add(book);
                }
            }
            statementForRetrievingAllBooks.close();
            logger.debug("Finished invoking method 'getBooks' of class 'MySqlManager'");
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBook(Book book) {
        try {
            logger.debug("Invoking the method 'updateBook' of class 'MySqlManager'");
            PreparedStatement preparedStatementForUpdatingBook = connection.prepareStatement
                    ("update availablebooks set type=?,author=?,name=?, dateWritten=?, dateTaken=?, dateReturned=? where id=?");
            preparedStatementForUpdatingBook.setObject(1, book.getType(), Types.VARCHAR);
            preparedStatementForUpdatingBook.setObject(2, book.getAuthor(), Types.VARCHAR);
            preparedStatementForUpdatingBook.setObject(3, book.getName(), Types.VARCHAR);
            preparedStatementForUpdatingBook.setObject(4, book.getDateWritten(), Types.DATE);
            preparedStatementForUpdatingBook.setObject(5, book.getDateTaken(), Types.DATE);
            preparedStatementForUpdatingBook.setObject(6, book.getDateReturned(), Types.DATE);
            preparedStatementForUpdatingBook.setObject(7, book.getId(), Types.INTEGER);
            preparedStatementForUpdatingBook.executeUpdate();
            preparedStatementForUpdatingBook.close();
            logger.debug("Finished invoking the method 'updateBook' of class 'MySqlManager'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBook(Book book) {
        try {
            logger.debug("Invoking the method 'deleteBook' of class 'MySqlManager'");
            PreparedStatement preparedStatementForDeletingBook = connection.prepareStatement(
                    "delete from availablebooks where id=?");
            preparedStatementForDeletingBook.setObject(1, book.getId(), Types.INTEGER);
            preparedStatementForDeletingBook.executeUpdate();
            preparedStatementForDeletingBook.close();
            logger.debug("Finished invoking the method 'deleteBook' of class 'MySqlManager'");
        } catch (SQLException exception) {
            throw new RuntimeException("loh");
        }
    }

    @Override
    public void giveBookToCustomer(Customer customer, Book book) {
        try {
            logger.debug("Invoking the method 'giveBookToCustomer' of class 'MySqlManager'");
            PreparedStatement preparedStatementForGivingBookToCustomer = connection.prepareStatement(
                    "insert into takenbooks(type, author, name, dateWritten, readerTicketId)" +
                            "values (?,?,?,?,?)"
            );
            preparedStatementForGivingBookToCustomer.setObject(1, book.getType(), Types.VARCHAR);
            preparedStatementForGivingBookToCustomer.setObject(2, book.getAuthor(), Types.VARCHAR);
            preparedStatementForGivingBookToCustomer.setObject(3, book.getName(), Types.VARCHAR);
            preparedStatementForGivingBookToCustomer.setObject(4, book.getDateWritten(), Types.DATE);
            preparedStatementForGivingBookToCustomer.setObject(5, customer.getReaderTicket().getId(), Types.INTEGER);
            preparedStatementForGivingBookToCustomer.executeUpdate();

            PreparedStatement statementToDeleteFromAvailableBooks = connection.prepareStatement(
                    "delete from availablebooks where id=?"
            );
            statementToDeleteFromAvailableBooks.setObject(1, book.getId(), Types.INTEGER);
            statementToDeleteFromAvailableBooks.executeUpdate();
            statementToDeleteFromAvailableBooks.close();
            preparedStatementForGivingBookToCustomer.close();
            logger.debug("Finished invoking the method 'giveBookToCustomer' of class 'MySqlManager'");
        } catch (SQLException e) {
            logger.error("Error executing method 'giveBookToCustomer' of class 'MySqlManager'");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void takeBookFromCustomer(Customer customer, Book book) {
        try {
            logger.debug("Invoking the method 'takeBookFromCustomer' of class 'MySqlManager'");
            PreparedStatement preparedStatementToTakeBookFromCustomer = connection.prepareStatement(
                    "delete from takenbooks where id=?"
            );
            preparedStatementToTakeBookFromCustomer.setObject(1, book.getId(), Types.INTEGER);
            preparedStatementToTakeBookFromCustomer.executeUpdate();
            preparedStatementToTakeBookFromCustomer.close();
            logger.debug("Finished invoking the method 'updateBook' of class 'MySqlManager'");
        } catch (SQLException e) {
            logger.error("Error executin the method 'takeBookFromCustomer' of class 'MySqlManager'");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> getBooksThatCustomerHas(Customer customer) {
        try {
            List<Book> books = new ArrayList<>();
            PreparedStatement preparedStatementToRetrieveBooksOfCustomer = connection.prepareStatement(
                    "select * from takenbooks where readerticketid=?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            preparedStatementToRetrieveBooksOfCustomer.setObject(1, customer.getReaderTicket().getId(), Types.INTEGER);
            ResultSet resultSet = preparedStatementToRetrieveBooksOfCustomer.executeQuery();
            int countOfBooks = 0;
            if (resultSet.last()) {
                countOfBooks = resultSet.getRow();
                resultSet.beforeFirst();
            }

            for (int i = 1; i <= countOfBooks; ++i) {
                if (resultSet.absolute(i)) {
                    int id = resultSet.getInt(1);
                    String type = resultSet.getString(2);
                    String author = resultSet.getString(3);
                    String name = resultSet.getString(4);
                    Date dateWritten = resultSet.getDate(5);
                    Book book = new Book(type, author, name, dateWritten);
                    book.setId(id);
                    book.setDateTaken(resultSet.getDate(6));
                    book.setDateReturned(resultSet.getDate(7));
                    books.add(book);
                }
            }
            preparedStatementToRetrieveBooksOfCustomer.close();
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addBookToCustomerHistory(Customer customer, Book book) {
        try {
            PreparedStatement preparedStatementToAddBookToHistory = connection.prepareStatement(
                    "insert into readbooks(type, author, name, dateWritten, readerTicketId)" +
                            "values (?, ?, ?, ?, ?)"
            );
            preparedStatementToAddBookToHistory.setObject(1, book.getType(), Types.VARCHAR);
            preparedStatementToAddBookToHistory.setObject(2, book.getAuthor(), Types.VARCHAR);
            preparedStatementToAddBookToHistory.setObject(3, book.getName(), Types.VARCHAR);
            preparedStatementToAddBookToHistory.setObject(4, book.getDateWritten(), Types.DATE);
            preparedStatementToAddBookToHistory.setObject(5, customer.getReaderTicket().getId(), Types.INTEGER);
            preparedStatementToAddBookToHistory.executeUpdate();
            preparedStatementToAddBookToHistory.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> getBookHistoryOfCustomer(Customer customer) {
        try {
            PreparedStatement preparedStatementToRetrieveBooksReadByCustomer = connection.prepareStatement(
                    "select * from readbooks where readerticketid=?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE
            );
            preparedStatementToRetrieveBooksReadByCustomer.setObject(1, customer.getReaderTicket().getId());
            ResultSet result = preparedStatementToRetrieveBooksReadByCustomer.executeQuery();
            int countOfBooks = 0;
            if (result.last()) {
                countOfBooks = result.getRow();
                result.beforeFirst();
            }
            List<Book> books = new ArrayList<>();
            for (int i = 1; i <= countOfBooks; ++i) {
                if (result.absolute(i)) {
                    int id = result.getInt(1);
                    String type = result.getString(2);
                    String author = result.getString(3);
                    String name = result.getString(4);
                    Date dateWritten = result.getDate(5);
                    Book book = new Book(type, author, name, dateWritten);
                    book.setId(id);
                    books.add(book);
                }
            }
            preparedStatementToRetrieveBooksReadByCustomer.close();
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printDataAboutTables() {
        try {
            Statement statementToGetInfoAboutAvailableBooks = connection.createStatement();
            ResultSet resultSetOfAvailableBooks = statementToGetInfoAboutAvailableBooks
                    .executeQuery("select * from availablebooks");
            ResultSetMetaData metaDataAboutAvailableBooks = resultSetOfAvailableBooks.getMetaData();
            System.out.println("Table: AvailableBooks");
            for(int i = 1; i <= metaDataAboutAvailableBooks.getColumnCount(); ++i) {
                System.out.printf("Column %s - %s\n", i, metaDataAboutAvailableBooks.getColumnName(i));
            }
            statementToGetInfoAboutAvailableBooks.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
