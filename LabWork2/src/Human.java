public abstract class Human {
    public String fullName;
    public String birthdate;
    public String phoneNumber;

    public String ToSaveFormat(){
        return ";"+fullName+";"+birthdate+";"+phoneNumber;
    }
    public String MainInfoOutput(){
        return fullName;
    }

}
