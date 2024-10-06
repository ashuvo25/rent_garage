package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class home_controller {

    @FXML
    private Label change_users;

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
    private Pane home_options;

    @FXML
    private Pane nav_bar;

    @FXML
    private Pane epn1;

    @FXML
    private Pane epn2;

    @FXML
    private Pane epn3;

    @FXML
    private Pane epn4;

    // Additional Pane for showing tooltips
    @FXML
    private Pane hover_pane;

    public void initialize() {

          home_btn4.setOnAction(event -> profileFunct());
          home_btn2.setOnAction(event -> rent_page());
          home_btn1.setOnAction(event -> globalChat());
//          hideHoverPane();
    }

    private void globalChat() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Chat.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) home_btn1.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
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
    private  void rent_page(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rent_page.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) home_btn2.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

//    private void showHoverPane(Pane pane, String labelText, String color) {
//        hover_pane.setVisible(true);
//        hover_pane.setLayoutX(pane.getLayoutX() + 60);
//        hover_pane.setLayoutY(pane.getLayoutY());
//        hover_pane.setStyle("-fx-background-color: #ffffff; -fx-border-color: " + color + ";");
//        change_users.setText(labelText);
//    }

//    private void hideHoverPane() {
//        hover_pane.setVisible(false); // Hide the tooltip when not hovering
//    }





}
