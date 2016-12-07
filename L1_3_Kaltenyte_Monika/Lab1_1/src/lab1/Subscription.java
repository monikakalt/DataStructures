package lab1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Subscription {

    List<Resident> residents = new ArrayList<>();
    List<Magazine> magazines = new ArrayList<>();

    public List<Resident> readResidents(String filePath) throws FileNotFoundException, IOException {
        String line;

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null) {
            Resident Resident = new Resident(line);
            residents.add(Resident);
        }
        return residents;
    }

    public List<Magazine> readMagazines(String filePath) throws FileNotFoundException, IOException {
        String line;

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null) {
            Magazine magazine = new Magazine(line);
            magazines.add(magazine);
        }
        return magazines;
    }

    public static double expenses(List<Magazine> magazines, List<Resident> residents) {
        double allExpenses = 0;
        for (Resident r : residents) {
            for (Magazine m : magazines) {
                if (r.getTitle().equals(m.getMagazine())) {
                    allExpenses += (r.getDuration() * m.getPrice());
                }
            }
        }
        return allExpenses;
    }

    public static void allMonths(List<Resident> residents, List<Magazine> magazines) {
        int temp = 0;
        for (Magazine m : magazines) {
            temp = 0;
            for (Resident r : residents) {
                if (m.getMagazine().equals(r.getTitle())) {
                    int d = r.getDuration();
                    temp += d;
                }
                m.setAllMonths(temp);
            }
        }
    }

    public static void allIncome(List<Resident> residents, List<Magazine> magazines) {
        for (Magazine m : magazines) {
            m.setIncome(m.getAllMonths() * m.getPrice());
        }
        for (Magazine m : magazines) {
            System.out.println(m.toString());
        }
    }

    public static void toConsole() throws IOException {
        Subscription ResidentsReader = new Subscription();
        Subscription MagazinesReader = new Subscription();
        List<Resident> residents = ResidentsReader.readResidents("F:\\2 KURSAS\\DUOMENŲ STRUKTŪROS\\Lab1_1\\gyventojai.txt");
        List<Magazine> magazines = MagazinesReader.readMagazines("F:\\2 KURSAS\\DUOMENŲ STRUKTŪROS\\Lab1_1\\dienrasciai.txt");
        Subscription.allMonths(residents, magazines);
        Subscription.allIncome(residents, magazines);
        System.out.println("Total expenses: " + Subscription.expenses(magazines, residents));
    }

}
