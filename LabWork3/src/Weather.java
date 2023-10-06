public class Weather {

    public String city;
    public int degrees;

    public Weather(String city, int degrees) {
        this.city = city;
        this.degrees = degrees;
    }

    @Override
    public String toString() {
        return city + " " + degrees+"\n";
    }
}
