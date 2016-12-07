package Lab3Kaltenyte;

/**
 * @author Monika Kaltenyte
 */
import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public final class Knyga implements KTUable<Knyga> {

    // bendri duomenys visiems automobiliams (visai klasei)
    private static final int priimtinųMetųRiba = 1990;
    private static final int esamiMetai = LocalDate.now().getYear();
    private static final double minKaina = 1.0;
    private static final double maxKaina = 333000.0;
    private static final String idCode = "TA";   //  ***** nauja
    private static int serNr = 100;               //  ***** nauja
    private final String knygRegNr;
    private String autorius = "";
    private String pavadinimas = "";
    private int leidMetai = -1;
    private int kodas = -1;
    private double kaina = -1.0;

    public Knyga() {
        knygRegNr = idCode + (serNr++);    // suteikiamas originalus knygRegNr
    }

    public Knyga(String autorius, String pavadinimas,
            int gamMetai, int kodas, double kaina) {
        knygRegNr = idCode + (serNr++);    // suteikiamas originalus knygRegNr
        this.autorius = autorius;
        this.pavadinimas = pavadinimas;
        this.leidMetai = gamMetai;
        this.kodas = kodas;
        this.kaina = kaina;
        validate();
    }

    public Knyga(String dataString) {
        knygRegNr = idCode + (serNr++);    // suteikiamas originalus knygRegNr
        this.parse(dataString);
    }

    public Knyga(Builder builder) {
        knygRegNr = idCode + (serNr++);    // suteikiamas originalus knygRegNr
        this.autorius = builder.autorius;
        this.pavadinimas = builder.pavadinimas;
        this.leidMetai = builder.leidMetai;
        this.kodas = builder.kodas;
        this.kaina = builder.kaina;
        validate();
    }

    @Override
    public Knyga create(String dataString) {
        return new Knyga(dataString);
    }

    @Override
    public String validate() {
        String klaidosTipas = "";
        if (leidMetai < priimtinųMetųRiba || leidMetai > esamiMetai) {
            klaidosTipas = "Netinkami gamybos metai, turi būti ["
                    + priimtinųMetųRiba + ":" + esamiMetai + "]";
        }
        if (kaina < minKaina || kaina > maxKaina) {
            klaidosTipas += " Kaina už leistinų ribų [" + minKaina
                    + ":" + maxKaina + "]";
        }
        return klaidosTipas;
    }

    @Override
    public void parse(String dataString) {
        try {   // ed - tai elementarūs duomenys, atskirti tarpais
            Scanner ed = new Scanner(dataString);
            autorius = ed.next();
            pavadinimas = ed.next();
            leidMetai = ed.nextInt();
            kodas = ed.nextInt();
            kaina = ed.nextDouble();
           // setKodas(ed.nextInt());
            //setKaina(ed.nextDouble());
            validate();
        } catch (InputMismatchException e) {
            Ks.ern("Blogas duomenų formatas apie auto -> " + dataString);
        } catch (NoSuchElementException e) {
            Ks.ern("Trūksta duomenų apie auto -> " + dataString);
        }
    }

    @Override
    public String toString() {  // papildyta su knygRegNr
        return getKnygRegNr() + "=" + autorius + "_" + pavadinimas + ":" + leidMetai + " " + getKodas() + " " + String.format("%4.1f", kaina);
    }

    public String getAutorius() {
        return autorius;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public int getGamMetai() {
        return leidMetai;
    }

    public int getKodas() {
        return kodas;
    }

    public void setKodas(int kodas) {
        this.kodas = kodas;
    }

    public double getKaina() {
        return kaina;
    }

    public void setKaina(double kaina) {
        this.kaina = kaina;
    }

    public String getKnygRegNr() {  //** nauja.
        return knygRegNr;
    }

    @Override
    public int compareTo(Knyga a) {
        return getKnygRegNr().compareTo(a.getKnygRegNr());
    }

    public static Comparator<Knyga> pagalAutoriu = (Knyga a1, Knyga a2) -> a1.autorius.compareTo(a2.autorius); // pradžioje pagal markes, o po to pagal modelius

    public static Comparator<Knyga> pagalKaina = (Knyga a1, Knyga a2) -> {
        // didėjanti tvarka, pradedant nuo mažiausios
        if (a1.kaina < a2.kaina) {
            return -1;
        }
        if (a1.kaina > a2.kaina) {
            return +1;
        }
        return 0;
    };

    public static Comparator<Knyga> pagalMetusKainą = (Knyga a1, Knyga a2) -> {
        // metai mažėjančia tvarka, esant vienodiems lyginama kaina
        if (a1.leidMetai > a2.leidMetai) {
            return +1;
        }
        if (a1.leidMetai < a2.leidMetai) {
            return -1;
        }
        if (a1.kaina > a2.kaina) {
            return +1;
        }
        if (a1.kaina < a2.kaina) {
            return -1;
        }
        return 0;
    };

    public static class Builder {

        private final static Random RANDOM = new Random(1949);  // Atsitiktinių generatorius
        private final static String[][] KNYGOS = { // galimų automobilių markių ir jų modelių masyvas
            {"Egziuper", "Pasaka", "323", "626", "MX6"},
            {"Jungstedt", "Vasara", "Escort", "Focus", "Sierra", "Odiseja"},
            {"Lev_Tolstoj", "Vaiduokliai", "Svajoklis"},
            {"Kahlil_Gibran", "Princas", "Ruduo", "Jazz"},
            {"Hill", "Moteris", "Pasaka", "Iliada", "Scenic"}
        };

        private String autorius = "";
        private String pavadinimas = "";
        private int leidMetai = -1;
        private int kodas = -1;
        private double kaina = -1.0;

        public Knyga build() {
            return new Knyga(this);
        }

        public Knyga buildRandom() {
            int ma = RANDOM.nextInt(KNYGOS.length);        // markės indeksas  0..
            int mo = RANDOM.nextInt(KNYGOS[ma].length - 1) + 1;// modelio indeksas 1..              
            return new Knyga(KNYGOS[ma][0],
                    KNYGOS[ma][mo],
                    1990 + RANDOM.nextInt(20),// metai tarp 1990 ir 2009
                    6 + RANDOM.nextInt(222),// rida tarp 6000 ir 228000
                    5 + RANDOM.nextDouble() * 88);// kaina tarp 800 ir 88800
        }

        public Builder leidMetai(int gamMetai) {
            this.leidMetai = gamMetai;
            return this;
        }

        public Builder autorius(String autorius) {
            this.autorius = autorius;
            return this;
        }

        public Builder pavadinimas(String pavadinimas) {
            this.pavadinimas = pavadinimas;
            return this;
        }

        public Builder kodas(int kodas) {
            this.kodas = kodas;
            return this;
        }

        public Builder kaina(double kaina) {
            this.kaina = kaina;
            return this;
        }
    }
}
