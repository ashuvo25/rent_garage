package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class PositionSpecificationControll {

    @FXML
    private TextField garage_height;

    @FXML
    private TextField garage_width;

    @FXML
    private TextField garage_length;

    @FXML
    private TextField describe;

    @FXML
    private Button next_3;

    private Map<String, Object> details;

    // Method to receive the current details map from the previous step
    public void garageDetails(Map<String, Object > details) {
        this.details = details;
    }

    @FXML
    private void initialize() {
        // Set action for the "Next" button
        next_3.setOnAction(_ -> handleNextButton());
    }

    private void handleNextButton() {
        // Collect the data from TextFields
        String height = garage_height.getText().trim();
        String width = garage_width.getText().trim();
        String length = garage_length.getText().trim();
        String description = describe.getText().trim();

        // Validate that height, width, and length are filled in
        if (!height.isEmpty() && !width.isEmpty() && !length.isEmpty()) {
            // Add the values to the details map
            details.put("height", height);
            details.put("width", width);
            details.put("length", length);
            details.put("description", description.isEmpty() ? "Optional" : description);  // Default to "Optional" if empty
            System.out.println(details);
//            FirebaseConfig.datainput("garage_details",RentGaradge.user_id,details);
            // Proceed to the next step
            openNextWindow(details);
        } else {
            // Show an error alert if required fields are empty
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Incomplete Input");
            alert.setContentText("Please fill in all required fields for height, width, and length.");
            alert.showAndWait();
        }

    }

    private void openNextWindow(Map<String, Object> details) {
        try {
            // Load the FXML file and create a new scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("map.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Get the controller after loading the FXML
            map_controller controller = fxmlLoader.getController();

            // Pass the selections to the new controller
            controller.garageDetails(details);

            // Create and set the new stage
            Stage stage = new Stage();
            stage.setTitle("Requirements");
            stage.setScene(scene);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) next_3.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
