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

    private Label selectedFileLabel;
    private Label statusLabel;

    public void start() {
        launch();
    }

    public void start(Stage primaryStage) {
        selectedFileLabel = new Label();
        statusLabel = new Label();

        statusLabel.setTranslateX(120);
        statusLabel.setTranslateY(40);
        Button btnOpenDirectoryChooser = new Button();
        btnOpenDirectoryChooser.setTranslateX(120);
        btnOpenDirectoryChooser.setTranslateY(10);
        btnOpenDirectoryChooser.setText("Seleccionar el archivo a convertir");

        btnOpenDirectoryChooser.setOnAction(event -> {
            try {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(primaryStage);

                if (selectedFile == null) {
                    selectedFileLabel.setText("No se selecciono ningun archivo");
                    return;
                }

                selectedFileLabel.setText("Archivo " + selectedFile.getAbsolutePath());
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

        VBox vBox = new VBox();
        vBox.getChildren().addAll(new Node[]{selectedFileLabel, btnOpenDirectoryChooser, statusLabel});
        StackPane root = new StackPane();
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 430.0D, 100.0D);
        primaryStage.setTitle("Conversor a MP3 de Gladys");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private Thread startTask(Runnable task) {
        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
        return backgroundThread;
    }

}
