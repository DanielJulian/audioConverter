package djaudioconverter.ui;

import djaudioconverter.converter.MP3Converter;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainScreen extends Application {

    ExecutorService executorService = Executors.newFixedThreadPool(3);

    public void start() {
        launch();
    }

    public void start(Stage primaryStage) {
        Label selectedFileLabel = new Label();
        Label statusLabel = new Label();
        statusLabel.setTranslateX(120.0D);
        statusLabel.setTranslateY(40.0D);
        Button btnOpenDirectoryChooser = new Button();
        btnOpenDirectoryChooser.setTranslateX(120.0D);
        btnOpenDirectoryChooser.setTranslateY(10.0D);
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

                Loading loading = new Loading(statusLabel);
                new Thread(loading).start();

                MP3Converter converter = new MP3Converter(selectedFile);
                Future<Boolean> convertionTask = executorService.submit(converter);
                boolean successfulConversion = convertionTask.get();
                loading.stop();

                if (successfulConversion) {
                    statusLabel.setText("Procesamiento finalizado!");
                } else {
                    statusLabel.setText("Ocurrió un error!");
                }

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

}
