package com.example.FirebasePushExample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Login_controller {

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
        // Initially hide the error messages
        email_notification.setVisible(false);
        password_notification.setVisible(false);
        invalid.setVisible(false);

        singUp_link.setOnAction(event -> openSignupWindow());

        // Handle login button click
        login.setOnAction(event -> {
            String email = input_email.getText();
            String password = input_password.getText();
            openHomeWindow();
            if (validateLogin(email, password)) {
                // Simulate login success by opening the home window
                openHomeWindow();
            } else {
                invalid.setText("Invalid email or password");
                invalid.setVisible(true);
            }
        });
    }

    private boolean validateLogin(String email, String password) {
        boolean isValid = true;

        // Reset visibility of error labels
        email_notification.setVisible(false);
        password_notification.setVisible(false);

        if (email.isEmpty()) {
            email_notification.setText("Email cannot be empty");
            email_notification.setVisible(true);
            isValid = false;
        } else if (!email.contains("@") || !email.contains(".")) {
            email_notification.setText("Invalid email format");
            email_notification.setVisible(true);
            isValid = false;
        }

        if (password.isEmpty()) {
            password_notification.setText("Password cannot be empty");
            password_notification.setVisible(true);
            isValid = false;
        } else if (password.length() < 4) {
            password_notification.setText("Password must be at least 4 characters");
            password_notification.setVisible(true);
            isValid = false;
        }

        return isValid;
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Rent Garage");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) login.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
