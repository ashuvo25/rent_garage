package com.example.rent_garadge;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Map;

public class LoginPanelUserControl {

    @FXML
    private Button change_profile;

    @FXML
    private AnchorPane ankor;

    @FXML
    private Pane slide_panel;  // The panel that will slide up and down

    private boolean isSlideVisible = false; // Track the visibility of the panel

    @FXML
    private void initialize() {
        // Initially hide the slide panel by moving it out of view
        slide_panel.setTranslateY(150);  // You can adjust this value depending on the size of the panel
        isSlideVisible = false;  // Initially hidden

        // Set an action for the change_profile button
        change_profile.setOnAction(event -> toggleSlidePanel());
    }

    // Method to toggle the slide panel's visibility
    private void toggleSlidePanel() {
        System.out.println("press");
        TranslateTransition slideTransition = new TranslateTransition(Duration.millis(300), slide_panel);
        System.out.println("press");

        if (isSlideVisible) {
            // If the panel is visible, slide it down to hide it
            slideTransition.setToY(150);  // Move the panel down (hidden)
            isSlideVisible = false;
        } else {
            // If the panel is hidden, slide it up to show it
            slideTransition.setToY(0);    // Move the panel up (visible)
            isSlideVisible = true;
        }

        // Play the slide animation
        slideTransition.play();
    }

    public void garageDetails(Map<String, Object> details) {
    }
}
