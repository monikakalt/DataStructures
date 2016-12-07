/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainclass;

import java.util.Random;
 public class CharsThread{
        int vel, len, x, y, yBottom;
        char[][] chArr;

        CharsThread(int x){

            this.x = x;
            len = randInt(5,30);
            chArr = new char[len][1];
            chArr = populateArrWithChars(chArr);
            vel = randInt(1,5);
            this.y = (-1)*len*32;
        }
        public char[][] populateArrWithChars(char[][] arr){
            for (int i = 0; i < arr.length; i++) {
                arr[i][0] = randChar();
            }
            return arr;
        }
        public char randChar(){
            final String alphabet = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
            final int N = alphabet.length();
            Random r = new Random();
            return alphabet.charAt(r.nextInt(N));
        }
        public int randInt(int min, int max) {
            Random rand = new Random();
            int randomNum = rand.nextInt((max - min) + 1) + min;
            return randomNum;
        }
    }
