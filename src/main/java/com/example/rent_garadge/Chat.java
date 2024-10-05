package com.example.rent_garadge;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Chat extends Application {
    private PrintWriter out;
    private BufferedReader in;

    private TextArea textArea;
    private TextField inputField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Client");

        textArea = new TextArea();
        textArea.setEditable(false);

        inputField = new TextField();
        inputField.setPromptText("Type your message...");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage());

        VBox vbox = new VBox(textArea, inputField, sendButton);
        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Replace "localhost" with the server's IP address on the same Wi-Fi network
        String serverIp = JOptionPane.showInputDialog("Enter the server's IP address:");

        try {
            Socket socket = new Socket(serverIp, 12345);  // Use the server IP here
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start a thread to listen for messages from the server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        textArea.appendText(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Prompt for the client's name
            String name = JOptionPane.showInputDialog("Enter your name:");
            out.println(name); // Send the name to the server

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            out.println(message);
            inputField.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}