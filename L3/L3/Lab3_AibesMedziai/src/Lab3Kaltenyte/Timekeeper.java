/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab3Kaltenyte;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * @author Monika Kaltenyte
 */
public class Timekeeper {

    int[] tyrimoImtis;
    private final BlockingQueue<String> resultsLogger;
    private final Semaphore semaphore;
    private long startTime, finishTime;
    private final long startTimeTot;
    private long finishTimeTot;
    private double sumTime;
    private int tyrimoInd;
    private int kiekioInd;
    private int tyrimųN;
    private final int tyrimųNmax = 30;
    private final int kiekioN;
    double[][] laikai;
    private String tyrimųEilutė;
    private final String duomFormatas = "%9.6f ";
    private final String normFormatas = "%9.2f ";
    private final String vardoFormatas = "%9s ";
    private final String kiekioFormatas = "%8d(%2d) ";
    private String antraštė = "  kiekis(*k) ";

    public Timekeeper(int[] kiekiai, BlockingQueue resultsLogger, Semaphore semaphore) {
        this.tyrimoImtis = kiekiai;
        this.resultsLogger = resultsLogger;
        this.semaphore = semaphore;
        kiekioN = tyrimoImtis.length;
        laikai = new double[kiekioN][tyrimųNmax];
        startTimeTot = System.nanoTime();
    }

    public void start() throws InterruptedException {
        tyrimoInd = 0;
        if (kiekioInd >= kiekioN) {
            logResult("Duomenų kiekis keičiamas daugiau kartų nei buvo planuota");
            // System.exit(0);        
        }
        tyrimųEilutė = String.format(kiekioFormatas, tyrimoImtis[kiekioInd],
                tyrimoImtis[kiekioInd] / tyrimoImtis[0]);
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        startTime = System.nanoTime();
    }

    public void startAfterPause() {
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        startTime = System.nanoTime();
    }

    public void finish(String vardas) throws InterruptedException {
        finishTime = System.nanoTime();
        double t1 = (finishTime - startTime) / 1e9;
        sumTime += t1;
        if (startTime == 0) {
            logResult("Metodas finish panaudotas be start metodo !!!\n");
            //   System.exit(0);
        }
        if (kiekioInd == 0) {
            antraštė += String.format(vardoFormatas, vardas);
        }
        if (tyrimoInd >= tyrimųNmax) {
            logResult("Jau atlikta daugiau tyrimų nei numatyta  !!!\n");
            //  System.exit(0);
        }
        laikai[kiekioInd][tyrimoInd++] = t1;
        tyrimųEilutė += String.format(duomFormatas, t1);
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        startTime = System.nanoTime();
    }

    public void seriesFinish() throws InterruptedException {
        if (kiekioInd == 0) {
            logResult(antraštė + "\n");
        }
        logResult(tyrimųEilutė + "\n");
        kiekioInd++;
        tyrimųN = tyrimoInd;
        if (kiekioInd == (kiekioN)) {
            summary();
        }
    }

    private void summary() throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        finishTimeTot = System.nanoTime();
        double totTime = (finishTimeTot - startTimeTot) / 1e9;
        sb.append(String.format("       Bendras tyrimo laikas %8.3f sekundžių", totTime)).append("\n");
        sb.append(String.format("    Išmatuotas tyrimo laikas %8.3f sekundžių", sumTime)).append("\n");
        sb.append(String.format("    t.y. %5.1f%% sudaro pagalbiniai darbai",
                (totTime - sumTime) / totTime * 100)).append("\n");
        sb.append("\n");
        sb.append("Normalizuota (santykinė) laikų lentelė\n");
        sb.append(antraštė).append("\n");
        double d1 = laikai[0][0];
        for (int i = 0; i < kiekioN; i++) {
            tyrimųEilutė = String.format(kiekioFormatas, tyrimoImtis[i],
                    tyrimoImtis[i] / tyrimoImtis[0]);
            for (int j = 0; j < tyrimųN; j++) {
                tyrimųEilutė += String.format(normFormatas, laikai[i][j] / d1);
            }
            sb.append(tyrimųEilutė).append("\n");
        }
        sb.append("\n");
        logResult(sb.toString());
    }

    public void logResult(String result) throws InterruptedException {
        resultsLogger.put(result);
        semaphore.acquire();
    }
}