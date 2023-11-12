import java.util.ArrayList;
import java.util.List;

public class MainFunctional {
    public static String carsPath = "C:\\Users\\itesl\\IdeaProjects\\JavaLabWork2\\src\\cars.txt";
    public static String clientsPath = "C:\\Users\\itesl\\IdeaProjects\\JavaLabWork2\\src\\clients.txt";
    public static String driversPath = "C:\\Users\\itesl\\IdeaProjects\\JavaLabWork2\\src\\drivers.txt";
    public static String ordersPath = "C:\\Users\\itesl\\IdeaProjects\\JavaLabWork2\\src\\orders.txt";
    public static String licencePath = "C:\\Users\\itesl\\IdeaProjects\\JavaLabWork2\\src\\licencePlates.txt";



    public static List<Car> GetMainCarInfo(List<String> strings){

        List<Car> cars = new ArrayList<>();

        for (int i = 0; i< strings.size();i++){
             cars.add(new Car(String.valueOf(strings.get(i)).split(";")));
        }

        return cars;
    }
    public static List<Driver> GetMainDriverInfo(List<String> strings){

        List<Driver> drivers = new ArrayList<>();

        for (int i = 0; i< strings.size();i++){
             drivers.add(new    Driver(String.valueOf(strings.get(i)).split(";")));
        }

        return drivers;
    }
    public static List<Client> GetMainClientInfo(List<String> strings){

        List<Client> clients = new ArrayList<>();

        for (int i = 0; i< strings.size();i++){
             clients.add(new Client(String.valueOf(strings.get(i)).split(";")));
        }

        return clients;
    }
    public static List<Order> GetMainOrderInfo(List<String> strings){

        List<Order> clients = new ArrayList<>();

        for (int i = 0; i< strings.size();i++){
             clients.add(new Order(String.valueOf(strings.get(i)).split(";")));
        }

        return clients;
    }


}


