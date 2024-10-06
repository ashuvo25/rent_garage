package com.example.rent_garadge;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatController {

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    @FXML
    private ComboBox<String> dropdownMenu;

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> messageListView; // ListView to display messages

    private PrintWriter out;
    private Socket socket;
    private String clientName;

    @FXML
    public void initialize() {
        dropdownMenu.getItems().addAll("8080", "Dhaka - 8081", "Khulna - 8082");

        // Default server connection
        dropdownMenu.setOnAction(event -> {
            String selectedPort = dropdownMenu.getSelectionModel().getSelectedItem();
            connectToServer(selectedPort);
        });

        // Handle message sending
        sendButton.setOnAction(event -> sendMessage());

        // Handle back button click
        backButton.setOnAction(event -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close(); // Close the chat window
        });

        // By default, connect to the server on port 8080
        connectToServer("8080");
    }

    // Connect to the server
    private void connectToServer(String port) {
        try {
            socket = new Socket("127.0.0.1", Integer.parseInt(port));
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(this::receiveMessages).start(); // Start receiving messages
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send a message to the server
    private void sendMessage() {
        String message = messageField.getText();
        if (message != null && !message.trim().isEmpty()) {
            out.println(message); // Send message to the server
            messageListView.getItems().add("Me: " + message); // Display the sent message
            messageField.clear(); // Clear the input field
        }
    }

    private void receiveMessages() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String receivedMessage;
            while ((receivedMessage = in.readLine()) != null) {
                final String messageToDisplay = receivedMessage;
                Platform.runLater(() -> {
                    messageListView.getItems().add("Other: " + messageToDisplay); // Add received message
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
