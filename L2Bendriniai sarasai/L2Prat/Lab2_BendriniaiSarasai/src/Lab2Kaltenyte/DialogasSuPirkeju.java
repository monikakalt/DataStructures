/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2Kaltenyte;
import java.util.Locale;
import studijosKTU.*;

public class DialogasSuPirkeju {
    Knygynas knygynas = new Knygynas();

   void bendravimasSuKlientu() {
      ListKTUx<Knyga> atranka = null;
      int varNr;  // skaičiavimo varijantas pasirenkamas nurodant jo numerį
      String dialogMeniu = "Pasirinkimas: "
            + "1-skaityti iš failo; 2-papildyti sąrašą; 3-naujų atranka;\n    "
            + "4-atranka pagal kainą; 5-brangiausios knygos 6-pagal markę;\n    "
            + "0-baigti skaičiavimus > ";
      while ((varNr = Ks.giveInt(dialogMeniu, 0, 6)) != 0) {
         if (varNr == 1) {
            knygynas.visosKnygos.load(Ks.giveFileName());
            knygynas.visosKnygos.println("Visų knygų sąrašas");
         } else {
            if (varNr == 2) {
               String autoDuomenys = Ks.giveString("Nurodykite auto markę, "+
                            "modelį, gamybos metus, ridą ir kainą\n ");
               Knyga a = new Knyga();
               a.parse(autoDuomenys);
               String klaidosPožymis = a.validate();
               if (klaidosPožymis.isEmpty()) // dedame tik su gerais duomenimis
                   knygynas.visosKnygos.add(a);
               else
                 Ks.oun("!!! Knyga į sąrašą nepriimtas "+klaidosPožymis);
            } else {  // toliau vykdomos atskiri atrankos metodai
               switch (varNr) {
                  case 3:
                     int nR = Ks.giveInt("Nurodykite knygų metų ribą: ");
                     atranka = knygynas.atrinktiNaujasKnygas(nR);
                     break;
                  case 4:
                     int r1 = Ks.giveInt("Nurodykite apatinę kainos ribą: ");
                     int r2 = Ks.giveInt("Nurodykite viršutinę kainos ribą: ");
                     atranka = knygynas.atrinktiPagalKainą(r1, r2);
                     break;
                  case 5:
                     atranka = knygynas.maksimaliosKainosKnyga();
                     break;
                  case 6:
                     String markė=Ks.giveString("Nurodykite norimą markę ir "+
                             "modelį, atskirtus tarpu: ");
                     atranka = knygynas.atrinktiKnygąPagalMetus("2015");
                     break;
               }
               atranka.println("Štai atrinktų knygų sąrašas");
               atranka.save(Ks.giveString
                     ("Kur saugoti atrinktus auto (jei ne-tai ENTER) ? "));
            }
         }
      }
   }
   public static void main(String[] args) {
          // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        Locale.setDefault(new Locale("LT"));
      new DialogasSuPirkeju().bendravimasSuKlientu();
   }
}
