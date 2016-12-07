/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Kaltenyte;
/**
 * @author Monika Kaltenyte
 */
import laborai.studijosktu.BstSetKTU;
import laborai.studijosktu.SetADT;

public class KnyguApskaita {

    public static SetADT<String> knyguMarkes(Knyga[] auto) {
        SetADT<Knyga> uni = new BstSetKTU<>(Knyga.pagalAutoriu);
        SetADT<String> kart = new BstSetKTU<>();
        for (Knyga a : auto) {
            int sizeBefore = uni.size();
            uni.add(a);

            if (sizeBefore == uni.size()) {
                kart.add(a.getAutorius());
            }
        }
        return kart;
    }
}
