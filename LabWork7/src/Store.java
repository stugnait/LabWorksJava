import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Store {
    public static ArrayList<String> availableProducts = new ArrayList<>(List.of(new String[]{"egg", "bread", "bread", "egg", "meat", "cocaine", "tomato", "apple", "slave", "M4A3E2 american medium tank"}));
    private ArrayList<String> goods;
    private volatile boolean isDeliveryMode = true;

    public Store() {
        goods = new ArrayList<>();
    }

    public synchronized String buy() {
        String product = null;

        try {
            while (isDeliveryMode) {
                wait();
            }

            product = goods.get(0);
            goods.remove(0);

            if (goods.isEmpty()) {
                isDeliveryMode = true;
                notify();
            }

            return product;
        } catch (InterruptedException e) {
            System.out.println("The store won't give the " + product + " to the customer");
        } catch (IndexOutOfBoundsException e) {
            deliver();
        }

        return product;
    }

    public synchronized void deliver() {
        try {
            while (!isDeliveryMode) {
                wait();
            }

            System.out.println("The store is empty, delivering the products...");
            Thread.sleep(5000);
            Collections.shuffle(availableProducts);
            goods.addAll(availableProducts);

            isDeliveryMode = false;
            notify();
        } catch (InterruptedException e) {
            System.out.println("\nSomeone very clever thought it was a very good idea to sell his delivery company to a homeless alcoholic for 20 bucks\n");
        }
    }
}
