package djaudioconverter.ui;

import djaudioconverter.converter.ConvertionProgressListener;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class Loading implements Runnable {

    private ConvertionProgressListener progressListener;
    private Label label;
    private double progress = 0;
    private ProgressBar progressBar;

    public Loading(ProgressBar progressBar, Label label, ConvertionProgressListener progressListener) {
        this.label = label;
        this.progressListener = progressListener;
        this.progressBar = progressBar;
    }

    @Override
    public void run() {
        progressBar.setVisible(true);
        Platform.runLater(() -> label.setText("Convirtiendo..."));
        while (progress != 1) {
            progress = progressListener.getProgress();
            System.out.println(progress);
            Platform.runLater(() -> progressBar.setProgress(progress));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        progressBar.setVisible(false);
        Platform.runLater(() -> label.setText("Conversión completa!"));

    }

}
