package djaudioconverter.ui;

import djaudioconverter.converter.ConvertionProgressListener;
import djaudioconverter.converter.MP3Converter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainScreen extends Application {

    private Label statusLabel;

    public void start() {
        launch();
    }

    public void start(Stage primaryStage) {
        statusLabel = new Label();

        statusLabel.setTranslateX(120);
        statusLabel.setTranslateY(40);
        Button directoryButton = new Button();
        configureDirectoryButton(directoryButton, primaryStage);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(directoryButton, statusLabel);
        StackPane root = new StackPane();
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 430.0D, 100.0D);
        primaryStage.setTitle("Conversor a MP3");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void startTask(Runnable task) {
        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

    private void configureDirectoryButton(Button directoryButton, Stage primaryStage) {
        directoryButton.setTranslateX(120);
        directoryButton.setTranslateY(10);
        directoryButton.setText("Seleccioná el archivo a convertir");

        directoryButton.setOnAction(event -> {
            try {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(primaryStage);

                if (selectedFile == null) {
                    statusLabel.setText("No se selecciono ningun archivo");
                    return;
                }

                ConvertionProgressListener progressListener = new ConvertionProgressListener();
                MP3Converter converter = new MP3Converter(selectedFile, progressListener);
                startTask(converter);

                Loading loading = new Loading(statusLabel, progressListener);
                startTask(loading);

            } catch (Exception var14) {
                var14.printStackTrace();
                statusLabel.setText("Ocurrió un error!");
            }
        });
    }

}
