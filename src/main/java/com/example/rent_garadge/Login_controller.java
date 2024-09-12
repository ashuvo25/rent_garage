package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Login_controller {

    @FXML
    private Label copyright;

    @FXML
    private Pane facebook_pane;

    @FXML
    private Pane github_pane;

    @FXML
    private ImageView google_image;

    @FXML
    private ImageView google_image1;

    @FXML
    private ImageView google_image2;

    @FXML
    private Pane google_pane;

    @FXML
    private TextField input_email;

    @FXML
    private PasswordField input_password;

    @FXML
    private Pane left_pane;

    @FXML
    private Button login;

    @FXML
    private Pane logo_name;

    @FXML
    private Label name;

    @FXML
    private Pane right_pane;

    @FXML
    private Label signintext;

    @FXML
    private Hyperlink singUp_link;

    @FXML
    private void openSignupWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) singUp_link.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
//   @FXML
//    public void initialize() {
//       singUp_link.setOnAction(event -> openSignupWindow());
//    }


    @FXML
    private void openHomeWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Rent GaraDge");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) singUp_link.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    @FXML
    public void initialize() {
        singUp_link.setOnAction(event -> openSignupWindow());
        login.setOnAction(event -> openHomeWindow());
    }

}
