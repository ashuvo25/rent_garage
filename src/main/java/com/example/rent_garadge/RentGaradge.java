package com.example.rent_garadge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class RentGaradge extends Application {
    static String user_id;
    static Map<String, Object> UserData;
    static Map<String, Object> garageDetails;

    private static int clientCount = 0;
    private static Hashtable<Socket, String> clients = new Hashtable<>();
    private static List<String> messageHistory = new ArrayList<>();
    private static final String LOG_FILE = "database.txt";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RentGaradge.class.getResource("become.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 360, 500);

        stage.setTitle("ParkMyGarage!");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();

    }


}
