import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        List<String> strings = new ArrayList<>();
        new ArrayList<>();


        System.out.println("number 1\n\n");


        strings.add("ghjk");
        strings.add("asd");
        strings.add("asd");
        strings.add("ghjk");

        List<String> output = strings.stream()
            .filter(item -> item.contains("a"))
            .map(String::toUpperCase)
            .toList();


        for (String item: output
             ) {
            System.out.println(item);
        }


        System.out.println("\n\nnumber 2\n\n");

        List<Book> books = new ArrayList<>();

        books.add(new Book("Book1",2012));
        books.add(new Book("Book2",2002));
        books.add(new Book("Book3",2013));
        books.add(new Book("Book4",1913));

        List<Book> sortedBooks = books.stream()
                .sorted(Comparator.comparing(Book::getYear))
                .limit(3)
                .toList();



        for (Book book : sortedBooks) {
            System.out.println(book.toString());
        }


        System.out.println("\n\nnumber 3\n\n");


        List<Student> students = new ArrayList<>();

        students.add(new Student("Igor",1,2));
        students.add(new Student("Vasya",2,4));
        students.add(new Student("Kit",5,5));
        students.add(new Student("Kit",5,2));
        students.add(new Student("Kit",5,3));
        students.add(new Student("Kit",5,5));

        Map<Integer,Double> outputStudents = students.stream()
                .collect(Collectors.groupingBy(Student::getCourse,Collectors.averagingDouble(Student::getAvgMark)));

        System.out.println(outputStudents);


        System.out.println("\n\nnumber 4\n\n");

        List<Order> orders = new ArrayList<>();

        orders.add(new Order(new ArrayList<>(Arrays.asList(new Items(50),new Items(60),new Items(30)))));
        orders.add(new Order(new ArrayList<>(Arrays.asList(new Items(20),new Items(80),new Items(40)))));



        List<Items> downToFifty = orders.stream()
                .flatMap(order -> order.getElements().stream())
                .filter(item -> item.getPrice() > 50)
                .toList();

        System.out.println(downToFifty);


        System.out.println("\n\nnumber 5\n\n");



        List<Integer> ints = new ArrayList<>(Arrays.asList(1,1,13,12));

        List<Integer> sorted = ints.stream()
                .distinct()
                .toList();

        System.out.println(sorted.size());


        System.out.println("\n\nnumber 6\n\n");

        List<Operation> operations =new ArrayList<>();

        operations.add(new Operation("Витрата",-1000));
        operations.add(new Operation("Дохід",1000));
        operations.add(new Operation("Дохід",124124));
        operations.add(new Operation("Витрата",-124));

        Map<Boolean,Integer> sortedMap = operations.stream()
                .collect(Collectors.partitioningBy(
                    operation -> "Витрата".equals(operation.getCategory()),
                    Collectors.summingInt(Operation::getMoney)));


        System.out.println(sortedMap);

        System.out.println("\n\nnumber 6\n\n");

        List<String> strings2= new ArrayList<>(List.of("asdgf","sadfddg","asdfgh"));


        String st567= strings2.stream()
                .collect(Collectors.joining(", "));

        System.out.println(st567);

        List<Weather> weathers = new ArrayList<>();
         weathers.add(new Weather("Manya",40));
         weathers.add(new Weather("Pola",20));
         weathers.add(new Weather("Ukr",30));
         weathers.add(new Weather("Tya",-20));


        List<Weather> weathers1 = weathers.stream()
                .peek(city -> {
                    if (city.degrees > 30) {
                        System.out.println(city);
                    }
                })
                .filter(weather -> weather.degrees <= 30)
                .toList();

        System.out.println("Holodno:");

        System.out.println(weathers1);

    }
}
