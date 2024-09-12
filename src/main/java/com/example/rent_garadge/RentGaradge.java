package com.example.rent_garadge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class RentGaradge extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RentGaradge.class.getResource("prev_imgs.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 500);


        stage.setTitle("RENT GARADGE!");
        stage.setScene(scene);
        stage.show();
    }
// hellooo Adnan
    public static void main(String[] args) {
        launch();
    }
}