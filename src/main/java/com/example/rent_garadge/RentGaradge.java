package com.example.rent_garadge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Map;



public class RentGaradge extends Application {
     static String user_id;
    static Map<String, Object> UserData;
    static Map<String, Object> garageDetails;


//    public static String user_id;

    @Override
    //shuvo
    public void start(Stage stage) throws IOException {
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/rent_garadge/logo.png")));
//
//        stage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(RentGaradge.class.getResource("become.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 500);

//        FirebaseConfig.data();
        System.out.println(FirebaseConfig.db);

        stage.setTitle("ParkMyGarage!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}