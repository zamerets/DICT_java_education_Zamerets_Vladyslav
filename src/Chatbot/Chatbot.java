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
    }
}