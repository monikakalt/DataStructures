package lab1;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Magazine {

    private String magazine;
    private double price;
    private int allMonths;
    private double income;

    public Magazine(String m, double p, int a, double i) {
        this.magazine = m;
        this.price = p;
        this.allMonths = a;
        this.income = i;
    }

    public int getAllMonths() {
        return allMonths;
    }

    public void setAllMonths(int AllMonths) {
        this.allMonths = AllMonths;
    }

    public void setIncome(double Income) {
        this.income = Income;
    }

    public String getMagazine() {
        return magazine;
    }

    public double getPrice() {
        return price;
    }

    public Magazine(String data) {
        Scanner sc = new Scanner(data);
        sc.useDelimiter(";");
        try {
            this.magazine = sc.next();
            this.price = sc.nextDouble();
            this.allMonths = 0;
            this.income = 0;
        } catch (Exception e) {
            System.out.println("error in magazines data");
        }
    }

    @Override
    public String toString() {
        return String.format("%-15s  %5.2f  %5d    %5.2f", magazine, price, allMonths, income);
    }
}
