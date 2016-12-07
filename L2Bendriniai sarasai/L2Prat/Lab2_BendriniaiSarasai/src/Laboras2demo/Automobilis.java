/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai yra demonstracinė Automobilio klasė (jos objektai dedami į ListKTU)
 *       kuri realizuoja interfeisą KTUable<T>
   *  IŠSIAIŠKINKITE metodų sudarymą, jų paskirtį ir atitikimą KTUable.
   *  IŠBANDYKITE jų veikimą, panaudojant klasę AutomobiliuBandymai.
   *  PASIRINKITE savo objektų klasę ir sudarykite analogiškus metodus
   *  GERESNIAM ĮSISAVINIMUI rekomenduojame pradėti nuo tuščios klasės
   ****************************************************************************/

package Laboras2demo;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import studijosKTU.*;

public class Automobilis implements KTUable<Automobilis> {
    
    // bendri duomenys visiems automobiliams (visai klasei)
    final static private int priimtinųMetųRiba = 1994;
    final static private int esamiMetai  = LocalDate.now().getYear();
    
    final static private double valKursas = esamiMetai <= 2014? 1: 1/3.4528;
    final static private double minKaina =     200.0;
    final static private double maxKaina = 120_000.0;

    // kiekvieno automobilio individualūs duomenys
    private String markė;
    private String modelis;
    private int gamMetai;  
    private int rida;
    private double kaina; 

    
    public Automobilis() {
    }
    public Automobilis(String markė, String modelis,
                        int gamMetai, int rida, double kaina){
        this.markė = markė;
        this.modelis = modelis;
        this.gamMetai = gamMetai;
        this.rida = rida;
        this.kaina = kaina;
    }
    public Automobilis(String dataString){
        this.parse(dataString);
    }
    @Override
    public Automobilis create(String dataString) {
        Automobilis a = new Automobilis();
        a.parse(dataString);
        return a;
    }
    @Override
    public final void parse(String dataString) {
        try {   // ed - tai elementarūs duomenys, atskirti tarpais
            Scanner ed = new Scanner(dataString);
            markė   = ed.next();
            modelis = ed.next();
            gamMetai= ed.nextInt();
            rida    = ed.nextInt();
            setKaina(ed.nextDouble());
        } catch (InputMismatchException  e) {
            Ks.ern("Blogas duomenų formatas apie auto -> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų apie auto -> " + dataString);
        }
    }
    @Override
    public String validate() {
        String klaidosTipas = "";
        if (gamMetai < priimtinųMetųRiba || gamMetai > esamiMetai)
           klaidosTipas = "Netinkami gamybos metai, turi būti [" +
                   priimtinųMetųRiba + ":" + esamiMetai + "]";
        if (kaina < minKaina || kaina > maxKaina )
            klaidosTipas += " Kaina už leistinų ribų [" + minKaina +
                            ":" + maxKaina  + "]";
        return klaidosTipas;
    }
    @Override
    public String toString(){  // surenkama visa reikalinga informacija
        return String.format("%-8s %-8s %4d %7d %8.1f %s",
               markė, modelis, gamMetai, rida, kaina, validate());
    };
    public String getMarkė() {
        return markė;
    }
    public String getModelis() {
        return modelis;
    }
    public int getGamMetai() {
        return gamMetai;
    }
    public int getRida() {
        return rida;
    }
    public double getKaina() {
        return kaina;
    }
    // keisti bus galima tik kainą - kiti parametrai pastovūs
    public void setKaina(double kaina) {
        this.kaina = kaina;
    }
    @Override
    public int compareTo(Automobilis a) { 
        // lyginame pagal svarbiausią požymį - kainą
        double kainaKita = a.getKaina();
        if (kaina < kainaKita) return -1;
        if (kaina > kainaKita) return +1;
        return 0;
    }
    // sarankiškai priderinkite prie Lambda funkcijų
    public final static Comparator<Automobilis> pagalMarkęModelį =
              new Comparator<Automobilis>() {
       @Override
       public int compare(Automobilis a1, Automobilis a2) {
          // pradžioje pagal markes, o po to pagal modelius
          int cmp = a1.getMarkė().compareTo(a2.getMarkė());
          if(cmp != 0) return cmp;
          return a1.getModelis().compareTo(a2.getModelis());
       }
    };
    public final static Comparator pagalKainą = new Comparator() {
       // sarankiškai priderinkite prie generic interfeiso ir Lambda funkcijų
       @Override
       public int compare(Object o1, Object o2) {
          double k1 = ((Automobilis) o1).getKaina();
          double k2 = ((Automobilis) o2).getKaina();
          // didėjanti tvarka, pradedant nuo mažiausios
          if(k1<k2) return -1;
          if(k1>k2) return 1;
          return 0;
       }
    };
    public final static Comparator pagalMetusKainą = new Comparator() {
       // sarankiškai priderinkite prie generic interfeiso ir Lambda funkcijų
       @Override
       public int compare(Object o1, Object o2) {
          Automobilis a1 = (Automobilis) o1;
          Automobilis a2 = (Automobilis) o2;
          // metai mažėjančia tvarka, esant vienodiems lyginama kaina
          if(a1.getGamMetai() < a2.getGamMetai()) return 1;
          if(a1.getGamMetai() > a2.getGamMetai()) return -1;
          if(a1.getKaina() < a2.getKaina()) return 1;
          if(a1.getKaina() > a2.getKaina()) return -1;
          return 0;
       }
    };
    // metodas main = tiesiog paprastas pirminis automobilių išbandymas
    public static void main(String... args){
          // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT")); 
        Automobilis a1 = new Automobilis("Renault","Laguna", 1997, 50000,  599);
        Automobilis a2 = new Automobilis("Renault","Megane", 2015, 20000, 3500);
        Automobilis a3 = new Automobilis();
        Automobilis a4 = new Automobilis();
        a3.parse("Toyota Corolla 2001 20000 8500,8");
        a4.parse("Toyota Avensis 1990 20000  500,8");
        Ks.oun(a1);
        Ks.oun(a2);
        Ks.oun(a3);
        Ks.oun(a4);
    }    
}
