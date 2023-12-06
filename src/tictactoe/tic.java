package tictactoe;

import java.util.Scanner;

public class tic {

    final static int FIELD_HEIGHT = 3;
    final static int FIELD_WIDTH = 3;
    static char[][] field = new char[FIELD_HEIGHT][FIELD_WIDTH];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char symbol = 'X';

        getFieldFromString("_________");
        printField();
        while (checkState().equals("Game not finished")) {
            System.out.print("Enter the coordinates: ");
            String row = scanner.next();
            String column = scanner.next();
            if (checkInt(row) && checkInt(column)) {
                if (checkCoordinates(Integer.parseInt(row), Integer.parseInt(column), symbol)) {
                    printField();
                    symbol = changeSymbol(symbol);
                    continue;
                }
            } else {
                System.out.println("You should enter numbers!");
                continue;
            }
        }
        System.out.println(checkState());
    }

    public static void getFieldFromString(String line) {
        for (int i = 0; i < FIELD_HEIGHT; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                field[i][j] = line.charAt(i * 3 + j);
            }
        }
    }

    public static void getFieldFromInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cells: ");
        String line = scanner.nextLine();
        for (int i = 0; i < FIELD_HEIGHT; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                field[i][j] = line.charAt(i * 3 + j);
            }
        }
    }

    public static void printField() {
        System.out.println("╔═══╦═══╦═══╗");
        for (int i = 0; i < FIELD_HEIGHT; i++) {
            System.out.print("║ ");
            for (int j = 0; j < FIELD_WIDTH; j++) {
                System.out.print(field[i][j] + " ");
                if (j < FIELD_WIDTH - 1) {
                    System.out.print("║ ");
                }
            }
            System.out.println("║");
            if (i < FIELD_HEIGHT - 1) {
                System.out.println("╠═══╬═══╬═══╣");
            }
        }
        System.out.println("╚═══╩═══╩═══╝");
    }

    public static int checkThreeInHRow(char symbol) {
        int totalCounter = 0;
        for (int i = 0; i < FIELD_HEIGHT; i++) {
            int counter = 0;
            for (int j = 1; j < FIELD_WIDTH; j++) {
                if (field[i][j] == field[i][j - 1] && field[i][j] == symbol) {
                    counter++;
                }
            }
            if (counter == 2) {
                totalCounter++;
            }
        }
        return totalCounter;
    }

    public static int checkThreeInWRow(char symbol) {
        int totalCounter = 0;
        for (int i = 0; i < FIELD_HEIGHT; i++) {
            int counter = 0;
            for (int j = 1; j < FIELD_WIDTH; j++) {
                if (field[j][i] == field[j - 1][i] && field[j][i] == symbol) {
                    counter++;
                }
            }
            if (counter == 2) {
                totalCounter++;
            }
        }
        return totalCounter;
    }

    public static int checkThreeInDiagonal(char symbol) {
        if ((field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[2][2] == symbol) || (field[2][0] == field[1][1] && field[1][1] == field[0][2] && field[0][2] == symbol)) {
            return 1;
        }
        return 0;
    }

    public static int calculateSymbol(char symbol) {
        int counter = 0;
        for (char[] ar : field) {
            for (char el : ar) {
                if (el == symbol) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public static String checkState() {
        int threeXInHRow = checkThreeInHRow('X');
        int threeXInWRow = checkThreeInWRow('X');
        int threeOInHRow = checkThreeInHRow('O');
        int threeOInWRow = checkThreeInWRow('O');
        int threeXInDiagonal = checkThreeInDiagonal('X');
        int threeOInDiagonal = checkThreeInDiagonal('O');
        int numberOfX = calculateSymbol('X');
        int numberOfO = calculateSymbol('O');
        int numberOfEmpty = calculateSymbol('_');
        String state = "";

        if (numberOfX - numberOfO >= 2 || numberOfO - numberOfX >= 2 || (threeXInHRow > 0 && threeOInHRow > 0) || (threeXInWRow > 0 && threeOInWRow > 0)) {
            state = "Impossible";
        }
        if ((threeXInHRow == 1 || threeXInWRow == 1 || threeXInDiagonal == 1) && state.equals("")) {
            state = "X wins";
        }
        if ((threeOInHRow == 1 || threeOInWRow == 1 || threeOInDiagonal == 1) && state.equals("")) {
            state = "O wins";
        }
        if (numberOfEmpty > 0 && state.equals("")) {
            state = "Game not finished";
        }
        if ((threeXInHRow == 0 || threeXInWRow == 0) && numberOfEmpty == 0 && state.equals("")) {
            state = "Draw";
        }

        return state;
    }

    public static boolean checkCoordinates(int row, int column, char symbol) {
        if (row < 1 || row > 3 || column < 1 || column > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
        if (field[row - 1][column - 1] == 'X' || field[row - 1][column - 1] == 'O') {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
        field[row - 1][column - 1] = symbol;
        return true;
    }

    public static boolean checkInt(String checkedValue) {
        for (int i = 0; i < checkedValue.length(); i++) {
            if (checkedValue.charAt(i) < 47 || checkedValue.charAt(i) > 58) {
                return false;
            }
        }
        return true;
    }

    public static char changeSymbol(char symbol) {
        if (symbol == 'X') {
            return 'O';
        } else {
            return 'X';
        }
    }
}
