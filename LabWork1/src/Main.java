import javax.swing.*;
import java.lang.ref.Cleaner;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int choose = 0;
        while(choose !=5){

            choose = input.nextInt();

            switch (choose){
                case 1:{

                    System.out.println("Loading");
                    NumberOne();
                    break;
                }
                case 2:{
                    System.out.println("Loading");
                    NumberTwo();
                    break;
                }
                case 3:{
                    System.out.println("Loading");
                    NumberThree();
                    break;
                }
                case 4:{
                    System.out.println("Loading");
                    NumberFour();
                    break;
                }
                default:
                    System.out.println("Wrong operation, try again");
                    break;
            }

        }

    }

    public static void NumberOne(){
        Scanner input = new Scanner(System.in);

        System.out.print("enter first number >");

        float firstNumber = input.nextFloat();

        System.out.print("enter first number >");

        float secondNumber = input.nextFloat();


        float sum = firstNumber+secondNumber;
        float min = firstNumber-secondNumber;
        float pl = firstNumber*secondNumber;
        float dil = firstNumber/secondNumber;


        System.out.println( "+ " + sum + "\n- " + min +"\n* " + pl + "\n/ " + dil);

    }

    public static void NumberTwo(){


        String text = "Hi, I’m Chandler. I make jokes when I’m uncomfortable";
        System.out.println(text.contains("joke"));

        char[] chars = text.toCharArray();
        Scanner input = new Scanner(System.in);

        int textlength = chars.length;
        boolean ContainSubstring= false;


        if (IsContainSubstring(chars,"joke") || IsContainSubstring(chars,"orange")){
            ContainSubstring = true;
        }

        char[] charsNoConsonants = DeleteСonsonants(chars);

        String TwoChars = String.valueOf(chars) + String.valueOf(charsNoConsonants);

        System.out.println(textlength + "\n" + ContainSubstring +"\n" + TwoChars);

    }

    public static char[] DeleteСonsonants(char[] mainWord){

        char[] consonants = {'q','w','r','t','p','s','d','f','g','h','j','k','l','z','x','c','v','b','n','m',
                'Q', 'W', 'R', 'T', 'P', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M',};

        char[] answer = new char[mainWord.length];
        int answerCounter = 0;

        for (int i = 0; i < mainWord.length; i++){
            boolean IsDetected = false;

            for (int j = 0; j < consonants.length; j++){
                if (mainWord[i]==consonants[j]){
                    IsDetected = true;
                }
            }
            if (!IsDetected){
                answer[answerCounter] = mainWord[i];
                answerCounter++;
            }

        }

        char[] finalAnswer = new char[answerCounter];

        for (int i = 0; i < answerCounter; i++){

            finalAnswer[i] = answer[i];

        }

        return finalAnswer;
    }

    public static boolean IsContainSubstring(char[] chars, String enterSubstring){

        char[] substring = enterSubstring.toCharArray();

        for (int i = 0; i < chars.length - substring.length;i++){
            boolean IsCorrect = true;
            for (int j = 0; j < substring.length; j++){
                if (chars[i+j]!=substring[j]){
                    IsCorrect = false;
                    break;
                }
            }
            if (IsCorrect){
                return IsCorrect;
            }
        }
        return false;
    }

    public static void NumberThree(){

        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();

        if (year % 4 == 0){
            System.out.println("true");
        }
        else {
            System.out.println("false");
        }

    }

    public static void NumberFour(){

        String[] days = {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};

        Scanner scanner = new Scanner(System.in);

        String strings = scanner.next();

        boolean IsFound = false;

        for (int i = 0; i < days.length; i++){
            if (days[i].equals(strings)){

                IsFound = true;
                System.out.println(i+1);
                break;
            }
        }
        if (!IsFound){
            System.out.println("No " + strings + " in days");
        }
    }
}