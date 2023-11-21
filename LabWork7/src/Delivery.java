public class Delivery implements Runnable{
    private Store store;
    private  volatile boolean isRunning = true;
    public Delivery(Store store) {
        this.store = store;
    }


    public void stopThread() {
        isRunning = false;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted() && isRunning) {
                store.deliver();
                Thread.sleep(3500);
            }
        } catch (InterruptedException e) {}
    }
}
