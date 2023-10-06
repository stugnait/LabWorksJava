public class Book {
    public String name;
    public int year;

    public Book(String name, int year) {
        this.name = name;
        this.year = year;
    }

    @Override
    public String toString() {
        return name + " " + year;
    }

    public int getYear() {
        return year;
    }
}
