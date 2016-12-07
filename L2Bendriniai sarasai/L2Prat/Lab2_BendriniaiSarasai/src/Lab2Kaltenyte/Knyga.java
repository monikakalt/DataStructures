package Lab2Kaltenyte;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import studijosKTU.*;


public class Knyga implements KTUable<Knyga> {
    final static private double minKaina = 200.0;
    final static private double maxKaina = 120_000.0;
    // knygos individualūs duomenys
    private String autorius;
    private String pavadinimas;
    private int leidimoMetai;
    private double kaina;
    public Knyga() {
    }
    public Knyga(String dataString) {
        this.parse(dataString);
    }
    Knyga(String autorius, String pavadinimas, int leidimoMetai, double kaina) {
        this.autorius = autorius;
        this.pavadinimas = pavadinimas;
        this.leidimoMetai = leidimoMetai;
        this.kaina = kaina;
    }

    @Override
    public Knyga create(String dataString) {
        Knyga a = new Knyga();
        a.parse(dataString);
        return a;
    }

    @Override
    public final void parse(String dataString) {
        try {   // ed - tai elementarūs duomenys, atskirti tarpais
            Scanner ed = new Scanner(dataString);
            autorius = ed.next();
            pavadinimas = ed.next();
            leidimoMetai = ed.nextInt();
            kaina = ed.nextDouble();
            // setKaina(ed.nextDouble());
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas apie knygą -> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų apie knygą -> " + dataString);
        }
    }

    @Override
    public String toString() {
        return String.format("%-18s %-20s %8d %8.1f ",
                autorius, pavadinimas, leidimoMetai, kaina);
    }
    public String getAutorius() {
        return autorius;
    }
    public String getPavadinimas() {
        return pavadinimas;
    }
    public int getLeidimoMetai() {
        return leidimoMetai;
    }
    public double getKaina() {
        return kaina;
    }

    // keisti bus galima tik kainą - kiti parametrai pastovūs
    public void setKaina(double kaina) {
        this.kaina = kaina;
    }

    @Override
    public int compareTo(Knyga a) {
        // lyginame pagal svarbiausią požymį - kainą
        double kainaKita = a.getKaina();
        if (kaina < kainaKita) {
            return -1;
        }
        if (kaina > kainaKita) {
            return +1;
        }
        return 0;
    }
    // sarankiškai priderinkite prie Lambda funkcijų
    public final static Comparator<Knyga> pagalAutoriųPavadinimą
            = new Comparator<Knyga>() {
        @Override
        public int compare(Knyga a1, Knyga a2) {
            // pradžioje pagal autoriu, o po to pagal pavadinima
            int cmp = a1.getAutorius().compareTo(a2.getAutorius());
            if (cmp != 0) {
                return cmp;
            }
            return a1.getPavadinimas().compareTo(a2.getPavadinimas());
        }
    };
    public final static Comparator pagalKainą = new Comparator() {
        // sarankiškai priderinkite prie generic interfeiso ir Lambda funkcijų
        @Override
        public int compare(Object o1, Object o2) {
            double k1 = ((Knyga) o1).getKaina();
            double k2 = ((Knyga) o2).getKaina();
            // didėjanti tvarka, pradedant nuo mažiausios
            if (k1 < k2) {
                return -1;
            }
            if (k1 > k2) {
                return 1;
            }
            return 0;
        }
    };
    public final static Comparator pagalMetusKainą = new Comparator() {
        // sarankiškai priderinkite prie generic interfeiso ir Lambda funkcijų
        @Override
        public int compare(Object o1, Object o2) {
            Knyga a1 = (Knyga) o1;
            Knyga a2 = (Knyga) o2;
            // metai mažėjančia tvarka, esant vienodiems lyginama kaina
            if (a1.getLeidimoMetai() < a2.getLeidimoMetai()) {
                return 1;
            }
            if (a1.getLeidimoMetai() > a2.getLeidimoMetai()) {
                return -1;
            }
            if (a1.getKaina() < a2.getKaina()) {
                return 1;
            }
            if (a1.getKaina() > a2.getKaina()) {
                return -1;
            }
            return 0;
        }
    };

    // metodas main = tiesiog paprastas pirminis knygų išbandymas
    public static void main(String... args) {
    }

    @Override
    public String validate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
