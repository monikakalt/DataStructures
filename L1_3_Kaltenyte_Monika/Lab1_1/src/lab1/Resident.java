package lab1;

import java.util.Scanner;

public class Resident {

    private String name;
    private String title;
    private int month;
    private int duration;

    public Resident() {
    }

    public Resident(String name, String t, int month, int duration) {
        this.name = name;
        this.title = t;
        this.month = month;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public int getMonth() {
        return month;
    }

    public int getDuration() {
        return duration;
    }

    public Resident(String data) {
        Scanner sc = new Scanner(data);
        sc.useDelimiter(";");
        try {
            this.name = sc.next();
            this.title = sc.next();
            this.month = sc.nextInt();
            this.duration = sc.nextInt();
        } catch (Exception e) {
            System.out.println("error in residents data");
        }
    }
}
