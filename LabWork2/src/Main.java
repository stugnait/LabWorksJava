import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("1-add; 2-sort; 3-show all");

        try {


            int choose = scanner.nextInt();

            switch (choose) {

                case 1: {

                    break;
                }

                case 2: {
                    Driver driver = new Driver();
                    break;
                }

                case 3: {
                    Client client = new Client();
                    break;
                }

                default: {
                    System.out.println("1-add; 2-sort; 3-show all");

                }

            }
        } catch (Exception e) {


        }

        System.out.println("1-driver; 2-car; 3-client; 4-ride");

    }
}
