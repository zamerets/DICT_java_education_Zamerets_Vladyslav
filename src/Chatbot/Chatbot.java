package Chatbot;
import java.util.Scanner;
public class Chatbot{
    public static void main(String[] args) {
        System.out.println("Hello! My name is Anton");
        System.out.println("I was created in 2023");
        Scanner scan = new Scanner(System.in);
        System.out.println("Please, enter your name");
        String name = scan.next();
        System.out.println("What a great name you have, " + name + "!");
        System.out.println("Let me guess your age");
        System.out.println("Enter remainders of dividing your age by 3, 5 and 7");
        int remainder3 = scan.nextInt();
        int remainder5 = scan.nextInt();
        int remainder7 = scan.nextInt();
        int age =  (remainder3 * 70 + remainder5 * 21 + remainder7 * 15) % 105;
        System.out.println("Your age is " + age + "; that's a good time to start programming!");
        System.out.println("Now I will prove to you that I can count to any number you want");
        int userNumber = scan.nextInt();
        int counter =  0;
        while(counter <= userNumber) {
            System.out.println(counter + " !");
            counter += 1;
        }
        System.out.println("Let's test your programming knowledge");
        System.out.println("Witch number do we get, if we divide 10/5");
        System.out.println("1. '1' \n2. '2' \n3. '3' \n4. '4'");
        int check = 0;
        while(check == 0) {
            int userChoice = scan.nextInt();
            if (userChoice == 2){
                check = 2;
                System.out.println("Completed, have a nice day!");
            }
            else {
                System.out.println("Please, try  again");
            }
    }
}
}