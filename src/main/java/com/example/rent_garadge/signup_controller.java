package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class signup_controller {

    @FXML
    private Label copyright;

    @FXML
    private Pane facebook_pane;

    @FXML
    private Pane github_pane;

    @FXML
    private Pane google_pane;

    @FXML
    private Pane left_pane;

    @FXML
    private Button login;

    @FXML
    private Button google_button;

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
    private PasswordField signup_password;

    @FXML
    private TextField signup_Username;

    @FXML
    private Hyperlink singUp_link;

    @FXML
    private Label email_error_notificatiobn, username_notificatiobn, conf_pass_notificatiobn, pass_notificatiobn;

    @FXML
    public void initialize() {
        singUp_link.setOnAction(event -> openSignupWindow());

        login.setOnAction(event -> {
            String username = signup_Username.getText();
            String email = signup_email.getText();
            String password = signup_password.getText();
            String confirmPassword = signup_confrom_password.getText();

            // Clear notifications before validation
            hideAllNotifications();

            // Validate user input and show notification if there's an issue
            if (validateInputs(username, email, password, confirmPassword)) {
                // Prepare the data to store in Firebase
                Map<String, Object> userData = new HashMap<>();
                userData.put("username", username);
                userData.put("email", email);
                userData.put("password", password);  // You may want to hash the password in real applications

                // Call the datainput method to store the data
                String isDataStored = FirebaseConfig.datainput("users", email, userData);

                if (isDataStored.equals("signup")) {
                    // If successful, open the signup window or display a success message
                    openSignupWindow();
                } else if(isDataStored.equals("duplicate")) {
                    email_error_notificatiobn.setText("Email Already Registered");
                    email_error_notificatiobn.setVisible(true);
                }
            }
        });
    }

    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        boolean isValid = true;

        if (username.isEmpty()) {
            username_notificatiobn.setText("Username cannot be empty.");
            username_notificatiobn.setVisible(true);
            isValid = false;
        }

        // Check email format
        if (email.isEmpty()) {
            email_error_notificatiobn.setText("Email cannot be empty");
            email_error_notificatiobn.setVisible(true);
            isValid = false;
        } else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            email_error_notificatiobn.setText("Invalid email format");
            email_error_notificatiobn.setVisible(true);
            isValid = false;
        }

        // Password validation
        if (password.isEmpty()) {
            pass_notificatiobn.setText("Password cannot be empty.");
            pass_notificatiobn.setVisible(true);
            isValid = false;
        } else if (password.length() < 4) {
            pass_notificatiobn.setText("Password must be at least 4 characters long.");
            pass_notificatiobn.setVisible(true);
            isValid = false;
        }

        // Confirm password validation
        if (!password.equals(confirmPassword)) {
            conf_pass_notificatiobn.setText("Passwords do not match.");
            conf_pass_notificatiobn.setVisible(true);
            isValid = false;
        }

        return isValid;
    }

    // Hide all notification labels
    private void hideAllNotifications() {
        email_error_notificatiobn.setVisible(false);
        username_notificatiobn.setVisible(false);
        conf_pass_notificatiobn.setVisible(false);
        pass_notificatiobn.setVisible(false);
    }

    private void openSignupWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
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
}
