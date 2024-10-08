package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class LocationTypeControl {

    @FXML
    private CheckBox residential_id;

    @FXML
    private CheckBox commercial_id;

    @FXML
    private TextField city_id;

    @FXML
    private TextField slot;

    @FXML
    private TextField address_id;

    @FXML
    private Button next_1;

    @FXML
    private Button back_to_pre;

    private Map<String, Object> allgarageDetails;

    @FXML
    private void initialize() {
        // Set up action listener for Next button
        next_1.setOnAction(event -> handleNextButton());
        back_to_pre.setOnAction(event -> backTohome());
    }

    public void garageDetails(Map<String, Object> selections) {
        // Store the selections and print them for verification
        allgarageDetails = selections;
        System.out.println("Received selections: " + selections);
    }

    private void handleNextButton() {
        // Check if at least one checkbox is selected
        boolean isSpaceSelected = residential_id.isSelected() || commercial_id.isSelected();

        // Check if both text fields are filled
        boolean isCityFilled = !city_id.getText().trim().isEmpty();
        boolean isAddressFilled = !address_id.getText().trim().isEmpty();
        boolean isslotFilled = !slot.getText().trim().isEmpty();


        if (isSpaceSelected && isCityFilled && isAddressFilled && isslotFilled ) {
            // Proceed to the next page (implement your navigation logic here)
            // Add the city and address information to the map
            allgarageDetails.put("city", city_id.getText().trim());
            allgarageDetails.put("address", address_id.getText().trim());
            allgarageDetails.put("slot",slot.getText().trim());

            // Include checkpoint data in the allgarageDetails map
            allgarageDetails.put("residential", residential_id.isSelected() ? "yes" : "no");
            allgarageDetails.put("commercial", commercial_id.isSelected() ? "yes" : "no");

            // Print the updated garage details for verification
            System.out.println("Updated garage details: " + allgarageDetails);

            // Open the next window
            openNextWindow(allgarageDetails);
        } else {
            // Show an error alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Incomplete Input");
            alert.setContentText("Please select at least one type of space and fill both location fields.");
            alert.showAndWait();
        }
    }

    private void openNextWindow(Map<String, Object> selections) {
        try {
            // Load the FXML file and create a new scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Requirements.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Get the controller after loading the FXML
            RequirementsController controller = fxmlLoader.getController();

            // Pass the selections to the new controller
            controller.garageDetails(selections);

            // Create and set the new stage
            Stage stage = new Stage();
            stage.setTitle("Requirements");
            stage.setScene(scene);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) next_1.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backTohome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("veichlePositionSelection.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) back_to_pre.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
