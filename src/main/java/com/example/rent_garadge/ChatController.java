package com.example.rent_garadge;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ChatController {

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<?> dropdownMenu;

    @FXML
    private TextField messageField;

    @FXML
    private ListView<String> messageListView; // Changed to String to store messages

    @FXML
    private Button sendButton;
    String user_id = RentGaradge.user_id;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    Map<String, Object> UserData=RentGaradge.UserData;
    // Define the username for the current client
    private String username = user_id; // Set this as desired
    private  String owner_name ;
    public void initialize() {
        // Start the socket connection when the controller is initialized
        startConnection();
        startRealTimeFileWatcher("database.txt");
        // Set up the action for the send button
        sendButton.setOnAction(event -> sendNewMessage());
        backButton.setOnAction(event -> backTohome());

        if (UserData != null) {
            owner_name = RentGaradge.UserData.get("username")+"";
        }

        messageListView.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String message, boolean empty) {
                super.updateItem(message, empty);

                if (empty || message == null) {
                    setGraphic(null);
                    setText(null);
                    setStyle(""); // Clear styling for empty cells
                } else {
                    // Create a vertical box to hold the sender and message text
                    VBox vBox = new VBox();

                    // Split the message to get the sender and content
                    String sender = message.split(":")[0].trim(); // Extract sender
                    String messageContent = message.substring(sender.length() + 1).trim(); // Extract message content

                    // Create Text nodes for sender and message content
                    Text senderText = new Text(sender + ":");
                    Text messageText = new Text(messageContent);

                    // Set sender text style to bold
                    senderText.setStyle("-fx-font-weight: bold;");

                    // Add sender and message text to the VBox
                    vBox.getChildren().add(senderText);
                    vBox.getChildren().add(messageText);

                    // Check if the message is from the current user
                    if (sender.equals(username)) {
                        // Your message (align left)
                        vBox.setStyle("-fx-alignment: CENTER-LEFT; -fx-background-color: #d1e7dd; -fx-padding: 10px; -fx-border-radius: 10px;");
                    } else {
                        // Other user's message (align right)
                        vBox.setStyle("-fx-alignment: CENTER-RIGHT; -fx-background-color: #f8d7da; -fx-padding: 10px; -fx-border-radius: 10px;");
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                    }

                    // Set the VBox as the graphic for the ListCell
                    setGraphic(vBox);
                }
            }
        });


    }

    private void startConnection() {
        // Check if the socket is already open
        if (socket != null && !socket.isClosed()) {
            return; // Prevent creating a new socket if already connected
        }

        try {
            socket = new Socket("127.0.0.1", 8080);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        // Update the message list view in JavaFX
                        // Format: "username: message"
                        String[] messageParts = serverMessage.split(": ", 2);
                        String formattedMessage;

                        if (messageParts.length == 2) {
                            // Check if the message is from the current user
                            if (messageParts[0].equals(username)) {
                                formattedMessage =user_id + ": " + messageParts[1];
                            } else {
                                formattedMessage = messageParts[0] + ": " + messageParts[1];
                            }
                        } else {
                            formattedMessage = serverMessage; // Just in case format is unexpected
                        }

                        // Add to ListView
                        messageListView.getItems().add(formattedMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection(); // Ensure resources are closed
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startRealTimeFileWatcher(String filePath) {
        Thread fileWatcherThread = new Thread(() -> {
            try {
                while (true) {
                    Platform.runLater(() -> {
                        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
                            String message;
                            ObservableList<String> messages = messageListView.getItems();

                            // Read each line (message) from the file
                            while ((message = fileReader.readLine()) != null) {
                                // Add message to the ListView
                                messages.add(message);

                                // Limit the ListView to the last 20 messages
                                if (messages.size() > 20) {
                                    messages.remove(0); // Remove the oldest message
                                }

                                // Auto-scroll to the bottom when new messages arrive
                                messageListView.scrollTo(messages.size() - 1);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    // Pause for a while before checking again (e.g., 2 seconds)
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        fileWatcherThread.setDaemon(true); // Ensures the thread stops when the application exits
        fileWatcherThread.start(); // Start the file watcher thread
    }



//    private boolean isMessageRelevant(String message) {
//
//        return true;
//    }

    private void sendNewMessage() {
        String message = messageField.getText().trim(); // Get the text from the input field
        if (!message.isEmpty()) {
            if (out != null) {
                // Format the message to send
                String formattedMessage = username + ": " + message;
                out.println(formattedMessage); // Send formatted message to the server

                // Add the message to the list view for display
                messageListView.getItems().add(user_id +": " + message); // Display in local view
                messageField.clear(); // Clear the message field after sending

                // Optionally, write the new message to the database.txt
                try (PrintWriter fileWriter = new PrintWriter(new FileWriter("database.txt", true))) {
                    fileWriter.println(formattedMessage); // Append new message to database.txt
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void updateMessageListView(String message) {
        // Add the message to the ListView
        messageListView.getItems().add(message);
    }
    private void closeConnection() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Optional: Method to send messages from a file
    private void sendMessageFromFile(String filePath) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String message;
            while ((message = fileReader.readLine()) != null) {
                if (out != null) {
                    String formattedMessage = username + ": " + message; // Include username
                    out.println(formattedMessage);
                    messageListView.getItems().add(user_id +": " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void backTohome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
