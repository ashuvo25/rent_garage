package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Login_controller {

    public Label signintext;
    public Pane left_pane;
    public PasswordField signup_password;
    @FXML
    private TextField input_email;

    @FXML
    private PasswordField input_password;

    @FXML
    private Button login;

    @FXML
    private Hyperlink singUp_link;

    @FXML
    private Label email_notification; // The label for email error messages

    @FXML
    private Label password_notification; // The label for password error messages

    @FXML
    private Label invalid; // The label for invalid login error messages

    @FXML
    public void initialize() {


        singUp_link.setOnAction(event -> openSignupWindow());

        login.setOnAction(event -> openHomeWindow());
    }

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

    @FXML
    private void openHomeWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) login.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
