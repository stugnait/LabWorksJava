public class Car implements Comparable<Car>{

    public int id;
    public String mark;
    public String model;
    public String fuelType;
    public String bodyType;
    public String color;
    public String licencePlate;
    public int maxPassengers;
    public int maxWeight;
    public int driven;
    public int driverID;

    public Car(int id, String mark, String model, String fuelType, String bodyType, String color, String licencePlate, int maxPassengers, int maxWeight, int driven, int driverID) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.fuelType = fuelType;
        this.bodyType = bodyType;
        this.color = color;
        this.licencePlate = licencePlate;
        this.maxPassengers = maxPassengers;
        this.maxWeight = maxWeight;
        this.driven = driven;
        this.driverID = driverID;
    }

    public Car(String[] array){

        if (array.length==2){
            this.id = Integer.parseInt(array[0]);
            this.licencePlate = array[1];
        }
        else {
            this.id = Integer.parseInt(array[0]);
            this.mark = array[1];
            this.model = array[2];
            this.fuelType = array[3];
            this.bodyType = array[4];
            this.color = array[5];
            this.licencePlate = array[6];
            this.maxPassengers = Integer.parseInt(array[7]);
            this.maxWeight = Integer.parseInt(array[8]);
            this.driven = Integer.parseInt(array[9]);
            this.driverID = Integer.parseInt(array[10]);
        }
    }


    public String ToSaveFormat(){
        return id+";"+mark+";"+model+";"+fuelType+";"+bodyType+";"+color+";"+licencePlate+";"+maxPassengers+";"+maxWeight+";"+driven+";"+driverID+"\n";
    }

    public String ToMainSaveFormat(){
        return id+";"+licencePlate+"\n";
    }
    public void MainInfoOutput(){
        System.out.println(id+" - "+ licencePlate + "\n");
    }
    public void BigInfoOutput(){
        System.out.println(id + " - " + driven + " " + licencePlate + "\n");
    }


    @Override
    public int compareTo(Car other) {
        // Порівнюємо int-поле двох об'єктів
        return Integer.compare(this.driven, other.driven);
    }

}
