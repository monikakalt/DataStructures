/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2Kaltenyte;



import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import studijosKTU.*;

public class SortTyrimas {
    Knyga[] šunųBazė1;
    ListKTU<Knyga> knygos = new ListKTU<>();
    Random rnd = new Random();  // atsitiktinių generatorius
    int[] tiriamiKiekiai = {2_000, 4_000, 8_000, 16_000};
//    pabandykite, gal Jūsų kompiuteris įveiks šiuos eksperimentus
//    paieškokite ekstremalaus apkrovimo be burbuliuko metodo
//    static int[] tiriamiKiekiai = {10_000, 20_000, 40_000, 80_000};
    
    void generuotiKnygas(int kiekis){
       String[][] am = { // galimų šunų veislių ir jų vardų masyvas
         {"Egziuperi", "Princas", "Vienuolis", "Pasaka", "Lietus"},
          {"Žemaitė", "Marti", "Jonė", "Pyragai", "Draugai", "Pasakos"},
          {"Degutytė", "Eilėraščiai", "Dainos"},
          {"Homeras", "Iliada", "Odisėja", "Graikai"},
          {"Meyer", "Saulėlydis", "Mėnulis", "Saulė", "Dangus"},
       };
        šunųBazė1= new Knyga[kiekis];
        rnd.setSeed(2017);
        for(int i=0;i<kiekis;i++){
            int ma = rnd.nextInt(am.length);        // veislės indeksas  0..
            int mo = rnd.nextInt(am[ma].length-1)+1;// veislės indeksas 1..
            šunųBazė1[i]= new Knyga(am[ma][0], am[ma][mo],
                2013 + rnd.nextInt(3),           // metai tarp 2013 ir 2016
                100 + rnd.nextDouble()*1000); // kaina tarp 100 ir 1000
        }
        Collections.shuffle(Arrays.asList(šunųBazė1));
        knygos.clear();
        for(Knyga a: šunųBazė1) knygos.add(a);
    }
    
    void paprastasTyrimas(int elementųKiekis){
// Paruošiamoji tyrimo dalis
        long t0=System.nanoTime();
        generuotiKnygas(elementųKiekis);
        ListKTU<Knyga> knygos2 = knygos.clone();
        ListKTU<Knyga> knygos3 = knygos.clone();
        ListKTU<Knyga> knygos4 = knygos.clone();
        long t1=System.nanoTime();
        System.gc(); System.gc(); System.gc();
        long t2=System.nanoTime();
//  Greitaveikos bandymai ir laiko matavimai
        knygos.sortSystem();
        long t3=System.nanoTime();
        knygos2.sortSystem(Knyga.pagalKainą);
        long t4=System.nanoTime();
        knygos3.sortBuble();
        long t5=System.nanoTime();
        knygos4.sortBuble(Knyga.pagalKainą);
        long t6=System.nanoTime();
        Ks.ouf("%7d %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f \n", elementųKiekis,
                (t1-t0)/1e9, (t2-t1)/1e9, (t3-t2)/1e9,
                (t4-t3)/1e9, (t5-t4)/1e9, (t6-t5)/1e9 );
    }
// sekančio tyrimo metu gaunama normalizuoti įvertinimai su klase TimeKeeper
    void sisteminisTyrimas(){
    // Paruošiamoji tyrimo dalis
        Timekeeper tk = new Timekeeper(tiriamiKiekiai);
        for (int kiekis : tiriamiKiekiai) {
           generuotiKnygas(kiekis);
           ListKTU<Knyga> knygos2 = knygos.clone();
           ListKTU<Knyga> knygos3 = knygos.clone();
           ListKTU<Knyga> knygos4 = knygos.clone();

    //  Greitaveikos bandymai ir laiko matavimai
            tk.start();
            knygos.sortSystem();
            tk.finish("SysBeCom");
            knygos2.sortSystem(Knyga.pagalKainą);
            tk.finish("SysSuCom");
            knygos3.sortBuble();
            tk.finish("BurBeCom");
            knygos4.sortBuble(Knyga.pagalKainą);
            tk.finish("BurSuCom");
            tk.seriesFinish();
        }
    }
    void tyrimoPasirinkimas(){
        long memTotal = Runtime.getRuntime().totalMemory();
        Ks.oun("memTotal= "+memTotal);
        // Pasižiūrime kaip generuoja šunis (20) vienetų)
        generuotiKnygas(20);
        for(Knyga a: knygos) Ks.oun(a);
        Ks.oun("1 - Pasiruošimas tyrimui - duomenų generavimas");
        Ks.oun("2 - Pasiruošimas tyrimui - šiukšlių surinkimas");
        Ks.oun("3 - Rūšiavimas sisteminiu greitu būdu be Comparator");
        Ks.oun("4 - Rūšiavimas sisteminiu greitu būdu su Comparator");
        Ks.oun("5 - Rūšiavimas List burbuliuku be Comparator");
        Ks.oun("6 - Rūšiavimas List burbuliuku su Comparator");
        Ks.ouf("%6d %7d %7d %7d %7d %7d %7d \n", 0,1,2,3,4,5,6);
        for(int n: tiriamiKiekiai) 
            paprastasTyrimas(n);
        // sekančio tyrimo metu gaunama normalizuoti įvertinimai
        sisteminisTyrimas();
    }
   public static void main(String[] args){
          // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        new SortTyrimas().tyrimoPasirinkimas();
   }    
}
