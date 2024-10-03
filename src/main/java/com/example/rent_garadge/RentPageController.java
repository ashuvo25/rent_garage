package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class RentPageController {
    @FXML
    private Button bike_buttons;

    @FXML
    private Button car_buttons;

    @FXML
    private Button home_btn;

    @FXML
    private Button home_btn1;

    @FXML
    private Button home_btn2;

    @FXML
    private Button home_btn3;

    @FXML
    private Button home_btn4;

    @FXML
    private Pane nav_bar;

    @FXML
    private Pane rent_background;

    @FXML
    private  void initialize() {
        bike_buttons.setOnAction(event -> handleBikeButtonClick());
        car_buttons.setOnAction(event -> handleCarButtonClick());
        home_btn4.setOnAction(event -> profileFunct());
        home_btn.setOnAction(event -> homescreen());
    }
    @FXML
    private void handleCarButtonClick() {
        car_buttons.setStyle("-fx-background-color: #5c5cba;");
        bike_buttons.setStyle("-fx-background-color: #fdfeff;");
    }
    @FXML
    private void handleBikeButtonClick() {
        bike_buttons.setStyle("-fx-background-color: #5c5cba;");
        car_buttons.setStyle("-fx-background-color: #fdfeff;");
    }



    private void profileFunct() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profilepage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) home_btn4.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private  void  homescreen(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) home_btn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
