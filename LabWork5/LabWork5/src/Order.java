public class Order {

    public int orderId;
    private float distanceInKM;
    private int driverID;
    public int clientID;

    public Order(int orderId,float distanceInKM, int driverID, int clientID){
        this.orderId = orderId;
        this.distanceInKM = distanceInKM;
        this.driverID = driverID;
        this.clientID = clientID;
    }
    public Order(String array[]) {
        this.orderId = Integer.parseInt(array[3]);
        this.distanceInKM = Float.parseFloat(array[0]);
        this.driverID = Integer.parseInt(array[1]);
        this.clientID = Integer.parseInt(array[2]);
    }

    public String ToSaveFormat(){
        return distanceInKM+";"+driverID+";"+clientID+";"+orderId+"\n";
    }
    public float getDistanceInKM() {
        return distanceInKM;
    }
    public void setDistanceInKM(float distanceInKM) {
        this.distanceInKM = distanceInKM;
    }
    public int getDriverID() {
        return driverID;
    }
    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }
}
