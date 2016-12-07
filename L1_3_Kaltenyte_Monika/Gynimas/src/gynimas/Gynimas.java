/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gynimas;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Gynimas {

    //static 
    public static void main(String[] args) {
        Gynimas g = new Gynimas();
        g.demo();
    }

    public static int[] gener2000(int n) {
        int temp = 0;
        int[] items = new int[n];
        Integer[] arr = new Integer[n];
        for (int i = 2000; i <= 2000 + n - 1; i++) {
            arr[temp++] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        for (int i = 0; i < arr.length; i++) {
            items[i] = arr[i];
        }
        return items;
    }

    public void demo() {
        int n = 5;
        int[] m = gener2000(n);
        for (int i = 0; i < n; i++) {
           System.out.println(m[i]);
        }

//        Random rand = new Random();
//
//        int[] skaiciai = new int[n];
//        for (int i = 0; i < n; i++) {
//            int rnd = new Random().nextInt(n); 
//            if (!Arrays.asList(skaiciai).contains(m[rnd])) {
//                 skaiciai[i]=m[rnd];
//            } else {
//                i--;
//            }
//        }
//     
//        for (int i = 0; i < n; i++) {
//             System.out.println(skaiciai[i]);
//        }
    }

}
