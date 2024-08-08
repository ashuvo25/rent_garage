package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class signup_controller {

    @FXML
    private Label copyright;

    @FXML
    private ImageView facebook_image;

    @FXML
    private Pane facebook_pane;

    @FXML
    private ImageView github_image;

    @FXML
    private Pane github_pane;

    @FXML
    private ImageView google_image;

    @FXML
    private Pane google_pane;

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
    private PasswordField signup_confrom_password;

    @FXML
    private TextField signup_email;

    @FXML
    private Label signup_name;

    @FXML
    private PasswordField signup_password;

    @FXML
    private Hyperlink singUp_link;

    private void openSignupWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
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
    }

}
