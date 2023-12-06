package CoffeeMachine;


import java.util.Scanner;

public class Cf {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        do {
            coffeeMachine.displayMessage();
        } while (coffeeMachine.handleUserInput(inputScanner.nextLine()));
    }
}

class CoffeeMachine {
    private int availableWater = 400;
    private int availableMilk = 540;
    private int availableCoffeeBeans = 120;
    private int availableCups = 9;
    private int moneyEarned = 550;
    private MachineState currentState = MachineState.MAIN_MENU;

    private final String[] messages = new String[MachineState.values().length];
    private String errorData;

    private static final String BUY_COMMAND = "buy";
    private static final String FILL_COMMAND = "fill";
    private static final String TAKE_COMMAND = "take";
    private static final String REMAINING_COMMAND = "remaining";
    private static final String EXIT_COMMAND = "exit";

    private static final String ESPRESSO_COMMAND = "1";
    private static final String LATTE_COMMAND = "2";
    private static final String CAPPUCCINO_COMMAND = "3";
    private static final String BACK_COMMAND = "back";

    private static final String MAIN_MENU_MESSAGE = String.format(
            "Write action (%s, %s, %s, %s, %s):",
            BUY_COMMAND, FILL_COMMAND, TAKE_COMMAND, REMAINING_COMMAND, EXIT_COMMAND);
    private static final String BUY_MENU_MESSAGE = String.format("What do you want to buy? " +
                    "%s - espresso, %s - latte, %s - cappuccino, %s - to main menu:",
            ESPRESSO_COMMAND, LATTE_COMMAND, CAPPUCCINO_COMMAND, BACK_COMMAND);
    private static final String BUY_OK_MESSAGE = "I have enough resources, making you a coffee!";
    private static final String BUY_ERROR_MESSAGE = "Sorry, not enough %s!";

    private static final String FILL_MESSAGE = "Write how many %s of %s do you want to add:";
    private static final String RESOURCE_WATER = "water";
    private static final String RESOURCE_MILK = "milk";
    private static final String RESOURCE_BEANS = "coffee beans";
    private static final String RESOURCE_CUPS = "disposable cups";
    private static final String RESOURCE_CUPS_ENDING = "coffee";

    private static final String RESOURCE_VOLUME = "ml";
    private static final String RESOURCE_WEIGHT = "grams";
    private static final String CURRENCY = "$";

    private static final String TAKE_MESSAGE = "I gave you %s%d";

    CoffeeMachine() {
        messages[MachineState.MAIN_MENU.ordinal()] = MAIN_MENU_MESSAGE;
        messages[MachineState.BUY_MENU.ordinal()] = BUY_MENU_MESSAGE;
        messages[MachineState.BUY_OK.ordinal()] = BUY_OK_MESSAGE;
        messages[MachineState.FILL_WATER.ordinal()] = String.format(FILL_MESSAGE, RESOURCE_VOLUME, RESOURCE_WATER);
        messages[MachineState.FILL_MILK.ordinal()] = String.format(FILL_MESSAGE, RESOURCE_VOLUME, RESOURCE_MILK);
        messages[MachineState.FILL_BEANS.ordinal()] = String.format(FILL_MESSAGE, RESOURCE_WEIGHT, RESOURCE_BEANS);
        messages[MachineState.FILL_CUPS.ordinal()] = String.format(FILL_MESSAGE, RESOURCE_CUPS, RESOURCE_CUPS_ENDING);
    }

    public void displayMessage() {
        String message;
        switch (currentState) {
            case BUY_ERROR:
                message = String.format(BUY_ERROR_MESSAGE, errorData);
                break;
            case TAKE_MONEY:
                message = String.format(TAKE_MESSAGE, CURRENCY, takeMoney());
                break;
            case SHOW_REMAINING:
                message = toString();
                break;
            default:
                message = messages[currentState.ordinal()];
        }
        System.out.println(message);
    }

    public boolean handleUserInput(String command) {
        if (EXIT_COMMAND.equals(command)) {
            return false;
        }

        switch (currentState) {
            case MAIN_MENU:
                switch (command) {
                    case BUY_COMMAND:
                        currentState = MachineState.BUY_MENU;
                        break;
                    case FILL_COMMAND:
                        currentState = MachineState.FILL_WATER;
                        break;
                    case TAKE_COMMAND:
                        currentState = MachineState.TAKE_MONEY;
                        displayMessage();
                        currentState = MachineState.MAIN_MENU;
                        break;
                    case REMAINING_COMMAND:
                        currentState = MachineState.SHOW_REMAINING;
                        displayMessage();
                        currentState = MachineState.MAIN_MENU;
                        break;
                }
                break;
            case BUY_MENU:
                Coffee coffee;
                switch (command) {
                    case ESPRESSO_COMMAND:
                        coffee = new Espresso();
                        break;
                    case LATTE_COMMAND:
                        coffee = new Latte();
                        break;
                    case CAPPUCCINO_COMMAND:
                        coffee = new Cappuccino();
                        break;
                    default:
                        coffee = null;
                        currentState = MachineState.MAIN_MENU;
                }
                if (coffee != null) {
                    String error = checkCapacity(coffee);
                    if (error != null) {
                        errorData = error;
                        currentState = MachineState.BUY_ERROR;
                    }
                    else {
                        makeCoffee(coffee);
                        currentState = MachineState.BUY_OK;
                    }
                    displayMessage();
                    currentState = MachineState.MAIN_MENU;
                }
                break;
            case FILL_WATER:
                addWater(Integer.parseInt(command));
                currentState = MachineState.FILL_MILK;
                break;
            case FILL_MILK:
                addMilk(Integer.parseInt(command));
                currentState = MachineState.FILL_BEANS;
                break;
            case FILL_BEANS:
                addBeans(Integer.parseInt(command));
                currentState = MachineState.FILL_CUPS;
                break;
            case FILL_CUPS:
                addCups(Integer.parseInt(command));
                currentState = MachineState.MAIN_MENU;
                break;
        }
        return true;
    }

    private void addWater(int water) {
        this.availableWater += water;
    }

    private void addMilk(int milk) {
        this.availableMilk += milk;
    }

    private void addBeans(int beans) {
        this.availableCoffeeBeans += beans;
    }

    private void addCups(int cups) {
        this.availableCups += cups;
    }

    private String checkCapacity(Coffee coffee) {
        String error = null;
        if (availableWater < coffee.getWater()) {
            error = "water";
        }
        else if (availableMilk < coffee.getMilk()) {
            error = "milk";
        }
        else if (availableCoffeeBeans < coffee.getBeans()) {
            error = "coffee beans";
        }
        else if (availableCups < 1) {
            error = "disposable cups";
        }

        return error;
    }

    private void makeCoffee(Coffee coffee) {
        availableWater -= coffee.getWater();
        availableMilk -= coffee.getMilk();
        availableCoffeeBeans -= coffee.getBeans();
        availableCups -= 1;
        moneyEarned += coffee.getCost();
    }

    private int takeMoney() {
        int taken = this.moneyEarned;
        this.moneyEarned = 0;
        return taken;
    }

    @Override
    public String toString() {
        return "The coffee machine has:\n" +
                availableWater + " of " + RESOURCE_WATER + "\n" +
                availableMilk + " of " + RESOURCE_MILK + "\n" +
                availableCoffeeBeans + " of " + RESOURCE_BEANS + "\n" +
                availableCups + " of " + RESOURCE_CUPS + "\n" +
                CURRENCY + moneyEarned + " of money";
    }
}

abstract class Coffee {
    final protected int water;
    final protected int milk;
    final protected int coffeeBeans;
    final protected int cost;

    protected Coffee(int water, int milk, int coffeeBeans, int cost) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cost = cost;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getBeans() {
        return coffeeBeans;
    }

    public int getCost() {
        return cost;
    }
}
enum MachineState {
    MAIN_MENU, BUY_MENU, BUY_OK, BUY_ERROR, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS, TAKE_MONEY, SHOW_REMAINING
}

class Espresso extends Coffee {
    Espresso() {
        super(250, 0, 16, 4);
    }
}

class Latte extends Coffee {
    Latte() {
        super(350, 75, 20, 7);
    }
}

class Cappuccino extends Coffee {
    Cappuccino() {
        super(200, 100, 12, 6);
    }
}
