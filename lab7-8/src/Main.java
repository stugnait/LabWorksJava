import java.util.ArrayList;
import java.util.List;


public class Main {
    public static Store store;
    public static ArrayList<Customer> customers;
    public static Delivery delivery;
    public static ArrayList<Thread> customerThreads;

    public static void main(String[] args) {

        store = new Store();
        customers = new ArrayList<>(List.of(new Customer("Ivan", store),
                new Customer("John", store),
                new Customer("Victor", store),
                new Customer("Kitsya", store),
                new Customer("Killigoy", store),
                new Customer("Harry", store)
        ));

        customerThreads = new ArrayList<>();
        for (Customer customer : customers) {
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customerThread.start();
        }

        delivery = new Delivery(store);
        Thread deliveryThread = new Thread(delivery);
        deliveryThread.setDaemon(true);
        deliveryThread.start();

        try {
            Thread.sleep(600000);

            deliveryThread.interrupt();

            for (Thread thread : customerThreads) {
                thread.interrupt();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cleanup();
    }

    private static void cleanup() {
        for (var customer : customers) {
            customer.stopThread();
        }
        delivery.stopThread();
    }
}