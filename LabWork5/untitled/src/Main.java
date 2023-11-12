// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Shotgun XM209 = new Shotgun(5,4,400,"Marichka",1);
        System.out.println(XM209.ammo);
        Pistol USPS = new Pistol();
        USPS.GetKnife();
        USPS.Fire();

        int ShotgunXM209ammo = 20;

        XM209.Fire();
        System.out.println(XM209.ammo);


        XM209.FireAllAmmo();

        System.out.println(XM209.ammo);


    }
}

abstract class Gun{

    public Gun(){

    }

    public Gun(int ammo, int weight, int cost, String mods) {
        this.ammo = ammo;
        this.weight = weight;
        this.cost = cost;
        this.mods = mods;
    }

    public int ammo;
    public int weight;
    public int cost;
    public String mods;

    public void Fire(){
        this.ammo-=1;
    }
}
class Shotgun extends Gun{
    public Shotgun(int amountOfDulo) {
        this.amountOfDulo = amountOfDulo;
    }

    public Shotgun(int ammo, int weight, int cost, String mods, int amountOfDulo) {
        super(ammo, weight, cost, mods);
        this.amountOfDulo = amountOfDulo;
    }

    public int amountOfDulo;
    public void Reload(){
        System.out.println("Чік-чік");
    }

    @Override
    public void Fire() {
        System.out.println("пав");
        super.Fire();
    }
    public void FireAllAmmo(){
        System.out.println("hOHLAM PIZDA");
        while (this.ammo != 0){
            this.Fire();
            Reload();
        }
    }
}
class Pistol extends Gun{

    public void GetKnife(){
        System.out.println("Достав ніж");
    }
    @Override
    public void Fire() {
        System.out.println("пів");
    }
}
class Rifle extends Gun{

    @Override
    public void Fire() {
        System.out.println("трататав");
    }
}