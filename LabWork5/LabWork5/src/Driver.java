public class Driver extends Human{
    public int driveExperience;
    public int carInUseID;
    public String driverID;
    public Driver(String fullName, String birthdate, String phoneNumber, int driveExperience, int carInUseID, String driverID) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.driveExperience = driveExperience;
        this.carInUseID = carInUseID;
        this.driverID = driverID;
    }
    public Driver(String array[]) {
        this.fullName = array[3];
        this.birthdate = array[4];
        this.phoneNumber = array[5];
        this.driveExperience = Integer.parseInt(array[2]);
        this.carInUseID = Integer.parseInt(array[1]);
        this.driverID = array[0];
    }

    @Override
    public String ToSaveFormat() {
        return driverID+";"+carInUseID+";"+driveExperience + super.ToSaveFormat() + "\n";
    }
    @Override
    public String MainInfoOutput() {
        return driverID + " " + super.MainInfoOutput();
    }
}
