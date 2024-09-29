package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class PropPageController { // Made the class concrete

    @FXML
    private Pane h_page;

    @FXML
    private Pane prof_details;

    @FXML
    private Circle profile_image_view; // Circle is used instead of ImageView

    @FXML
    private Button back_home;

    @FXML
    private Button comments;

    @FXML
    private Pane details_pane;

    @FXML
    private Text garage_registration_no;

    @FXML
    private Text owner_gmail1;

    @FXML
    private Text owner_gmail11;

    @FXML
    private Text owner_location;

    @FXML
    private Text owner_name;

    @FXML
    private Text owner_phone_number;

    @FXML
    private Button prof_edit;

    @FXML
    private Button prof_edit1;

    @FXML
    private Button prof_edit2;

    @FXML
    private Button prof_edit3;

    @FXML
    private Button profileHistory;

    @FXML
    private Button profile_view;

    @FXML
    private void profileFunct() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("history.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) profileHistory.getScene().getWindow();
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
        profileHistory.setOnAction(event -> profileFunct());
        back_home.setOnAction(event -> backTohome());

    }
}
