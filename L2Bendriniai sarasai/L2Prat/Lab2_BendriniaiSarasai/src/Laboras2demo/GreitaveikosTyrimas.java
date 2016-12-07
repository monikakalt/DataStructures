/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai yra darbo su sąrašais greitaveikos tyrimo klasė.
 * Pavyzdyje pateiktas rikiavimo metodų tyrimas.
 * Tyrimo metu pateiktais metodais naudojamasi kaip šablonais,
 * išbandant įvairius rūšiavimo aspektus.
   *  IŠSIAIŠKINKITE metodų sudarymą, jų paskirtį.
   *  SUDARYKITE sąrašo peržiūros antišablono efektyvumo tyrimą.
   *  PASIRINKITE savo objektų klasę ir sudarykite jų generavimo metodą.
   ****************************************************************************/
package Laboras2demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import studijosKTU.*;
/*
 */
public class GreitaveikosTyrimas {
    Automobilis[] autoBazė1;
    ListKTU<Automobilis> aSeries = new ListKTU<>();
    Random ag = new Random();  // atsitiktinių generatorius
    int[] tiriamiKiekiai = {2_000, 4_000, 8_000, 16_000};
//    pabandykite, gal Jūsų kompiuteris įveiks šiuos eksperimentus
//    paieškokite ekstremalaus apkrovimo be burbuliuko metodo
//    static int[] tiriamiKiekiai = {10_000, 20_000, 40_000, 80_000};
    void generuotiAutomobilius(int kiekis){
       String[][] am = { // galimų automobilių markių ir jų modelių masyvas
          {"Mazda", "121", "323", "626", "MX6"},
          {"Ford", "Fiesta", "Escort", "Focus", "Sierra", "Mondeo"},
          {"Saab", "92", "96"},
          {"Honda", "Accord", "Civic", "Jazz"},
          {"Renault", "Laguna", "Megane", "Twingo", "Scenic"},
          {"Peugeot", "206", "207", "307"}
       };
        autoBazė1= new Automobilis[kiekis];
        ag.setSeed(2017);
        for(int i=0;i<kiekis;i++){
            int ma = ag.nextInt(am.length);        // markės indeksas  0..
            int mo = ag.nextInt(am[ma].length-1)+1;// modelio indeksas 1..
            autoBazė1[i]= new Automobilis(am[ma][0], am[ma][mo],
                1994 + ag.nextInt(20),           // metai tarp 1994 ir 2013
                6000 + ag.nextInt(222_000),      // rida tarp 6000 ir 228000
                1000 + ag.nextDouble()*350_000); // kaina tarp 1000 ir 351_000
        }
        Collections.shuffle(Arrays.asList(autoBazė1));
        aSeries.clear();
        for(Automobilis a: autoBazė1) aSeries.add(a);
    }
    void paprastasTyrimas(int elementųKiekis){
// Paruošiamoji tyrimo dalis
        long t0=System.nanoTime();
        generuotiAutomobilius(elementųKiekis);
        ListKTU<Automobilis> aSeries2 = aSeries.clone();
        ListKTU<Automobilis> aSeries3 = aSeries.clone();
        ListKTU<Automobilis> aSeries4 = aSeries.clone();
        long t1=System.nanoTime();
        System.gc(); System.gc(); System.gc();
        long t2=System.nanoTime();
//  Greitaveikos bandymai ir laiko matavimai
        aSeries.sortSystem();
        long t3=System.nanoTime();
        aSeries2.sortSystem(Automobilis.pagalKainą);
        long t4=System.nanoTime();
        aSeries3.sortBuble();
        long t5=System.nanoTime();
        aSeries4.sortBuble(Automobilis.pagalKainą);
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
           generuotiAutomobilius(kiekis);
           ListKTU<Automobilis> aSeries2 = aSeries.clone();
           ListKTU<Automobilis> aSeries3 = aSeries.clone();
           ListKTU<Automobilis> aSeries4 = aSeries.clone();

    //  Greitaveikos bandymai ir laiko matavimai
            tk.start();
            aSeries.sortSystem();
            tk.finish("SysBeCom");
            aSeries2.sortSystem(Automobilis.pagalKainą);
            tk.finish("SysSuCom");
            aSeries3.sortBuble();
            tk.finish("BurBeCom");
            aSeries4.sortBuble(Automobilis.pagalKainą);
            tk.finish("BurSuCom");
            tk.seriesFinish();
        }
    }
    void tyrimoPasirinkimas(){
        long memTotal = Runtime.getRuntime().totalMemory();
        Ks.oun("memTotal= "+memTotal);
        // Pasižiūrime kaip generuoja automobilius (20) vienetų)
        generuotiAutomobilius(20);
        for(Automobilis a: aSeries) Ks.oun(a);
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
        new GreitaveikosTyrimas().tyrimoPasirinkimas();
   }    
}
