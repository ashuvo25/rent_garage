package com.example.rent_garadge;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bson.Document;

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
    private Label invalid; // The label for password error messages

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public Login_controller() {
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
        // Initially hide the error messages
        email_notification.setVisible(false);
        password_notification.setVisible(false);
        invalid.setVisible(false);

        singUp_link.setOnAction(event -> openSignupWindow());

        // Handle login button click
        login.setOnAction(event -> {
            String email = input_email.getText();
            String password = input_password.getText();

            if (validateLogin(email, password)) {
                if (authenticateUser(email, password)) {
//                    showAlert("Success", "Login successful!", Alert.AlertType.INFORMATION);
                    openHomeWindow();
                } else {
//                    showAlert("Login Failed", "Invalid email or password", Alert.AlertType.ERROR);
                    invalid.setText("Invalid email or password");
                    invalid.setVisible(true);
                }
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

    private boolean authenticateUser(String email, String password) {
        Document query = new Document("email", email).append("password", password);
        Document user = collection.find(query).first();

        return user != null; // Return true if user is found, false otherwise
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
