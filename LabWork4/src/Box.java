import java.util.List;

public class Box <T>{
    public List<T> list;
    public void AddThing(T thing) {
        list.add(thing);
    }
    public void DeleteThing(T thing) {
        list.remove(thing);
    }

}
