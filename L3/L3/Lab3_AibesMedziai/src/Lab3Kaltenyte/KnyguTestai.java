/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Kaltenyte;
/**
 * @author Monika Kaltenyte
 */
import laborai.studijosktu.Ks;
import laborai.studijosktu.AvlSetKTUx;
import laborai.studijosktu.SortedSetADTx;
import laborai.studijosktu.SetADT;
import laborai.studijosktu.BstSetKTUx;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

/*
 * Aibės testavimas be Swing'o
 *
 */
public class KnyguTestai {

    static Knyga[] knyguBaze;
    static SortedSetADTx<Knyga> aSerija = new BstSetKTUx(new Knyga(), Knyga.pagalKaina);

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus
        aibėsTestas();
        
    }

    static SortedSetADTx generuotiAibe(int kiekis, int generN) {
        knyguBaze = new Knyga[generN];
        
        for (int i = 0; i < generN; i++) {
            knyguBaze[i] = new Knyga.Builder().buildRandom();
        }
        Collections.shuffle(Arrays.asList(knyguBaze));
        aSerija.clear();
        for (int i = 0; i < kiekis; i++) {
            aSerija.add(knyguBaze[i]);
        }
        return aSerija;
    }

    public static void aibėsTestas() throws CloneNotSupportedException {
        Knyga a1 = new Knyga("Egziuperi", "Princas", 1999,12, 19);
        Knyga a2 = new Knyga.Builder()
                .autorius("Egziuperi")
                .pavadinimas("Princas")
                .leidMetai(2001)
                .kodas(11)
                .kaina(3500)
                .build(); 
        Knyga a3 = new Knyga.Builder().buildRandom();
        Knyga a4 = new Knyga("Jungstedt Pasaka 2001 11 7");
        Knyga a5 = new Knyga("Tolstoj Menulis 1946 22 15");
        Knyga a6 = new Knyga("Escort Vasara  2001 33 72");
        Knyga a7 = new Knyga("Zemaite Sapnai 2001 44 26");
        Knyga a8 = new Knyga("Vasaris Ruduo 1946  55 9");
        Knyga a9 = new Knyga("Tolstoj Gyvenimas  2007 66 85");
        

        Knyga[] knyguMasyvas = {a9, a7, a8, a5, a1, a6};

        Ks.oun("Knygu Aibė:");
        SortedSetADTx<Knyga> knyguAibe = new BstSetKTUx(new Knyga());

        for (Knyga a : knyguMasyvas) {
            knyguAibe.add(a);
            Ks.oun("Aibė papildoma: " + a + ". Jos dydis: " + knyguAibe.size());
        }
        Ks.oun("");
        Ks.oun(knyguAibe.toVisualizedString(""));

        SortedSetADTx<Knyga> knyguAibeKopija
                = (SortedSetADTx<Knyga>) knyguAibe.clone();

        knyguAibeKopija.add(a2);
        knyguAibeKopija.add(a3);
        knyguAibeKopija.add(a4);
        knyguAibeKopija.tailSet(a9);//<-------------------------------------------------------
        Ks.oun("Papildyta autoaibės kopija:");
        Ks.oun(knyguAibeKopija.toVisualizedString(""));

        //a9.setRida(10000);

        Ks.oun("Originalas:");
        Ks.ounn(knyguAibe.toVisualizedString(""));

        Ks.oun("Ar elementai egzistuoja aibėje?");
        for (Knyga a : knyguMasyvas) {
            Ks.oun(a + ": " + knyguAibe.contains(a));
        }
        Ks.oun(a2 + ": " + knyguAibe.contains(a2));
        Ks.oun(a3 + ": " + knyguAibe.contains(a3));
        Ks.oun(a4 + ": " + knyguAibe.contains(a4));
        Ks.oun("");

        Ks.oun("Ar elementai egzistuoja aibės kopijoje?");
        for (Knyga a : knyguMasyvas) {
            Ks.oun(a + ": " + knyguAibeKopija.contains(a));
        }
        Ks.oun(a2 + ": " + knyguAibeKopija.contains(a2));
        Ks.oun(a3 + ": " + knyguAibeKopija.contains(a3));
        Ks.oun(a4 + ": " + knyguAibeKopija.contains(a4));
        Ks.oun("");

        Ks.oun("Elementų šalinimas iš kopijos. Aibės dydis prieš šalinimą:  " + knyguAibeKopija.size());
        for (Knyga a : new Knyga[]{a2, a1, a9, a8, a5, a3, a4, a2, a7, a6, a7, a9}) {
            knyguAibeKopija.remove(a);
            Ks.oun("Iš knyguaibės kopijos pašalinama: " + a + ". Jos dydis: " + knyguAibeKopija.size());
        }
        Ks.oun("");

        Ks.oun("Knygų aibė su iteratoriumi:");
        Ks.oun("");
        for (Knyga a : knyguAibe) {
            Ks.oun(a);
        }
        Ks.oun("");
        Ks.oun("Knygų aibė AVL-medyje:");
        SortedSetADTx<Knyga> knyguAibe3 = new AvlSetKTUx(new Knyga());
        for (Knyga a : knyguMasyvas) {
            knyguAibe3.add(a);
        }
        Ks.ounn(knyguAibe3.toVisualizedString(""));

        Ks.oun("Knygų aibė su iteratoriumi:");
        Ks.oun("");
        for (Knyga a : knyguAibe3) {
            Ks.oun(a);
        }

        Ks.oun("");
        Ks.oun("Knygų aibė su atvirkštiniu iteratoriumi:");
        Ks.oun("");
        Iterator iter = knyguAibe3.descendingIterator();
        while (iter.hasNext()) {
            Ks.oun(iter.next());
        }

        Ks.oun("");
        Ks.oun("Knygų aibės toString() metodas:");
        Ks.ounn(knyguAibe3);

        // Išvalome ir suformuojame aibes skaitydami iš failo
        knyguAibe.clear();
        knyguAibe3.clear();

       
        SetADT<String> knyguAibe4 = KnyguApskaita.knyguMarkes(knyguMasyvas);
        Ks.oun("Pasikartojantys autoriai:\n" + knyguAibe4.toString());
        
    }
}
