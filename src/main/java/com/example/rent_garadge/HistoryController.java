package com.example.rent_garadge;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoryController {

    @FXML
    private Button back_home;

    @FXML
    private Button comments;

    @FXML
    private Pane history_pane;

    @FXML
    private Pane history_pane1;

    @FXML
    private Pane history_pane2;

    @FXML
    private Button profileHistory;

    @FXML
    private Button profile_view;

    private void profileFunct() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profilepage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) profile_view.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
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


    @FXML
    public void initialize() {
        profile_view.setOnAction(event -> profileFunct());
        back_home.setOnAction(event -> backTohome());

    }

}

