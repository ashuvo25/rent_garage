package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class home_controller {

    @FXML
    private Label change_users;

    @FXML
    private Button home_btn;

    @FXML
    private Button home_btn1;

    @FXML
    private Button home_btn2;

    @FXML
    private Button home_btn3;

    @FXML
    private Button home_btn4;
    @FXML
    private Button add_garage;
    @FXML
    private Button running_garage;

    @FXML
    private Pane home_options;

    @FXML
    private Pane nav_bar;

    @FXML
    private Pane epn1;

    @FXML
    private Pane epn2;

    @FXML
    private Pane epn3;

    @FXML
    private Pane epn4;

    // Additional Pane for showing tooltips
    @FXML
    private Pane hover_pane;

    public void initialize() {

          home_btn4.setOnAction(event -> profileFunct());
          home_btn2.setOnAction(event -> rent_page());
          home_btn1.setOnAction(event -> globalChat());
        add_garage.setOnAction(event -> addGarage());
        home_btn3.setOnAction(event -> notififff());
        running_garage.setOnAction(event -> canclefunct());
//          hideHoverPane();
    }

    private void globalChat() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GlobalChat.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
            startServer();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) home_btn1.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void profileFunct() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profilepage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) home_btn4.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    private  void rent_page(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("rent_page.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Rent");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) home_btn2.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }    private  void addGarage(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("veichlePositionSelection.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add garage");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) add_garage.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
  private  void canclefunct(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cencle.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add garage");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) running_garage.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    private  void notififff(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NotificationPanel.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Notification");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) home_btn3.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

//    private void showHoverPane(Pane pane, String labelText, String color) {
//        hover_pane.setVisible(true);
//        hover_pane.setLayoutX(pane.getLayoutX() + 60);
//        hover_pane.setLayoutY(pane.getLayoutY());
//        hover_pane.setStyle("-fx-background-color: #ffffff; -fx-border-color: " + color + ";");
//        change_users.setText(labelText);
//    }

//    private void hideHoverPane() {
//        hover_pane.setVisible(false); // Hide the tooltip when not hovering
//    }

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
