package djaudioconverter.ui;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Loading implements Runnable {

    private Label label;
    private String dots = "";
    private volatile boolean running = true;

    public Loading(Label label) {
        this.label = label;
    }

    @Override
    public void run() {
        while (running) {
            if (dots.length() < 3) {
                dots = dots + ".";
            }

            Platform.runLater(() -> label.setText("Procesando" + dots));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void stop() {
        running = false;
    }
}
