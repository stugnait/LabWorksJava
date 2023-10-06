import java.util.List;

public class Order {

    public List<Items> elements;

    public Order(List<Items> elements) {
        this.elements = elements;
    }

    public List<Items> getElements() {
        return elements;
    }
}

class Operation{
    public String category;
    public int money;

    public String getCategory() {
        return category;
    }

    public Operation(String category, int money) {
        this.category = category;
        this.money = money;
    }

    public int getMoney() {
        return money;
    }
}