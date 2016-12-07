/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai yra demonstracinė Automobilio bandymų klasė, kuri skirta tiesiog
 * veiksmų su sąrašais išbandymui
   *  IŠSIAIŠKINKITE metodų sudarymą, jų paskirtį.
   *  PASIRINKITE savo objektų klasę ir sudarykite analogiškus metodus
   *  GERESNIAM ĮSISAVINIMUI rekomenduojame pradėti nuo tuščios klasės
   ****************************************************************************/
package Laboras2demo;
import java.util.Comparator;
import java.util.Locale;
import studijosKTU.*;

public class AutomobiliuBandymai{
    ListKTUx<Automobilis> bandomieji = new ListKTUx<>(new Automobilis());

    void metodoParinkimas(){
       //tikrintiAtskirusAuto();
        formuotiAutoSąrašą();
//        peržiūrėtiSąrašą();
//        papildytiSąrašą();
     //   patikrintiTurgausApskaitą();
  //     patikrintiRikiavimą();
    }

    void tikrintiAtskirusAuto() {
        Automobilis a1 = new Automobilis("Renault","Laguna",1997,50000,1700);
        Automobilis a2 = new Automobilis("Renault","Megane",2001,20000,3500);
        Automobilis a3 = new Automobilis("Toyota","Corolla",2001,20000,8500.8);
        Automobilis a4 = new Automobilis();
        Automobilis a5 = new Automobilis();
        Automobilis a6 = new Automobilis();
        a4.parse("Renault Laguna 2001 115900 7500");
        a5.parse("Renault Megane 1946 365100 9500");
        a6.parse("Honda   Civic  2007  36400 8500,3");

        Ks.oun(a1);
        Ks.oun(a2);
        Ks.oun(a3);
        Ks.oun("Pirmų 3 auto ridos vidurkis= "+
                (a1.getRida()+a2.getRida()+a3.getRida())/3);
        Ks.oun(a4);
        Ks.oun(a5);
        Ks.oun(a6);
        Ks.oun("Kitų 3 auto kainų suma= "+
                (a4.getKaina()+a5.getKaina()+a6.getKaina()));
    }
    void formuotiAutoSąrašą() {
        Automobilis a1 = new Automobilis("Renault","Laguna",1997,50000,1700);
        Automobilis a2 = new Automobilis("Renault","Megane",2001,20000,3500);
        Automobilis a3 = new Automobilis("Toyota","Corolla",2001,20000,8500.8);
        bandomieji.add(a1);
        bandomieji.add(a2);
        bandomieji.add(a3);
        bandomieji.println("Pirmi 3 auto");
        bandomieji.add("Renault Laguna 2001 115900 7500");
        bandomieji.add("Renault Megane 1946 365100 9500");
        bandomieji.add("Honda   Civic  2007  36400 8500,3");

        bandomieji.println("Visi 6 auto");
        bandomieji.forEach(System.out::println);
        Ks.oun("Pirmų 3 auto ridos vidurkis= "+
                (bandomieji.get(0).getRida()+bandomieji.get(1).getRida()+
                 bandomieji.get(2).getRida())/3);

        Ks.oun("Kitų 3 auto kainų suma= "+
                (bandomieji.get(3).getKaina()+bandomieji.get(4).getKaina()+
                 bandomieji.get(5).getKaina()));
        // palaipsniui atidenkite sekančias eilutes ir išbandykite
//        bandomieji.add(0, new Automobilis("Mazda","6",2007,50000,27000));
//        bandomieji.add(6, new Automobilis("Hyundai","Lantra",1998,9500,777));
//        bandomieji.set(4, a3);
//        bandomieji.println("Po įterpimų");
//        bandomieji.remove(7);
//        bandomieji.remove(0);
//        bandomieji.println("Po išmetimų");
//        bandomieji.remove(0); bandomieji.remove(0); bandomieji.remove(0);
//        bandomieji.remove(0); bandomieji.remove(0); bandomieji.remove(0);
//        bandomieji.println("Po visų išmetimų");
//        bandomieji.remove(0);
//        bandomieji.println("Po visų išmetimų");
   }
    void peržiūrėtiSąrašą(){
        int sk=0;
        for (Automobilis a: bandomieji){
            if (a.getMarkė().compareTo("Renault")==0)
                sk++;
        }
        Ks.oun("Renault automobilių yra = "+sk);
    }
    void papildytiSąrašą(){
        for (int i=0; i<8; i++){
            bandomieji.add(new Automobilis("Ford", "Focus",
                    2009-i, 40000+i*10000, 36000-i*2000));
        }
        bandomieji.add("Ford Mondeo  2009 37000 36000.0");
        bandomieji.add("Fiat Bravo   2008 27000 32500,0");
        bandomieji.add("Ford Fiesta  2009 37000 16000,0");
        bandomieji.add("Audi A6      2006 87000 36000,0");
        bandomieji.println("Testuojamų automobilių sąrašas");
        bandomieji.save("ban.txt");
    }
    void patikrintiTurgausApskaitą(){
        AutomobiliuTurgus aTurgus = new AutomobiliuTurgus();
        
        aTurgus.visiAuto.load("ban.txt");
        aTurgus.visiAuto.println("Bandomasis rinkinys");

        bandomieji = aTurgus.atrinktiNaujusAuto(2001);
        bandomieji.println("Pradedant nuo 2001");

        bandomieji = aTurgus.atrinktiPagalKainą(3000, 10000);
        bandomieji.println("Kaina tarp 3000 ir 10000");

        bandomieji = aTurgus.maksimaliosKainosAuto();
        bandomieji.println("Patys brangiausi");

        bandomieji = aTurgus.atrinktiMarkęModelį("F");
        bandomieji.println("Turi būti tik Fiatai ir Fordai");

        bandomieji = aTurgus.atrinktiMarkęModelį("Ford M");

        bandomieji.println("Turi būti tik Ford Mondeo");
        int sk=0;
        for (Automobilis a: bandomieji){
                sk++;    // testuojame ciklo veikimą
        }
        Ks.oun("Ford Mondeo kiekis = "+sk);
    }
    // išbandykite veikimą, o po to pakeiskite į Lambda stiliaus komparatorius.
    void patikrintiRikiavimą(){
        AutomobiliuTurgus aps = new AutomobiliuTurgus();

        aps.visiAuto.load("ban.txt");
        Ks.oun("========"+aps.visiAuto.get(0));
        aps.visiAuto.println("Bandomasis rinkinys");
        aps.visiAuto.sortBuble(Automobilis.pagalMarkęModelį);
        aps.visiAuto.println("Rūšiavimas pagal Markę ir Modelį");
        aps.visiAuto.sortBuble(Automobilis.pagalKainą);
        aps.visiAuto.println("Rūšiavimas pagal kainą");
        aps.visiAuto.sortBuble(Automobilis.pagalMetusKainą);
        aps.visiAuto.println("Rūšiavimas pagal Metus ir Kainą");
        aps.visiAuto.sortBuble(tvarkaPagalRidą);
        aps.visiAuto.sortBuble((a, b) -> Integer.compare(a.getRida(), b.getRida()));
        aps.visiAuto.println("Rūšiavimas pagal Ridą");
        aps.visiAuto.sortBuble();
        aps.visiAuto.println("Rūšiavimas pagal compareTo - Kainą");
    }

    static Comparator tvarkaPagalRidą = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            int r1 = ((Automobilis) o1).getRida();
            int r2 = ((Automobilis) o2).getRida();
            // rida atvirkščia mažėjančia tvarka, pradedant nuo didžiausios
            if(r1<r2) return 1;
            if(r1>r2) return -1;
            return 0;
        }
    };
    public static void main(String... args) {
          // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
        new AutomobiliuBandymai().metodoParinkimas();
    }
}
