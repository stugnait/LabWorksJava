import javax.swing.*;
import java.util.*;

public class Main {


    public static <T extends Comparable<T>> void main(String[] args) {

        ArrayList<T> list = (ArrayList<T>) new ArrayList<>(List.of(1, 2, 3, 4, 1, 2, 3, 232, 3));
        System.out.println(GenericMethodImplementation(list));

        ArrayList<T> listS = (ArrayList<T>) new ArrayList<>(List.of("ghjkladfsf", "1,2,3"));
        System.out.println(GenericMethodImplementation(listS));

        ArrayList<T> listD = (ArrayList<T>) new ArrayList<>(List.of(1.2, 1.1));
        Wildcard(listD);

        ArrayList<T> list1 = new ArrayList<>();
        list1.add((T) "5678");
        list1.add((T)(Integer)6789);

        HashMap<T,String> list2 = new HashMap<>();
        list2.put((T)"ghjk","ghjk");
        list2.put((T)(Integer)6789,"rtghj");
    }

    public static <T extends Comparable<T>> T GenericMethodImplementation(ArrayList<T> array) {
        return Collections.max(array);
    }

    public static void Wildcard(List<?> list) {
        for (Object item : list
        ) {
            System.out.println(item);
        }
    }

    public static double UpperBoundedWildcard(List<? extends Number> array) {

        double sum = 0;

        for (Number thing : array) {
            sum += thing.doubleValue();
        }

        return sum;
    }

    class BoundedWildcard {

        public static void addToEach(List<? super Integer> list, int valueToAdd) {
            for (int i = 0; i < list.size(); i++) {
                list.set(i, list.indexOf(i) + (Integer) valueToAdd);
            }
        }
    }

    class UnboundedWildcard {
        public static void PrintAll(List<?> list) {
            System.out.println(list);
        }
    }
    interface Validator<T> {
        boolean validate(T item);
    }

    class StringValidator implements Validator<String> {
        @Override
        public boolean validate(String item) {
            return item != null && !item.isEmpty();
        }
    }

    class IntegerValidator implements Validator<Integer> {
        @Override
        public boolean validate(Integer item) {
            return item != null && item > 0;
        }
    }

    class Animal{
        public String voice() {
            return "raw";
        }
    }

    class Cat extends Animal{
        @Override
        public String voice() {
            return "meow";
        }
    }
    class Dog extends Animal{
        @Override
        public String voice() {
            return "of";
        }
    }


    public void Print(List<? extends Animal> list) {
        for (Animal animal:
             list) {
            animal.voice();
        }
    }
}