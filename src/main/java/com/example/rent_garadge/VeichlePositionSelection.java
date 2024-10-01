package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VeichlePositionSelection {

    @FXML
    private CheckBox indoor_id;

    @FXML
    private CheckBox outdoor_id;

    @FXML
    private CheckBox bike_id;

    @FXML
    private CheckBox car_id;

    @FXML
    private Button next_0;

    @FXML
    private void initialize() {
        // Set up action listener for Next button
        next_0.setOnAction(event -> handleNextButton());
    }

    private void handleNextButton() {
        // Check if one from each category is selected
        boolean isIndoorOutdoorSelected = indoor_id.isSelected() || outdoor_id.isSelected();
        boolean isBikeCarSelected = bike_id.isSelected() || car_id.isSelected();

        if (isIndoorOutdoorSelected && isBikeCarSelected) {
            // Collect selections into a Map of String, String
            Map<String, Object> selections = new HashMap<>();
            if (indoor_id.isSelected()) {
                selections.put("position", "indoor");
            } else {
                selections.put("position", "outdoor");
            }

            if(bike_id.isSelected()&&car_id.isSelected()){
                selections.put("vehicle", "both");
            }
            else if (bike_id.isSelected()) {
                selections.put("vehicle", "bike");
            } else {
                selections.put("vehicle", "car");
            }

            Location_Type(selections);
        } else {
            // Show an error alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setHeaderText("Incomplete Selection");
            alert.setContentText("Please select one option from both 'Indoor/Outdoor' and 'Bike/Car' categories.");
            alert.showAndWait();
        }
    }

    @FXML
    private void Location_Type(Map<String, Object> selections) {
        try {
            // Load the FXML file and create a new scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LocationType.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Get the controller after loading the FXML
            LocationTypeControl controller = fxmlLoader.getController();

            // Pass the selections to the new controller
            controller.garageDetails(selections);

            // Create and set the new stage
            Stage stage = new Stage();
            stage.setTitle("Garage and Vehicle type");
            stage.setScene(scene);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) next_0.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
