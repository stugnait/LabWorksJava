import java.util.Date;

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


class Animal{
    public String name;
    public int age;
    public int weight;

    public void Voice(){

    }
}

class Dog extends Animal{

}

class Cat extends Animal{


}