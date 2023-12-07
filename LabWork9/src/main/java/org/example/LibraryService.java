package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Cleanup;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class LibraryService {
    private Logger logger = LogManager.getLogger(LibraryService.class);
    public void doActionBasedOnOption(int option, DatabaseManager databaseManager) {
        Scanner scanner = new Scanner(System.in);
        long startTime = System.currentTimeMillis();
        List<Customer> customers = databaseManager.getCustomers();
        List<Book> books = databaseManager.getBooks();

        switch (option) {
            case 1:
                while(true) {
                    System.out.println("Print persons full name:");
                    String name = scanner.nextLine();
                    System.out.println("Print persons date of birth (yyyy/mm/dd):");
                    String[] birthdayValues = scanner.nextLine().split("/");
                    if(birthdayValues.length < 3) {
                        System.out.println("Input  3 numbers in the given format (yyyy/mm/dd)");
                        continue;
                    }
                    Customer customer = new Customer(name, new Date(Integer.parseInt(birthdayValues[0]) - 1900, Integer.parseInt(birthdayValues[1]) - 1,
                            Integer.parseInt(birthdayValues[2])));
                    databaseManager.addCustomer(customer);
                    break;
                }
                break;
            case 2:
                if(customers.isEmpty()) {
                    System.out.println("There are no customers yet");
                    return;
                }
                for(int i = 0; i < customers.size(); ++i) {
                    System.out.printf("%d - %s\n", customers.get(i).getId(), customers.get(i).getFullName());
                }
                int choice = 0;
                while(true) {
                    System.out.println("Print number of the one you want to delete");
                    try {
                        String choiceStr = scanner.nextLine();
                        choice = Integer.parseInt(choiceStr);
                        int finalChoice1 = choice;
                        if(customers.stream().noneMatch(customer -> customer.getId() == finalChoice1)){
                            System.out.println("No such customer with that id!");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException exception) {
                        System.out.println("Not a number. Try again!");
                        continue;
                    }
                }
                int finalChoice = choice;
                databaseManager.deleteCustomer(customers.stream().filter(customer -> customer.getId() == finalChoice).toList().get(0));
                break;
            case 3:
                while(true) {
                    System.out.println("Input books name");
                    String name = scanner.nextLine();
                    System.out.println("Input books author");
                    String author = scanner.nextLine();
                    System.out.println("Input books type");
                    String type = scanner.nextLine();
                    System.out.println("Print books date of writing (yyyy/mm/dd)");
                    String[] dateWrittenValues;
                    while(true) {
                        dateWrittenValues = scanner.next().split("/");
                        if(dateWrittenValues.length < 3) {
                            System.out.println("Input 3 numbers in the given format");
                            continue;
                        }
                        break;
                    }
                    Book book = new Book(type, author, name, new Date(Integer.parseInt(dateWrittenValues[0]) - 1900, Integer.parseInt(dateWrittenValues[1]) - 1,
                            Integer.parseInt(dateWrittenValues[2])));
                    databaseManager.addBook(book);
                    break;
                }
                break;
            case 4:
                if(customers.isEmpty()) {
                    System.out.println("There are not customers yet");
                    return;
                }
                if(books.isEmpty()) {
                    System.out.println("There are no books yet");
                    return;
                }
                System.out.println("Choose which customer to give a book");
                for(int i = 0; i < customers.size(); ++i) {
                    System.out.printf("%d - %s\n", customers.get(i).getId(), customers.get(i).getFullName());
                }
                while(true) {
                    String strOption = scanner.nextLine();
                    option = Integer.parseInt(strOption);
                    int finalOption = option;
                    if(customers.stream().noneMatch(customer -> customer.getId() == finalOption)){
                        System.out.println("No such customer with that id!");
                        continue;
                    }
                    break;
                }
                int finalOption1 = option;
                Customer chosenCustomer = customers.stream().filter(customer -> customer.getId() == finalOption1).toList().get(0);
                System.out.println("Choose a book to give");
                for(int i = 0; i < books.size(); ++i) {
                    System.out.printf("%s - %s", books.get(i).getId(), books.get(i));
                }
                while(true) {
                    String strOption = scanner.nextLine();
                    option = Integer.parseInt(strOption);
                    int finalOption2 = option;
                    if(books.stream().noneMatch(book -> book.getId() == finalOption2)){
                        System.out.println("No such book with that id");
                        continue;
                    }
                    break;
                }
                int finalOption3 = option;
                Book book = books.stream().filter(book1 -> book1.getId() == finalOption3).toList().get(0);
                databaseManager.giveBookToCustomer(chosenCustomer, book);
                break;
            case 5:
                System.out.println("Choose which customer to take a book from");
                if(customers.isEmpty()) {
                    System.out.println("There are not customers yet");
                    return;
                }
                for(int i = 0; i < customers.size(); ++i) {
                    System.out.printf("%d - %s\n", customers.get(i).getId(), customers.get(i).getFullName());
                }
                while(true) {
                    String strOption = scanner.nextLine();
                    option = Integer.parseInt(strOption);
                    int finalOption4 = option;
                    if(customers.stream().noneMatch(customer -> customer.getId() == finalOption4)) {
                        System.out.println("No such customer with that id!");
                        continue;
                    }
                    break;
                }
                int finalOption5 = option;
                chosenCustomer = customers.stream().filter(customer -> customer.getId() == finalOption5).toList().get(0);
                books = databaseManager.getBooksThatCustomerHas(chosenCustomer);
                if(books.isEmpty()) {
                    System.out.println("There are no books taken by this customer");
                    return;
                }
                System.out.println("Choose a book to take");
                for(int i = 0; i < books.size(); ++i) {
                    System.out.printf("%s - %s", books.get(i).getId(), books.get(i));
                }
                while(true) {
                    String strOption = scanner.nextLine();
                    option = Integer.parseInt(strOption);
                    int finalOption8 = option;
                    if(books.stream().noneMatch(book1 -> book1.getId() == finalOption8)) {
                        System.out.println("There is no such book with that id!");
                        continue;
                    }
                    break;
                }
                int finalOption9 = option;
                book = books.stream().filter(book1 -> book1.getId() == finalOption9).toList().get(0);
                databaseManager.takeBookFromCustomer(chosenCustomer, book);
                databaseManager.addBookToCustomerHistory(chosenCustomer, book);
                databaseManager.addBook(book);
                break;
            case 6:
                if(customers.isEmpty()) {
                    System.out.println("There are no customers yet");
                    return;
                }
                for (Customer customer : customers) {
                    System.out.printf("%s\n", customer.getFullName());
                }
                break;
            case 7:
                if(books.isEmpty()) {
                    System.out.println("There are no books yet");
                }
                for (Book book1 : books) {
                    System.out.printf("%s\n", book1.toString());
                }
                break;
            case 8:
                System.out.println("Choose th customer to see theirs book history");
                if(customers.isEmpty()) {
                    System.out.println("There are no customers yet");
                    return;
                }
                for(int i = 0; i < customers.size(); ++i) {
                    System.out.printf("%d - %s\n", customers.get(i).getId(), customers.get(i).getFullName());
                }
                while(true) {
                    String strOption = scanner.nextLine();
                    option = Integer.parseInt(strOption);
                    int finalOption6 = option;
                    if(customers.stream().noneMatch(customer -> customer.getId() == finalOption6)) {
                        System.out.println("No such customer with such id!");
                        continue;
                    }
                    break;
                }
                int finalOption7 = option;
                Customer customerToWatchHistory = customers.stream().filter(customer -> customer.getId() == finalOption7).toList().get(0);
                List<Book> booksReadByCustomer = databaseManager.getBookHistoryOfCustomer(customerToWatchHistory);
                for(Book book1 : booksReadByCustomer) {
                    System.out.printf("Name: %s, Author: %s, Type: %s, Date Written: %s\n",
                            book1.getName(), book1.getAuthor(),book1.getType(), book1.getDateWritten());
                }
                break;
            case 9:
                if(customers.isEmpty()) {
                    System.out.println("There are no customers yet");
                    return;
                }
                for(int i = 0; i < customers.size(); ++i) {
                    for(int j = i + 1; j < customers.size() - 1; ++j) {
                        if(databaseManager.getBookHistoryOfCustomer(customers.get(i)).size() < databaseManager.getBookHistoryOfCustomer(customers.get(j)).size()) {
                            Customer temp = customers.get(i);
                            customers.set(j, customers.get(i));
                            customers.set(i, temp);
                        }
                    }
                }
                System.out.println("Sorted customers based on the read books");
                for (Customer customer : customers) {
                    System.out.println(customer.getFullName() + ". Books read: " + databaseManager.getBookHistoryOfCustomer(customer).size());
                }
                break;
            case 10:
                while(true) {
                    System.out.println("Choose options for sorting");
                    System.out.println("1 - Author");
                    System.out.println("2 - Type");
                    System.out.println("3 - Date Of Publishing");
                    String strOption = scanner.nextLine();
                    option = Integer.parseInt(strOption);
                    if(option < 0 || option > 3) {
                        System.out.println("Wrong input. Try again!");
                        continue;
                    }
                    switch (option) {
                        case 1:
                            for(int i = 0; i < books.size(); ++i) {
                                for(int j = 0; j < books.size() - 1; ++j) {
                                    if(books.get(j).getAuthor().charAt(0) > books.get(j + 1).getAuthor().charAt(0)) {
                                        Book temp = books.get(j);
                                        books.set(j, books.get(j + 1));
                                        books.set(j + 1, temp);
                                    }
                                }
                            };
                            System.out.println("Books were sorted by author");
                            break;
                        case 2:
                            for(int i = 0; i < books.size(); ++i) {
                                for(int j = 0; j < books.size() - 1; ++j) {
                                    if(books.get(j).getType().charAt(0) > books.get(j + 1).getType().charAt(0)) {
                                        Book temp = books.get(j);
                                        books.set(j, books.get(j + 1));
                                        books.set(j + 1, temp);
                                    }
                                }
                            };
                            System.out.println("Books were sorted by type");
                            break;
                        case 3:
                            for(int i = 0; i < books.size(); ++i) {
                                for(int j = 0; j < books.size() - 1; ++j) {
                                    if(books.get(j).getDateWritten().compareTo(books.get(j + 1).getDateWritten()) > 0) {
                                        Book temp = books.get(j);
                                        books.set(j, books.get(j + 1));
                                        books.set(j + 1, temp);
                                    }
                                }
                            };
                            System.out.println("Books were sorted by date of publishing");
                            break;
                    }
                    break;
                }
                break;
            case 11:
                if(customers.isEmpty()) {
                    System.out.println("There are no customers yet");
                    return;
                }
                Customer customerWithTheMostReadBooks = customers.get(0);
                for(int i = 0; i < customers.size(); i++) {
                    if(databaseManager.getBookHistoryOfCustomer(customers.get(i)).size() > databaseManager.getBookHistoryOfCustomer(customerWithTheMostReadBooks).size()) {
                        customerWithTheMostReadBooks = customers.get(i);
                    }
                }
                System.out.printf("Customer with the most read books: %s. Books read: %d\n", customerWithTheMostReadBooks.getFullName(), databaseManager.getBookHistoryOfCustomer(customerWithTheMostReadBooks).size());
                break;
            case 12:
                if(books.isEmpty()) {
                    System.out.println("There are not available books yet");
                    return;
                }
                for(int i = 0; i < books.size(); ++i) {
                    System.out.printf("%s - %s\n", books.get(i).getId(), books.get(i).toString());
                }
                int bookId;
                while(true) {
                    System.out.println("Choose book:");
                    String bookIdStr = scanner.nextLine();
                    bookId = Integer.parseInt(bookIdStr);
                    int finalBookId = bookId;
                    if(books.stream().noneMatch(book1 -> book1.getId() == finalBookId)) {
                        System.out.println("No such book with that id");
                        continue;
                    }
                    break;
                }

                System.out.println("Print new name");
                String newName = scanner.nextLine();
                System.out.println("Print new author");
                String newAuthor = scanner.nextLine();
                System.out.println("Print new type");
                String newType = scanner.nextLine();
                System.out.println("Print new date of being written (yyyy/mm/dd)");
                String[] newDateValues = scanner.next().split("/");
                int finalBookId1 = bookId;
                Book bookToChange = books.stream().filter(book1 -> book1.getId() == finalBookId1).toList().get(0);
                bookToChange.setAuthor(newAuthor);
                bookToChange.setName(newName);
                bookToChange.setType(newType);
                bookToChange.setDateWritten(new Date(Integer.parseInt(newDateValues[0]) - 1900,
                        Integer.parseInt(newDateValues[1]) - 1, Integer.parseInt(newDateValues[2])));
                databaseManager.updateBook(bookToChange);
                break;
            case 13:
                if(customers.isEmpty()) {
                    System.out.println("There are no customers yet");
                    return;
                }
                for(int i = 0; i < customers.size(); ++i) {
                    System.out.printf("%s - %s\n", customers.get(i).getId(), customers.get(i).getFullName());
                }
                int customerId;

                while(true){
                    customerId = Integer.parseInt(scanner.nextLine());
                    int finalCustomerId1 = customerId;
                    if(customers.stream().noneMatch(customer -> customer.getId() == finalCustomerId1)) {
                        System.out.println("No such customer with that id!");
                        continue;
                    }
                    break;
                }

                System.out.println("Print new full name");
                String newCustomerName = scanner.nextLine();
                System.out.println("Print new date of birth");
                String some = scanner.next();
                List<String> dateValuesAsString = Arrays.stream(some.split("/")).toList();
                List<Integer> newDateOfBirthValues = dateValuesAsString.stream().map(Integer::parseInt).collect(Collectors.toList());
                Date newDateOfBirth = new Date(newDateOfBirthValues.get(0) - 1900, newDateOfBirthValues.get(1) - 1, newDateOfBirthValues.get(2));
                int finalCustomerId = customerId;
                Customer customerToUpdate = customers.stream().filter(customer -> customer.getId() == finalCustomerId).toList().get(0);
                customerToUpdate.setDateOfBirth(newDateOfBirth);
                customerToUpdate.setFullName(newCustomerName);
                databaseManager.updateCustomer(customerToUpdate);
                break;
            case 14:
                if(books.isEmpty()) {
                    System.out.println("There are no books yet");
                    return;
                }
                while(true){
                    try {
                        for(int i = 0; i < books.size(); ++i){
                            System.out.printf("%s - %s\n", books.get(i).getId(), books.get(i).toString());
                        }
                        Integer idOfBookToDelete = scanner.nextInt();
                        if(books.stream().noneMatch(book1 -> book1.getId() == idOfBookToDelete)){
                            System.out.println("No such book with that id");
                            continue;
                        }
                        Book bookToDelete = books.stream().filter(book1 -> book1.getId() == idOfBookToDelete).toList().get(0);
                        databaseManager.deleteBook(bookToDelete);
                        break;
                    } catch (InputMismatchException inputMismatchException) {
                        System.out.println("Not a number!");
                    }
                }
                break;
            case 15:
                databaseManager.printDataAboutTables();
                break;
            case 16:
                System.out.println("Input path to file to write");
                String pathToFile = scanner.nextLine();
                System.out.println("Choose what info to give as JSON");
                System.out.println("1 - Customers");
                System.out.println("2 - Available Books");
                System.out.println("3 - Books read by specific person");
                ObjectMapper objectMapper = new ObjectMapper();
                String[] dataToWrite = null;
               while (true){
                   try {
                       String optionStr = scanner.nextLine();
                       option = Integer.parseInt(optionStr);
                        switch (option){
                            case 1:
                                String json = objectMapper.writeValueAsString(customers);
                                dataToWrite = json.split("\\},\\s*\\{");
                                break;
                            case 2:
                                json = objectMapper.writeValueAsString(books);
                                dataToWrite = json.split("\\},\\s*\\{");
                                break;
                            case 3:
                                for(Customer x : customers){
                                    System.out.println(String.format("%s - %s", x.getId(), x.getFullName()));
                                }
                                System.out.println("Choose the id of customer to get info about read books");
                                String customerIdAsString = scanner.nextLine();
                                customerId = Integer.parseInt(customerIdAsString);
                                int finalCustomerId2 = customerId;
                                Customer customerToTakeInfo = customers.stream().filter(customer -> customer.getId() == finalCustomerId2)
                                        .toList()
                                        .get(0);
                                List<Book> bookHistoryOfCustomer = databaseManager.getBookHistoryOfCustomer(customerToTakeInfo);
                                json = objectMapper.writeValueAsString(bookHistoryOfCustomer);
                                dataToWrite = json.split("\\},\\s*\\{");
                                break;
                            default:
                                System.out.println("No such option. Try again");
                                continue;
                        }
                        @Cleanup FileWriter fileWriter = new FileWriter(pathToFile);
                        for(String x : dataToWrite){
                            fileWriter.write(x + "\n");
                        }
                        break;
                   } catch (NumberFormatException ex){
                       System.out.println("Not a number. Try again");
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time for operation: " + (endTime - startTime) + "ms.");
        logger.info("Invocation of method ended successfully");
    }
}
