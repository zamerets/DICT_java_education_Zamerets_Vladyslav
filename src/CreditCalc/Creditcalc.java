package CreditCalc;
import java.util.Scanner;

public class Creditcalc {
    public static double A = 0, P = 0, n = 0, i = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(true){
            System.out.println("What do you want to calculate? \n" +
                    "type \"n\" for number of monthly payments,\n" +
                    "type \"a\" for annuity monthly payment amount,\n" +
                    "type \"p\" for loan principal:");

            try{
                String mode = in.next();
                if(mode.equals("n") || mode.equals("N")){
                    writeData(false,  false,  true);
                    n = absoluteMonth(numberOfPayments(A, P, i));
                    if((n / 12) == 0){
                        System.out.println("It will take " + (int)n + " months to repay this loan! ");
                    }
                    else if((n % 12) == 0){
                        System.out.println("It will take " + (int)(n / 12) + "8 years to repay this loan! ");
                    }
                    else{
                        System.out.println("It will take " + (int)(n / 12) + " years and " + (int)(n % 12) + " months to repay this loan! ");
                    }
                }
                else if(mode.equals("a") || mode.equals("A")){
                    writeData(true,  false,  false);
                    A = absoluteMonth(annualPayment(P, n, i));
                    System.out.println("Your monthly payment = " + (int)A + "! ");
                }
                else if(mode.equals("p") || mode.equals("P")){
                    writeData(false,  true, false);
                    P = (int)principalLoanAmount(A, n, i);
                    System.out.println("Your loan principal = " + (int)P +"! ");
                }
                else if(mode.equals("d") || mode.equals("D")){//test diff funk
                    writeData(true,  false,  false);
                    int overPayment = 0;
                    for (int m = 1; m <= n; m++) {
                        int D = absoluteMonth(differentiatedPayment(P, n, i, m));
                        System.out.println("Month " + m + ": payment is " + D);
                        overPayment += D;
                    }
                    System.out.println("Overpayment = " + (int)(overPayment - P));
                }
            }
            catch(Exception e){
                System.out.println("Incorrect parameters ");
            }
            System.out.println("-------------------------------------------------");
        }
    }

    private static void writeData(boolean _A, boolean _P, boolean _n){
        Scanner in = new Scanner(System.in);
        if(!_P){
            System.out.println("Enter the loan principal: ");
            try {
                P = in.nextDouble();
            } catch (Exception e) {
                System.out.println("Incorrect parameters ");
            }
        }
        if(!_A){
            System.out.println("Enter the monthly payment: ");
            try {
                A = in.nextDouble();
            } catch (Exception e) {
                System.out.println("Incorrect parameters ");
            }
        }
        if(!_n){
            System.out.println("Enter the number of periods: ");
            try {
                n = in.nextDouble();
            } catch (Exception e) {
                System.out.println("Incorrect parameters ");
            }
        }
        System.out.println("Enter the loan interest: ");
        try {
            i = in.nextDouble();
        } catch (Exception e) {
            System.out.println("Incorrect parameters ");
        }
    }

    private static double annualPayment(double P, double n, double i){
        i = procent(i);
        return (P * ((i * Math.pow(1 + i, n)) / (Math.pow(1 + i, n) - 1)));
    }
    private static double principalLoanAmount(double A, double n, double i){
        i = procent(i);
        return A / ((i * Math.pow(1 + i, n)) / (Math.pow(1 + i, n) - 1));
    }
    private static double numberOfPayments(double A, double P, double i){
        i = procent(i);
        return log(1 + i, (A / (A - (i * P))));
    }
    private static double differentiatedPayment(double P, double n, double i, double m){
        i = procent(i);
        return (P / n) + (i * (P - ((P * (m - 1)) / n)));
    }

    private static double log(double base, double x){
        return Math.log(x) / Math.log(base);
    }
    private static double procent(double value){
        return (value / (1200));
    }

    private static int absoluteMonth(double month){
        return (month - (int)month) > 0.0? (int)month + 1: (int)month;
    }
}