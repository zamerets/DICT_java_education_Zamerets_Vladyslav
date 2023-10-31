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
    }
}