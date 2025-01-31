package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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


    private String previousPage=""; // Store the previous page


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

            // Validate email and password format before proceeding
            if (validateLogin(email, password)) {
                // If validation passes, send the request to Firebase to check credentials
                String emailpass = FirebaseConfig.verifyEmailAndPassword("users", email, password);

                if (emailpass.equals("signin")) {
                     RentGaradge.user_id=email;
                    // Login success, open the home window
                    openHomeWindow();
                    startServer();
                } else if (emailpass.equals("incorrect_pass")) {
                    password_notification.setText("Incorrect password");
                    password_notification.setVisible(true);
                } else if (emailpass.equals("no_email")) {
                    email_notification.setText("Incorrect email");
                    email_notification.setVisible(true);
                } else {
                    System.out.println(emailpass); // Handle any other cases
                }
            }
        });
    }

    public void whichpage(String s) {
        previousPage=s;
    }

    private boolean validateLogin(String email, String password) {
        boolean isValid = true;

        // Reset visibility of error labels
        email_notification.setVisible(false);
        password_notification.setVisible(false);
        invalid.setVisible(false);

        // Check email format
        if (email.isEmpty()) {
            email_notification.setText("Email cannot be empty");
            email_notification.setVisible(true);
            isValid = false;
        } else if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            email_notification.setText("Invalid email format");
            email_notification.setVisible(true);
            isValid = false;
        }

        // Check password format
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
            FXMLLoader fxmlLoader;

            if (previousPage == null || previousPage.isEmpty()) {
                // If previousPage is empty, go to the home page
                fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            } else {
                // Otherwise, go to the previous page
                fxmlLoader = new FXMLLoader(getClass().getResource(previousPage));
            }

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

    private void startServer() {
        // Start server in a new thread
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(8081)) {
                System.out.println("Server started on port 8081");

                while (true) {
                    Socket clientSocket = serverSocket.accept(); // Accept new client connections
                    System.out.println("New client connected: " + clientSocket.getInetAddress());
                    // Handle each client in a new thread (for scalability)
                    new Thread(new ClientHandler(clientSocket)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Inner class to handle each client connection
    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                // Logic to handle client-server communication
                System.out.println("Handling client at " + clientSocket.getInetAddress());
                // Example: read from client, broadcast messages, etc.
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
