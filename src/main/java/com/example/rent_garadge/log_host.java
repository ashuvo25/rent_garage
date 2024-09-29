package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class log_host{

    @FXML
    private Pane bvbox;

    @FXML
    private Button hom_host;

    @FXML
    private Button hom_log;

    @FXML
    private Button hom_user;


    private void loginScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) hom_log.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }private void mapview() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("webView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Map");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) hom_user.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void initialize() {


        hom_log.setOnAction(event -> loginScreen());
        hom_user.setOnAction(event -> mapview());


    }
}
