import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class Main {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/labwork6";
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {

            for (;;) {
                try {
                    System.out.println("1-add, 2-cars for distance, 3-clients for rides, 4 - delete");
                    int choose = scanner.nextInt();

                    switch (choose) {
                        case 1: {

                            System.out.println("1-car, 2-driver, 3-client, 4-ride");
                            int type = scanner.nextInt();

                            switch (type) {
                                case 1: {
                                    // Insert car data into the database
                                    insertCarData(connection, scanner);
                                    System.out.println("Data inserted successfully!");
                                    break;
                                }
                                case 2: {
                                    // Insert driver data into the database
                                    insertDriverData(connection, scanner);
                                    System.out.println("Driver data inserted successfully!");
                                    break;
                                }
                                case 3: {
                                    // Insert client data into the database
                                    insertClientData(connection, scanner);
                                    System.out.println("Client data inserted successfully!");
                                    break;
                                }
                                case 4: {
                                    // Insert ride data into the database
                                    insertRideData(connection, scanner);
                                    System.out.println("Ride data inserted successfully!");
                                    break;
                                }
                                default: {
                                    break;
                                }
                            }
                            break;
                        }

                        case 2: {

                            displayCarsForDistance(connection);

                        }

                        case 3: {
                            displayClientsForRides(connection);
                        }


                        case 4: {
                            System.out.println("1-car, 2-driver, 3-client");
                            int deleteType = scanner.nextInt();

                            switch (deleteType) {
                                case 1:
                                    // Delete car from the database
                                    deleteCar(connection, scanner);
                                    break;
                                case 2:
                                    // Delete driver from the database
                                    deleteDriver(connection, scanner);
                                    break;
                                case 3:
                                    // Delete client from the database
                                    deleteClient(connection,  scanner);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods for inserting data into the database


    private static void insertCarData(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Mark");
        String mark = scanner.next();

        System.out.println("Model");
        String model = scanner.next();

        System.out.println("Fuel Type");
        String fuelType = scanner.next();

        System.out.println("BodyType");
        String bodyType = scanner.next();

        System.out.println("Colour");
        String color = scanner.next();

        System.out.println("Licence Plate");
        String licencePlate = scanner.next();

        System.out.println("Max Passengers");
        int maxPassengers = scanner.nextInt();

        System.out.println("Max Weight");
        int maxWeight = scanner.nextInt();

        String query = "INSERT INTO cars (mark, model, fuelType, bodyType, color, licencePlate, maxPassengers, maxWeight) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mark);
            preparedStatement.setString(2, model);
            preparedStatement.setString(3, fuelType);
            preparedStatement.setString(4, bodyType);
            preparedStatement.setString(5, color);
            preparedStatement.setString(6, licencePlate);
            preparedStatement.setInt(7, maxPassengers);
            preparedStatement.setInt(8, maxWeight);

            preparedStatement.executeUpdate();
        }
    }

    private static void insertDriverData(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter name");
        String name = scanner.next();

        System.out.println("Birthday");
        String birthday = scanner.next();

        System.out.println("PhoneNumber");
        String phoneNumber = scanner.next();

        System.out.println("Drive experience (in years)");
        int driveExperience = scanner.nextInt();

        String query = "INSERT INTO drivers (name, birthday, phoneNumber, driveExperience) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, birthday);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setInt(4, driveExperience);

            preparedStatement.executeUpdate();
        }
    }

    private static void insertClientData(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter name");
        String name = scanner.next();

        System.out.println("Birthday");
        String birthday = scanner.next();

        System.out.println("PhoneNumber");
        String phoneNumber = scanner.next();

        String query = "INSERT INTO clients (name, birthday, phoneNumber) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, birthday);
            preparedStatement.setString(3, phoneNumber);

            preparedStatement.executeUpdate();
        }
    }

    private static void insertRideData(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter Distance");
        float distance = scanner.nextFloat();

        System.out.println("Enter driverID");

        // Display existing drivers
        String driversQuery = "SELECT * FROM drivers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(driversQuery)) {
            while (resultSet.next()) {
                int driverId = resultSet.getInt("driverId");
                String name = resultSet.getString("name");
                System.out.println("Driver ID: " + driverId + ", Name: " + name);
            }
        }

        int driverId = scanner.nextInt();

        System.out.println("Enter clientID");

        // Display existing clients
        String clientsQuery = "SELECT * FROM clients";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(clientsQuery)) {
            while (resultSet.next()) {
                int clientId = resultSet.getInt("clientId");
                String name = resultSet.getString("name");
                System.out.println("Client ID: " + clientId + ", Name: " + name);
            }
        }

        int clientId = scanner.nextInt();

        // Insert data into orders table
        String ordersQuery = "INSERT INTO orders (distance, driverId, clientId) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(ordersQuery)) {
            preparedStatement.setFloat(1, distance);
            preparedStatement.setInt(2, driverId);
            preparedStatement.setInt(3, clientId);

            preparedStatement.executeUpdate();
        }

        // Update driven distance in cars table
        String updateCarsQuery = "UPDATE cars SET driven = driven + ? WHERE driverId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateCarsQuery)) {
            preparedStatement.setFloat(1, distance);
            preparedStatement.setInt(2, driverId);

            preparedStatement.executeUpdate();
        }
    }

    private static void displayCarsForDistance(Connection connection) throws SQLException {
        String query = "SELECT * FROM cars ORDER BY driven DESC";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int carId = resultSet.getInt("carId");
                String licencePlate = resultSet.getString("licencePlate");
                int driven = resultSet.getInt("driven");
                System.out.println("Car ID: " + carId + ", Licence Plate: " + licencePlate + ", Driven: " + driven);
            }
        }
    }

    private static void displayClientsForRides(Connection connection) throws SQLException {
        String query = "SELECT * FROM clients";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int clientId = resultSet.getInt("clientId");
                String name = resultSet.getString("name");
                System.out.println("Client ID: " + clientId + ", Name: " + name);
            }
        }
    }

    private static void deleteOperation(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("1-car, 2-driver, 3-client");
        int type = scanner.nextInt();

        switch (type) {
            case 1:
                deleteCar(connection, scanner);
                break;
            case 2:
                deleteDriver(connection, scanner);
                break;
            case 3:
                deleteClient(connection, scanner);
                break;
        }
    }

    private static void deleteCar(Connection connection, Scanner scanner) throws SQLException {
        displayCars(connection);

        System.out.println("Enter the car ID to remove:");
        int carId = scanner.nextInt();

        String query = "DELETE FROM cars WHERE carId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, carId);

            preparedStatement.executeUpdate();
        }
    }

    private static void deleteDriver(Connection connection, Scanner scanner) throws SQLException {
        displayDrivers(connection);

        System.out.println("Enter the driver ID to remove:");
        int driverId = scanner.nextInt();

        String query = "DELETE FROM drivers WHERE driverId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, driverId);

            preparedStatement.executeUpdate();
        }
    }

    private static void deleteClient(Connection connection, Scanner scanner) throws SQLException {
        displayClients(connection);

        System.out.println("Enter the client ID to remove:");
        int clientId = scanner.nextInt();

        String query = "DELETE FROM clients WHERE clientId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);

            preparedStatement.executeUpdate();
        }
    }

    private static void displayCars(Connection connection) throws SQLException {
        String query = "SELECT * FROM cars";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int carId = resultSet.getInt("carId");
                String licencePlate = resultSet.getString("licencePlate");
                System.out.println("Car ID: " + carId + ", Licence Plate: " + licencePlate);
            }
        }
    }

    private static void displayDrivers(Connection connection) throws SQLException {
        String query = "SELECT * FROM drivers";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int driverId = resultSet.getInt("driverId");
                String name = resultSet.getString("name");
                System.out.println("Driver ID: " + driverId + ", Name: " + name);
            }
        }
    }

    private static void displayClients(Connection connection) throws SQLException {
        String query = "SELECT * FROM clients";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int clientId = resultSet.getInt("clientId");
                String name = resultSet.getString("name");
                System.out.println("Client ID: " + clientId + ", Name: " + name);
            }
        }
    }


}