import java.util.*;

public class Main {


    public static<T extends Comparable<T>> void main(String[] args) {

        ArrayList<T> list = (ArrayList<T>) new ArrayList<>(List.of(1,2,3,4,1,2,3,232,3));
        System.out.println(findMax(list));

        ArrayList<T> listS = (ArrayList<T>) new ArrayList<>(List.of("ghjkladfsf","1,2,3"));
        System.out.println(findMax(listS));

        ArrayList<T> listD = (ArrayList<T>) new ArrayList<>(List.of(1.2,1.1));
        printItems(listD);

    }
    public static <T extends Comparable<T>> T findMax(ArrayList<T> array){
        return Collections.max(array);
    }
    public static void printItems(List<?> list){
        for (Object item:list
             ) {
            System.out.println(item);
        }
    }
}