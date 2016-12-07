/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2Kaltenyte;

import java.util.Comparator;
import java.util.Locale;
import studijosKTU.*;

public class KnyguBandymai {

    ListKTUx<Knyga> bandomieji = new ListKTUx<>(new Knyga());
    ListKTUx<Knyga> kazkas = new ListKTUx<>(new Knyga());

    void metodoParinkimas() {
        formuotiKnyguSąrašą();
        peržiūrėtiSąrašą();
        papildytiSąrašą();
        patikrintiTurgausApskaitą();
        patikrintiRikiavimą();
    }

    void formuotiKnyguSąrašą() {
        Knyga a1 = new Knyga("Egziuperi Princas 1943 50");
        Knyga a2 = new Knyga("Egziuperi Princas 1943 50");
        Knyga a3 = new Knyga("Egziuperi Princas 1943 50");
        //Knyga a2 = new Knyga("Coelho Alchemikas 2002 12,56");
        //Knyga a3 = new Knyga("Lewis Vienuolis 2016 11,55");

        Knyga a4 = new Knyga("Egziuperi Princas 2002 10,63");
        Knyga a5 = new Knyga("Egziuperi Princas 2002 10,63");
        Knyga a6 = new Knyga("Žemaitė Marti  1990 196 8,99");
        //bandomieji.add("Sepetys Druska 2016 320 13");
        // bandomieji.add("Coelho Alchemikas 2002 12,56");
        bandomieji.add(a1);
        bandomieji.add(a2);
        bandomieji.add(a3);
        bandomieji.println("Pirmos 3 knygos");
        bandomieji.add(a4);
        bandomieji.add(a5);
        bandomieji.add(a6);
        bandomieji.println("Visos 6 knygos");
        bandomieji.forEach(System.out::println);
        // Ks.oun("TRYNIMAS");
        //bandomieji.removeAll(bandomieji);
        bandomieji.forEach(System.out::println);
        Ks.oun("a INDEKSAS");
        Ks.oun(bandomieji.lastIndexOf(a6));
        Ks.oun("Pirmų 3 knygų kainų suma= "
                + (bandomieji.get(0).getKaina() + bandomieji.get(1).getKaina()
                + bandomieji.get(2).getKaina()));

        Ks.oun("Kitų 3 knygų kainų suma= "
                + (bandomieji.get(3).getKaina() + bandomieji.get(4).getKaina()
                + bandomieji.get(5).getKaina()));

        bandomieji.add(6, new Knyga("Žemaitė Marti  1990 8,99"));
        bandomieji.set(4, a3);
        bandomieji.println("Po įterpimų");

        bandomieji.remove(2);
        bandomieji.removeFirst();

        bandomieji.removeFirstOccurence(a2);
        bandomieji.println("Po išmetimų");
        bandomieji.removeAll(bandomieji);
         bandomieji.println("Išmetus viską");
//        bandomieji.remove(0); bandomieji.remove(0); bandomieji.remove(0);
//        bandomieji.remove(0); bandomieji.remove(0); bandomieji.remove(0);
//        bandomieji.println("Po visų išmetimų");
//        bandomieji.remove(0);
//        bandomieji.println("Po visų išmetimų");
    }

    void peržiūrėtiSąrašą() {
        int sk = 0;
        for (Knyga a : bandomieji) {
            if (a.getAutorius().compareTo("Egziuperi") == 0) {
                sk++;
            }
        }
        Ks.oun("Egziuperi knygų yra = " + sk);
    }

    void papildytiSąrašą() {
        for (int i = 0; i < 5; i++) {
            bandomieji.add(new Knyga("Gibran", "Pranašas",
                    2009 - i, 36));
        }
        bandomieji.add("Žemaitė Marti  1990 58,99");
        bandomieji.add("Coelho Alchemikas 2002 12,56");
        bandomieji.add("Vujicic Gyvenimas 2015 11,49");
        bandomieji.add("Egziuperi Citadelė 2002 12,63");
        bandomieji.add("Egziuperi Planeta 2002 18,63");
        bandomieji.add("Egziuperi Princas 2002 10,63");

        bandomieji.println("Testuojamų knygų sąrašas");
        bandomieji.save("ban.txt");
    }

    void patikrintiTurgausApskaitą() {
        Knygynas knygynas = new Knygynas();

        knygynas.visosKnygos.load("ban.txt");
        knygynas.visosKnygos.println("Bandomasis rinkinys");

        bandomieji = knygynas.atrinktiKnygąPagalMetus("2006");
        bandomieji.println("Pradedant nuo 2006");

        bandomieji = knygynas.atrinktiPagalKainą(5, 15);
        bandomieji.println("Kaina tarp 5 ir 15");

        bandomieji = knygynas.maksimaliosKainosKnyga();
        bandomieji.println("Pačios brangiausios");

        bandomieji = knygynas.atrinktiKnygąPagalMetus("2015");
        bandomieji.println("Turi būti tik 2015 metų knygos");
        int sk = 0;
        for (Knyga a : bandomieji) {
            sk++;    // testuojame ciklo veikimą
        }
        Ks.oun("2015 metų knygų kiekis = " + sk);
    }

    // išbandykite veikimą, o po to pakeiskite į Lambda stiliaus komparatorius.
    void patikrintiRikiavimą() {
        Knygynas aps = new Knygynas();

        aps.visosKnygos.load("ban.txt");
        Ks.oun("========" + aps.visosKnygos.get(0));
        aps.visosKnygos.println("Bandomasis rinkinys");
        aps.visosKnygos.sortBuble(Knyga.pagalAutoriųPavadinimą);
        aps.visosKnygos.println("Rūšiavimas pagal Autorių ir pavadinimą");
        aps.visosKnygos.sortBuble(Knyga.pagalKainą);
        aps.visosKnygos.println("Rūšiavimas pagal kainą");
        aps.visosKnygos.sortBuble(Knyga.pagalMetusKainą);
        aps.visosKnygos.println("Rūšiavimas pagal Metus ir Kainą");

//        aps.visosKnygos.sortBuble((a, b) -> Integer.compare(a.getPsl(), b.getPsl()));
//        aps.visosKnygos.println("Rūšiavimas pagal Psl");
        aps.visosKnygos.sortBuble();
        aps.visosKnygos.println("Rūšiavimas pagal compareTo - Kainą");
    }

   
    public static void main(String... args) {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        new KnyguBandymai().metodoParinkimas();
    }
}
