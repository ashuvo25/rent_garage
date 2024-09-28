package com.example.FirebasePushExample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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
        // Setup hover effects for the epn panes
        setupHoverEffect(epn1, "Garage", "#ffcccc");
        setupHoverEffect(epn2, "Notifications", "#ccffcc");
        setupHoverEffect(epn3, "Payments", "#ccccff");
        setupHoverEffect(epn4, "History", "#ffe28a");
    }

    private void setupHoverEffect(Pane pane, String labelText, String color) {
        // On mouse enter, show tooltip pane and change pane color
        pane.setOnMouseEntered(event -> {
            showHoverPane(pane, labelText, color);
            pane.setStyle("-fx-background-color: " + color + ";"); // Change pane color on hover
        });

        // On mouse exit, hide tooltip pane and revert pane color
        pane.setOnMouseExited(event -> {
            hideHoverPane();
            pane.setStyle("-fx-background-color: transparent;"); // Revert color on exit
        });
    }

    private void showHoverPane(Pane pane, String labelText, String color) {
        hover_pane.setVisible(true);
        hover_pane.setLayoutX(pane.getLayoutX() + 60); // Adjust the tooltip pane's X position
        hover_pane.setLayoutY(pane.getLayoutY());
        hover_pane.setStyle("-fx-background-color: #ffffff; -fx-border-color: " + color + ";");
        change_users.setText(labelText); // Set the text of the hover pane
    }

    private void hideHoverPane() {
        hover_pane.setVisible(false); // Hide the tooltip when not hovering
    }





}
