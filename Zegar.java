package sample;

import javafx.scene.control.Label;


import static java.lang.Thread.sleep;

public class Zegar extends Thread {

    private int totalSec;
    Label zegarek;

    public Zegar(int min, Label zegarek) {
        this.totalSec = 60 * min;
        this.zegarek = zegarek;
        zegarek.setText(formatTime());
    }

    @Override
    public void run() {
        while (totalSec != 0) {
            try {
                this.totalSec--;
//                zegarek.setText(formatTime());
                sleep(1000);
                System.out.println(totalSec / 60 + ":" + String.format("%02d", totalSec % 60));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("Koniec czasu!!!");

    }

    private String formatTime() {
        return totalSec / 60 + ":" + String.format("%02d", totalSec % 60);
    }
}
