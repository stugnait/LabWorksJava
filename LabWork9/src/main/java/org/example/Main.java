package org.example;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static DatabaseManager databaseManager;
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Application started working");
        BasicConfigurator.configure();
        Scanner scanner = new Scanner(System.in);
        String filePath = System.getProperty("user.dir") + "/src/main/resources/config.properties";
        Properties properties = getProperties(filePath);
        LibraryService libraryService = new LibraryService();
        Connection connection;
        try {
            connection = MySqlConnector.getConnection(properties);
        } catch (SQLException e) {
            logger.error("Error getting DB connection");
            throw new RuntimeException(e);
        }
        databaseManager = new MySqlManager(connection);
        while(true) {
            System.out.println("Choose action");
            System.out.println("1 - Register customer");
            System.out.println("2 - Remove customer");
            System.out.println("3 - Add a new book to collection");
            System.out.println("4 - Give book to customer");
            System.out.println("5 - Take book from customer");
            System.out.println("6 - Show all customers");
            System.out.println("7 - Print all available books");
            System.out.println("8 - Get books history of a user");
            System.out.println("9 - Sort customers by the number of read books");
            System.out.println("10 - Sort books by Author | Type | Date Of Publishing");
            System.out.println("11 - Show customer with the most read books");
            System.out.println("12 - Edit book");
            System.out.println("13 - Edit customer");
            System.out.println("14 - Delete book");
            System.out.println("15 - Get info about tables (for admin only)");
            System.out.println("16 - Retrieve values as JSON");
            System.out.println("q - Exit");
            int option;
            try {
                String optionStr = scanner.next();
                if(optionStr.equals("q")) {
                    break;
                }
                option = Integer.parseInt(optionStr);
                if(option < 1 || option > 16) {
                    System.out.println("Wrong input, try again!");
                    continue;
                }
            } catch (NumberFormatException exception) {
                System.out.println("Not a number. Try again");
                continue;
            }
            logger.info("Option has been chosen. Invoking the action.");
            libraryService.doActionBasedOnOption(option, databaseManager);
        }
    }

    private static Properties getProperties(String configFilePath){
        logger.info("Started process of retrieving properties for DB connection");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("Retrieved properties for DB connection successfully");
        return properties;
    }
}