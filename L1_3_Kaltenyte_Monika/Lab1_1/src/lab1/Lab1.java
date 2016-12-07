package lab1;

import java.io.IOException;

public class Lab1 {

    public static void main(String[] args) throws IOException {
        String t = "Title";
        String p = "Price";
        String m = "Months";
        String i = "Income";
        String result = String.format("%-15s %7s %6s %7s",
                t, p, m, i);
        System.out.println(result);
        Subscription.toConsole();
    }

}
