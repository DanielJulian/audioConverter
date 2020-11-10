package djaudioconverter.ui;

import djaudioconverter.converter.ConvertionProgressListener;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class Loading implements Runnable {

    private ConvertionProgressListener progressListener;
    private Label label;
    private double progress = 0;

    public Loading(Label label, ConvertionProgressListener progressListener) {
        this.label = label;
        this.progressListener = progressListener;
    }

    @Override
    public void run() {
        while (progress != 100) {
            progress = progressListener.getProgress();
            Platform.runLater(() -> label.setText("Progreso: %" + progress));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Platform.runLater(() -> label.setText("Conversión completa!"));

    }

}
