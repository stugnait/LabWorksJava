public class Client extends Human {

    public int clientId;
    public Client(String fullName, String birthdate, String phoneNumber, int clientId) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.clientId = clientId;
    }
    public Client(String array[]) {
        this.fullName = array[1];
        this.birthdate = array[2];
        this.phoneNumber = array[3];
        this.clientId = Integer.parseInt(array[0]);
    }

    @Override
    public String ToSaveFormat() {
        return clientId +" " + super.ToSaveFormat()+"\n";
    }
    @Override
    public String MainInfoOutput() {
        return clientId + " " + super.MainInfoOutput();
    }

}
