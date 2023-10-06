public class Items {
    public int price;

    public Items(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Items{" +
                "price=" + price +
                '}';
    }
}
