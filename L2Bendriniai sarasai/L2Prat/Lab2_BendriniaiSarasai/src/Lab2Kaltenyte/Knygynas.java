/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab2Kaltenyte;

import studijosKTU.*;

public class Knygynas {

    public ListKTUx<Knyga> visosKnygos = new ListKTUx<>(new Knyga());
    private static final Knyga bazinisEgz = new Knyga();

    // suformuojamas sąrašas knygu, kurie pagaminti vėliau nei riba
    public ListKTUx<Knyga> atrinktiNaujasKnygas(int riba) {
        ListKTUx<Knyga> naujosKnygos = new ListKTUx<>(bazinisEgz);
        for (Knyga a : visosKnygos) {
            if (a.getLeidimoMetai() >= riba) {
                naujosKnygos.add(a);
            }
        }
        return naujosKnygos;
    }
    // suformuojamas sąrašas knygų, kurių kaina yra tarp ribų
    public ListKTUx<Knyga> atrinktiPagalKainą(int riba1, int riba2) {
        ListKTUx<Knyga> vidutinesKnygos = new ListKTUx(bazinisEgz);
        for (Knyga a : visosKnygos) {
            if (a.getKaina() >= riba1 && a.getKaina() <= riba2) {
                vidutinesKnygos.add(a);
            }
        }
        return vidutinesKnygos;
    }
    // suformuojamas sąrašas knygų, turinčių max kainą
    public ListKTUx<Knyga> maksimaliosKainosKnyga() {
        ListKTUx<Knyga> brangiausiosKnygos = new ListKTUx(bazinisEgz);
        // formuojamas sąrašas su maksimalia reikšme vienos peržiūros metu
        double maxKaina = 0;
        for (Knyga a : visosKnygos) {
            double kaina = a.getKaina();
            if (kaina >= maxKaina) {
                if (kaina > maxKaina) {
                    brangiausiosKnygos.clear();
                    maxKaina = kaina;
                }
                brangiausiosKnygos.add(a);
            }
        }
        return brangiausiosKnygos;
    }
    // suformuojams sąrašas knyga, kurių metai atitinka nurodytą
    public ListKTUx<Knyga> atrinktiKnygąPagalMetus(String metai) {
        ListKTUx<Knyga> metuKnygos = new ListKTUx(bazinisEgz);
        for (Knyga a : visosKnygos) {
            String pilnasModelis = a.getAutorius() + " " + a.getPavadinimas()+" "+a.getLeidimoMetai();
            if (pilnasModelis.contains(metai)) {
                metuKnygos.add(a);
            }
        }
        return metuKnygos;
    }
   
}
