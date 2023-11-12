class Restaurant {
    private static int totalClients = 0;
    private static int totalServedClients = 0;
    private static Object lock = new Object();

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Thread client = new Thread(new Client(i));
            client.start();
        }

        Thread waiter = new Thread(new Waiter());
        waiter.start();
    }

    static class Client implements Runnable {
        private int id;

        public Client(int id) {
            this.id = id;
        }

        public void run() {
            try {
                Thread.sleep(1000); // Прихід клієнта в ресторан
                System.out.println("Client " + id + " entered the restaurant.");

                synchronized (lock) {
                    while (totalClients - totalServedClients >= 3) {
                        lock.wait(); // Очікування на вільне місце для замовлення
                    }

                    totalClients++;
                    System.out.println("Client " + id + " placed an order.");
                }

                Thread.sleep(3000); // Очікування на обслуговування

                synchronized (lock) {
                    totalServedClients++;
                    System.out.println("Client " + id + " was served and paid.");
                    if (totalServedClients == totalClients) {
                        lock.notify(); // Сповіщення офіціанту про закінчення роботи
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Waiter implements Runnable {
        public void run() {
            try {
                synchronized (lock) {
                    while (totalServedClients < totalClients) {
                        lock.wait(); // Очікування завершення всіх замовлень
                    }
                    System.out.println("Waiter finished serving all clients.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
