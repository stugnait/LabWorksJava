import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
                new Customer("Oleg", store),
                new Customer("Daryna", store),
                new Customer("Vitalina", store),
                new Customer("Vitaliy", store),
                new Customer("Ira", store),
                new Customer("Paul", store),
                new Customer("Jack", store),
                new Customer("Mike", store),
                new Customer("Andrew", store),
                new Customer("Tanya", store),
                new Customer("Oleksandr", store)
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
            Thread.sleep(60000);

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