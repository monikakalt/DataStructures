package Lab2Kaltenyte;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import studijosKTU.Ks;
import studijosKTU.ListKTU;

public class GreitaveikosTyrimas {

    public static void main(String[] args) {
        int x = 1000;
        pirmasTyrimas(x);
        antrasTyrimas();
    }

    //5	Math.sqrt(x)	<->	Math.sin(x)
    static void pirmasTyrimas(int x) {
        double x1 = Math.sqrt(x);
        long t1 = System.nanoTime();
        double x2 = Math.sin(x);
        long l1 = System.nanoTime();
        double x3 = Math.sqrt(x);
        long t2 = System.nanoTime();
        double x4 = Math.sin(x);
        long l2 = System.nanoTime();
        String saknis = "√x";
        String sin = "sin x";
        System.out.println("-----------------------------------");
        System.out.println("PIRMAS");
        System.out.println("Rezultatai, atlikus bandymą 1 kartą");
        Ks.ouf("%10s %10s \n", saknis, sin);
        Ks.ouf("%7.9f %7.9f \n", (t2 - t1) / 1e9, (l2 - l1) / 1e9);
        int n = 0;
        for (int i = 0; i <= 1000; i++) {
            x1 = Math.sqrt(x);
            t1 += System.nanoTime();
            x2 = Math.sin(x);
            l1 += System.nanoTime();
            x3 = Math.sqrt(x);
            t2 += System.nanoTime();
            x4 = Math.sin(x);
            l2 += System.nanoTime();
            n = i;
        }      
        System.out.println("Rezultatai, atlikus bandymą " + n + " kartų");
        Ks.ouf("%10s %10s \n", saknis, sin);
        Ks.ouf("%7.9f %7.9f \n", (t2 - t1) / 1e9, (l2 - l1) / 1e9);
        System.out.println("-----------------------------------");

    }
    //11 ArrayList<Integer> <->	LinkedList<Integer>	metodas lastIndexOf(Object o)
    static void antrasTyrimas() {
        int[] tiriamiKiekiai1 = {2_000, 4_000, 8_000, 16_000};

        int x = 3;
        int a1[] = {1, 2, 3, 55, 4, 6, 7, 8, 9,
            11, 15, 19, 21, 99};

        ArrayList<Integer> array = new ArrayList<>();
        for (int i : a1) {
            array.add(i);
        }
        LinkedList<Integer> list = new LinkedList<>();
        for (int i : a1) {
            list.add(i);
        }

        int x1 = array.lastIndexOf(x);
        long t1 = System.nanoTime();
        int x2 = list.lastIndexOf(x);
        long l1 = System.nanoTime();
        int x3 = array.lastIndexOf(x);
        long t2 = System.nanoTime();
        int x4 = list.lastIndexOf(x);
        long l2 = System.nanoTime();
        String ArrayListas = "pirmas";
        String LinkedListas = "antras";
        System.out.println("-----------------------------------");
        System.out.println("ANTRAS");
        System.out.println("Rezultatai, atlikus bandymą 1 kartą");
        Ks.ouf("%10s %10s \n", ArrayListas, LinkedListas);
        Ks.ouf("%7.9f %7.9f \n", (t2 - t1) / 1e9, (l2 - l1) / 1e9);
        int n = 0;
        for (int i : tiriamiKiekiai1) {
            x1 = array.lastIndexOf(x);
            t1 += System.nanoTime();
            x2 = list.lastIndexOf(x);
            l1 += System.nanoTime();
            x3 = array.lastIndexOf(x);
            t2 += System.nanoTime();
            x4 = list.lastIndexOf(x);
            l2 += System.nanoTime();
            n = i;
        }
        System.out.println("Rezultatai, atlikus bandymą " + n + " kartų");
        Ks.ouf("%10s %10s \n", ArrayListas, LinkedListas);
        Ks.ouf("%7.9f %7.9f \n", (t2 - t1) / 1e9, (l2 - l1) / 1e9);
        System.out.println("-----------------------------------");
    }

}
