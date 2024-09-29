package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class log_host {

    @FXML
    private Pane bvbox;

    @FXML
    private Button hom_host;

    @FXML
    private Button hom_log;

    @FXML
    private Button hom_user;

    public void initialize() {
        hom_log.setOnAction(event -> loginScreen());
        hom_user.setOnAction(event -> mapview());
        hom_host.setOnAction(event -> host());
    }

    private void loginScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();

            // Close current stage
            Stage currentStage = (Stage) hom_log.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mapview() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("map.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Map View"); // Updated title
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();

            // Close current stage
            Stage currentStage = (Stage) hom_user.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void host() {
        try {
            // Host details loader
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("veichlePositionSelection.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Garage Details");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show(); // Show the new stage

            // Close current stage
            Stage currentStage = (Stage) hom_host.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

}
