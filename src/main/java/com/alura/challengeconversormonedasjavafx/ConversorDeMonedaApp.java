package com.alura.challengeconversormonedasjavafx;

import com.alura.challengeconversormonedasjavafx.controlador.ConversorDeMonedaControlador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConversorDeMonedaApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ConversorDeMonedaApp.class.getResource("vista.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 560, 500);

        ConversorDeMonedaControlador controlador = fxmlLoader.getController();
        controlador.setApiServicio("5e1905de1c64e3ceb9ddf975");

        stage.setTitle("Conversor de Monedas");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
