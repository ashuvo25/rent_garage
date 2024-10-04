package com.example.rent_garadge;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Button updown_maps;

    @FXML
    private Pane map_view;
    @FXML
    private Button dropdown_map;

    @FXML
    private Button back_home;
    @FXML
    private Pane rent_background;
    private boolean isRentBackgroundVisible = false;
    @FXML
    private  void initialize() {
        bike_buttons.setOnAction(event -> handleBikeButtonClick());
        car_buttons.setOnAction(event -> handleCarButtonClick());
        home_btn4.setOnAction(event -> profileFunct());
        home_btn.setOnAction(event -> homescreen());
        rent_background.setLayoutY(420);
        map_view.setVisible(true);
        dropdown_map.setVisible(false);
        updown_maps.setVisible(true);
        back_home.setOnAction(event -> backTohome());
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

    @FXML
    private void handleUpdownMaps() {
        if (!isRentBackgroundVisible) {
            map_view.setVisible(false);
            TranslateTransition transitionUp = new TranslateTransition(Duration.millis(500), rent_background);
            transitionUp.setToY(-340);
            transitionUp.play();

            updown_maps.setVisible(false);
            dropdown_map.setVisible(true);

            isRentBackgroundVisible = true;
        }
    }

    @FXML
    private void handleDropdownMap() {
        if (isRentBackgroundVisible) {

            TranslateTransition transitionDown = new TranslateTransition(Duration.millis(500), rent_background);
            transitionDown.setToY(-30);
            transitionDown.play();
            transitionDown.setOnFinished(e -> map_view.setVisible(true));
            dropdown_map.setVisible(false);
            updown_maps.setVisible(true);

            isRentBackgroundVisible = false;
        }
    }
    private void backTohome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) back_home.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
