package com.example.rent_garadge;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

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


    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public signup_controller() {
        // Initialize MongoDB connection
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("Rent_Garage_Database"); // Your database name
            collection = database.getCollection("user_data"); // Your collection name
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                storeUserInDatabase(username, email, password);
//                showAlert("Success", "User registered successfully!", AlertType.INFORMATION);
            }
        });
    }

    private boolean validateInputs(String username, String email, String password, String confirmPassword) {
        boolean isValid = true;

        if (username.isEmpty()) {
            username_notificatiobn.setVisible(true);
            isValid = false;
        }

        if (email.isEmpty()) {
            email_error_notificatiobn.setText("Email cannot be empty.");
            email_error_notificatiobn.setVisible(true);
            isValid = false;
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            email_error_notificatiobn.setText("Invalid email format.");
            email_error_notificatiobn.setVisible(true);
            isValid = false;
        }

        if (password.isEmpty()) {
            pass_notificatiobn.setText("Password cannot be empty.");
            pass_notificatiobn.setVisible(true);
            isValid = false;
        } else if (password.length() < 4) {
            pass_notificatiobn.setText("At least 4 characters long.");
            pass_notificatiobn.setVisible(true);
            isValid = false;
        }

        if (!password.equals(confirmPassword)) {
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

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void storeUserInDatabase(String username, String email, String password) {
        if (isEmailRegistered(email)) {
            email_error_notificatiobn.setText("Email is already registered.");
            email_error_notificatiobn.setVisible(true);
        } else {
            try {
                Document newUser = new Document("username", username)
                        .append("email", email)
                        .append("password", password);
                collection.insertOne(newUser);
                openSignupWindow();
//                showAlert("Success", "User registered successfully!", AlertType.INFORMATION);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to register user. Please try again.", AlertType.ERROR);
            }
        }
    }


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
    private boolean isEmailRegistered(String email) {
        Document existingUser = collection.find(new Document("email", email)).first();
        return existingUser != null; // Returns true if the email is already registered
    }

}
