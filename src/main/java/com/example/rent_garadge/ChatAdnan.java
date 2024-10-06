package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatAdnan {
    @FXML
    private TextArea textArea;

    @FXML
    private TextField inputField;

    private PrintWriter out;
    private BufferedReader in;

    public void initialize() {
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
            String name = RentGaradge.user_id;
            out.println(name); // Send the name to the server

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSendButton() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            out.println(message);
            inputField.clear();
        }
    }
}
