package Hangman;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Hangman {
    public static String [] secretWords = {"python", "java", "javascript", "kotlin"};
    public static Random randomGenerator = new Random();
    public static Scanner inputScanner = new Scanner(System.in);
    public static ArrayList<String> wordToGuess = new ArrayList<>();

    public static void displayGameBoard(ArrayList<String> wordToGuess, int attemptsLeft) {
        for (String character : wordToGuess) {
            System.out.print(character);
        }
    }

    public static boolean checkGameResult(ArrayList<String> wordToGuess, String secretWord, int attemptsLeft) {
        String guessResult = "";

        for (String character : wordToGuess) {
            guessResult += character;
        }

        if (guessResult.equals(secretWord)) {
            System.out.println("You correctly guessed the word!\nYou've won!");
            return false;
        } else if (attemptsLeft == 0) {
            System.out.println("You've lost the game!");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        while (true) {
            String secretWord = "";
            wordToGuess.clear();
            int attemptsLeft = 8;
            System.out.println("HIDDEN GAME");
            System.out.print("Type 'start' to play the game, 'exit' to quit: > ");
            String menuChoice = inputScanner.nextLine();

            if (menuChoice.equals("exit")) {
                System.out.println("Exiting the game...");
                break;
            } else if (menuChoice.equals("start")) {
                int index = randomGenerator.nextInt(secretWords.length);
                secretWord += secretWords[index];

                for (int i = 0; i < secretWord.length(); i++) {
                    wordToGuess.add("_");
                }

                while (checkGameResult(wordToGuess, secretWord, attemptsLeft)) {
                    displayGameBoard(wordToGuess, attemptsLeft);
                    System.out.print("\nEnter a letter: > ");
                    String playerGuess = inputScanner.nextLine();
                    char letterGuessed = playerGuess.charAt(0);

                    if (wordToGuess.contains(playerGuess)) {
                        System.out.println("You've already guessed this letter");
                    } else if (playerGuess.length() >= 2) {
                        System.out.println("Please input a single letter");
                        continue;
                    } else if (Pattern.matches("[A-Z]", playerGuess)) {
                        System.out.println("Please enter a lowercase English letter");
                        continue;
                    }

                    for (int i = 0; i < secretWord.length(); i++) {
                        if (secretWord.charAt(i) == letterGuessed) {
                            wordToGuess.remove(i);
                            wordToGuess.add(i, playerGuess);
                        }
                    }

                    if (!wordToGuess.contains(playerGuess)) {
                        System.out.println("That letter doesn't appear in the word");
                        attemptsLeft -= 1;
                    }
                }
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}
