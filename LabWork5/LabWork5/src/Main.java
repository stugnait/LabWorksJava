import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    for (; ; ) {
        try {
            System.out.println("1-add, 2-cars for distance, 3-clients for rides, 4 - delete");
            int choose = scanner.nextInt();

            switch (choose) {
                case 1: {

                    System.out.println("1-car, 2-driver, 3-client, 4-ride");
                    choose = scanner.nextInt();

                    switch (choose) {
                        case 1: {

                            List<String> cars = Files.readAllLines(Paths.get(MainFunctional.carsPath));

                            String[] temporaryArray = new String[11];

                            Arrays.fill(temporaryArray, "0");

                            temporaryArray[0] = String.valueOf(cars.size());

                            System.out.println("Mark");
                            temporaryArray[1] = String.valueOf(scanner.next());

                            System.out.println("Model");
                            temporaryArray[2] = String.valueOf(scanner.next());

                            System.out.println("Fuel Type");
                            temporaryArray[3] = String.valueOf(scanner.next());

                            System.out.println("BodyType");
                            temporaryArray[4] = String.valueOf(scanner.next());

                            System.out.println("Colour");
                            temporaryArray[5] = String.valueOf(scanner.next());

                            System.out.println("Licence Plate");
                            temporaryArray[6] = String.valueOf(scanner.next());

                            System.out.println("Max Passengers");
                            temporaryArray[7] = String.valueOf(scanner.next());

                            System.out.println("Max Weight");
                            temporaryArray[8] = String.valueOf(scanner.next());

                            Car car = new Car(temporaryArray);

                            Files.writeString(Paths.get(MainFunctional.carsPath), Files.readString(Paths.get(MainFunctional.carsPath)) + car.ToSaveFormat());
                            Files.writeString(Paths.get(MainFunctional.licencePath), Files.readString(Paths.get(MainFunctional.licencePath)) + car.ToMainSaveFormat());

                            break;
                        }
                        case 2: {

                            List<String> drivers = Files.readAllLines(Paths.get(MainFunctional.driversPath));

                            String[] temporaryArray = new String[6];
                            Arrays.fill(temporaryArray, "-1");

                            System.out.println("Enter name");
                            temporaryArray[3] = String.valueOf(scanner.next());

                            System.out.println("Birthday");
                            temporaryArray[4] = String.valueOf(scanner.next());

                            System.out.println("PhoneNumber");
                            temporaryArray[5] = String.valueOf(scanner.next());

                            System.out.println("Drive expierence (in years)");
                            temporaryArray[2] = String.valueOf(scanner.next());

                            boolean flag = false;
                            int pick = 0;

                            List<String> strings = Files.readAllLines(Paths.get(MainFunctional.licencePath));

                            List<Car> cars = MainFunctional.GetMainCarInfo(strings);

                            temporaryArray[0] = String.valueOf(drivers.size());

                            if (!cars.isEmpty()) {

                                while (!flag) {

                                    System.out.println("Which car to use? (id)");

                                    for (Car car : cars) {
                                        car.MainInfoOutput();
                                    }

                                    temporaryArray[1] = String.valueOf(scanner.next());

                                    for (int i = 0; i < cars.size(); i++) {
                                        if (cars.get(i).id == Integer.parseInt(temporaryArray[1])) {
                                            flag = true;
                                            pick = i;
                                        }
                                    }
                                }
                                List<Car> cars1 = MainFunctional.GetMainCarInfo(Files.readAllLines(Paths.get(MainFunctional.carsPath)));

                                cars1.get(pick).driverID = Integer.parseInt(temporaryArray[1]);

                                Files.writeString(Paths.get(MainFunctional.carsPath), "");


                                for (int k = 0; k < cars1.size(); k++) {

                                    Files.writeString(Paths.get(MainFunctional.carsPath), Files.readString(Paths.get(MainFunctional.carsPath)) + cars1.get(k).ToSaveFormat());
                                }


                            } else {
                                System.out.println("No cars yet");
                            }


                            Driver driver = new Driver(temporaryArray);

                            Files.writeString(Paths.get(MainFunctional.driversPath), Files.readString(Paths.get(MainFunctional.driversPath)) + driver.ToSaveFormat());
                            break;
                        }
                        case 3: {

                            List<String> clients = Files.readAllLines(Paths.get(MainFunctional.clientsPath));

                            String temporaryArray[] = new String[4];

                            System.out.println("Enter name");
                            temporaryArray[1] = String.valueOf(scanner.next());

                            System.out.println("Birthday");
                            temporaryArray[2] = String.valueOf(scanner.next());

                            System.out.println("PhoneNumber");
                            temporaryArray[3] = String.valueOf(scanner.next());

                            temporaryArray[0] = String.valueOf(clients.size());

                            Client client = new Client(temporaryArray);

                            Files.writeString(Paths.get(MainFunctional.clientsPath), Files.readString(Paths.get(MainFunctional.clientsPath)) + client.ToSaveFormat());
                            break;
                        }
                        case 4: {
                            List<String> clients = Files.readAllLines(Paths.get(MainFunctional.ordersPath));

                            String temporaryArray[] = new String[4];

                            System.out.println("Enter Distance");
                            temporaryArray[0] = String.valueOf(scanner.next());


                            //driver

                            System.out.println("Enter driverID");

                            List<String> strings = Files.readAllLines(Paths.get(MainFunctional.driversPath));

                            List<Driver> drivers = MainFunctional.GetMainDriverInfo(strings);
                            for (Driver driver : drivers) {
                                System.out.println(driver.MainInfoOutput());
                            }

                            temporaryArray[1] = String.valueOf(scanner.next());


                            while (drivers.size() < Integer.parseInt(temporaryArray[1])) {
                                System.out.printf("!Wrong!");
                                temporaryArray[1] = String.valueOf(scanner.next());
                            }


                            //client

                            System.out.println("Enter clientID");

                            List<String> strings1 = Files.readAllLines(Paths.get(MainFunctional.clientsPath));

                            List<Client> clients1 = MainFunctional.GetMainClientInfo(strings1);

                            for (Client client : clients1) {
                                System.out.println(client.MainInfoOutput());
                            }

                            temporaryArray[2] = String.valueOf(scanner.next());

                            while (drivers.size() < Integer.parseInt(temporaryArray[1])) {
                                System.out.printf("!Wrong!");
                                temporaryArray[2] = String.valueOf(scanner.next());
                            }

                            temporaryArray[3] = String.valueOf(clients.size());

                            Order order = new Order(temporaryArray);

                            Files.writeString(Paths.get(MainFunctional.ordersPath), Files.readString(Paths.get(MainFunctional.ordersPath)) + order.ToSaveFormat());


                            //додавання пройдної дистанції

                            List<String> strings2 = Files.readAllLines(Paths.get(MainFunctional.carsPath));

                            List<Car> cars = MainFunctional.GetMainCarInfo(strings2);


                            for (int i = 0; i < cars.size(); i++) {
                                if (cars.get(i).driverID == Integer.parseInt(temporaryArray[1])) {
                                    cars.get(i).driven = cars.get(i).driven + Integer.parseInt(temporaryArray[0]);
                                }
                            }

                            Files.writeString(Paths.get(MainFunctional.carsPath), "");

                            for (int k = 0; k < cars.size(); k++) {

                                Files.writeString(Paths.get(MainFunctional.carsPath), Files.readString(Paths.get(MainFunctional.carsPath)) + cars.get(k).ToSaveFormat());
                            }

                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    break;

                }
                case 2: {

                    List<String> strings2 = Files.readAllLines(Paths.get(MainFunctional.carsPath));

                    List<Car> cars = MainFunctional.GetMainCarInfo(strings2);
                    cars.sort(null);

                    for (Car car : cars) {
                        car.BigInfoOutput();
                    }

                    break;

                }
                case 3: {

                    List<String> strings2 = Files.readAllLines(Paths.get(MainFunctional.ordersPath));

                    List<Order> orders = MainFunctional.GetMainOrderInfo(strings2);

                    List<String> strings = Files.readAllLines(Paths.get(MainFunctional.clientsPath));

                    List<Client> clients = MainFunctional.GetMainClientInfo(strings);

                    Integer temporaryArray[] = new Integer[clients.size()];

                    Arrays.fill(temporaryArray, 0);


                    //додавати на місце ІД кліенту ++
                    for (Order order : orders) {
                        temporaryArray[order.clientID]++;
                    }

                    for (int i = 0; i < clients.size(); i++) {
                        System.out.println(clients.get(i).MainInfoOutput() + temporaryArray[i]);
                    }
                }
                case 4:


                    System.out.println("1-car, 2-driver, 3-client");
                    choose = scanner.nextInt();
                    int i = 0;
                    switch (choose) {
                        case 1:

                            List<String> carString = Files.readAllLines(Paths.get(MainFunctional.carsPath));

                            List<Car> cars = MainFunctional.GetMainCarInfo(carString);

                            for (Car car: cars) {

                                System.out.println("index "+i);
                                car.MainInfoOutput();
                                i++;
                            }

                            System.out.println("remove:");
                            choose = scanner.nextInt();

                            cars.remove(choose);
                            StringBuilder output = new StringBuilder();
                            for (Car car: cars){
                                output.append(car.ToSaveFormat());
                            }

                            Files.writeString(Paths.get(MainFunctional.carsPath), output);

                            break;
                        case 2:
                            List<String> driverString = Files.readAllLines(Paths.get(MainFunctional.driversPath));

                            List<Driver> drivers = MainFunctional.GetMainDriverInfo(driverString);

                            for (Driver driver: drivers) {

                                System.out.println("index "+i);
                                driver.MainInfoOutput();
                                i++;
                            }

                            System.out.println("remove:");
                            choose = scanner.nextInt();

                            drivers.remove(choose);

                            StringBuilder driverOutput = new StringBuilder();
                            for (Driver driver: drivers){
                                driverOutput.append(driver.ToSaveFormat());
                            }

                            Files.writeString(Paths.get(MainFunctional.carsPath), driverOutput);

                            break;
                        case 3:
                            List<String> clientString = Files.readAllLines(Paths.get(MainFunctional.clientsPath));

                            List<Client> clients = MainFunctional.GetMainClientInfo(clientString);

                            for (Client client: clients) {

                                System.out.println("index "+i);
                                client.MainInfoOutput();
                                i++;
                            }

                            System.out.println("remove:");
                            choose = scanner.nextInt();

                            clients.remove(choose);

                            StringBuilder clientOutput = new StringBuilder();

                            for (Client client: clients){
                                driverOutput.append(client.ToSaveFormat());
                            }

                            Files.writeString(Paths.get(MainFunctional.carsPath), clientOutput);

                            break;
                    }
            }

        } catch (IOException e) {
            System.out.print("Sho ti vvodysh durachok");
            e.printStackTrace();
        }

    }
}
}