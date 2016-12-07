/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai išvestinė kompleksinės duomenų struktūros klasė, kuri leidžia
 * papildomai atlikti įvedimo - išvedimo veiksmus su sąrašo elementais.
 * Objektai, kurie bus dedami į sąrašą, turi tenkinti interfeisą KTUable<E>.
 *
 * Užduotis: Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 * Metodų algoritmai yra aptarti paslaitos metu
 ******************************************************************************/
package studijosKTU;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ListKTUx<E extends KTUable<E>> extends ListKTU<E>
        implements Cloneable{
    private E baseObj;       // bazinis objektas skirtas naujų kūrimui

    public ListKTUx(E baseObj) {   // konstruktorius su bazinio objekto
        this.baseObj = baseObj;    // fiksacija dėl naujų elementų kūrimo
    }
    public void add(String dataString) {        // sukuria elementą iš String
        add((E) baseObj.create(dataString)); // ir įdeda jį į pabaigą
    }
    public void load(String fName) {//suformuoja sąrašą iš fName failo
        clear();
        if(fName.length()==0)return;
        if(baseObj==null){          // elementų kūrimui reikalingas baseObj
            Ks.ern("Naudojant load-metodą, "+
                "reikia taikyti konstruktorių = new ListKTU(new Data())");
            System.exit(0);
        }
        try {
            (new File(Ks.getDataFolder())).mkdir();
            String fN = Ks.getDataFolder() + File.separatorChar + fName;
            BufferedReader fReader =
                    new BufferedReader(new FileReader(new File(fN)));
            String dLine;
            while ((dLine = fReader.readLine()) != null) {
                add(dLine);
            }
            fReader.close();
        } catch (FileNotFoundException e) {
            Ks.ern("Duomenų failas " + fName + " nerastas");
//            System.exit(0);
        } catch (IOException e) {
            Ks.ern("Failo " + fName + " skaitymo klaida");
            System.exit(0);
        }
    }
    public void save(String fName) {    // išsaugoja sąrašą faile fName
        PrintWriter fWriter = null;     // tekstiniu formatu
        try {                           // tinkamu vėlesniam skaitymui
            // jei vardo nėra - failas neformuojamas
            if (fName.equals("")) return;

            String fN = Ks.getDataFolder() + File.separatorChar + fName;
            fWriter = new PrintWriter(new FileWriter (new File(fN)));
            for (KTUable d1 = super.get(0); d1 != null; d1=super.getNext()) {
                fWriter.println(d1.toString());
            }
            fWriter.close();
        } catch (IOException e) {
            Ks.ern("!!! Klaida formuojant " + fName + " failą.");
            System.exit(0);
        }
    }
    public void println() {  // sąrašas spausdinamas į Ks.oun("");
        int eilNr=0;
        if (super.isEmpty()){
            Ks.oun("Sąrašas yra tuščias");
        }else
           for (KTUable d1 = super.get(0); d1 != null; d1=super.getNext()) {
           String printData=String.format("%3d: %s ", eilNr++, d1.toString());
           Ks.oun (printData);
        }
        Ks.oun("****** Bendras elementų kiekis yra "+super.size());
    }
    public void println(String title) { // spausdinant galima nurodyti antraštę
        Ks.oun("========"+title+"=======");
        println();
        Ks.oun("======== Sąrašo pabaiga =======");
    }
    @Override
    public ListKTUx<E> clone(){
       ListKTUx<E> cl= (ListKTUx<E>) super.clone();
       cl.baseObj = this.baseObj;
       return cl;
    }
}
