import java.util.concurrent.Semaphore;

public class Customer implements Runnable{
    public static Semaphore semaphore = new Semaphore(1);
    private String name;
    private Store store;
    private volatile boolean isRunning = true;

    public Customer(String name, Store store) {
        this.name = name;
        this.store = store;
    }

    public void stopThread() {
        isRunning = false;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted() && isRunning) {
                semaphore.acquire();

                String product = store.buy();
                if (product != null) {
                    System.out.println("Customer " + name + " bought " + product);
                }
                else {
                    System.out.println("Customer " + name + " could not buy a product");
                }
                Thread.sleep(1000);

                semaphore.release();
            }
        } catch (InterruptedException e) {
            System.out.println("Customer " + name + " was thrown away from the store");
        } finally {
            semaphore.release();
        }
    }
}
