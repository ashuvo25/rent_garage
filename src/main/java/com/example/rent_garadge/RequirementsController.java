package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class RequirementsController {

    @FXML
    private CheckBox smokefree;

    @FXML
    private CheckBox smokeditection;

    @FXML
    private CheckBox camera;

    @FXML
    private CheckBox climatecontrolled;

    @FXML
    private CheckBox lockarea;

    @FXML
    private CheckBox privateentrnce;

    @FXML
    private Button next_2;
  @FXML
    private Button back_to_pre;

    private Map<String, Object> allgarageDetails;

    // Method to receive the current selections map
    public void garageDetails(Map<String, Object> allgarageDetails) {
        this.allgarageDetails = allgarageDetails;
    }

    @FXML
    private void initialize() {
        // Set action on the "Next" button to handle the checkpoint selections
        next_2.setOnAction(event -> handleNextButton());
        back_to_pre.setOnAction(event -> backTohome());
    }

    private void handleNextButton() {
        // Store the state of each checkpoint in the map ("yes" if selected, "no" if not)
        allgarageDetails.put("smokeFree", smokefree.isSelected() ? "yes" : "no");
        allgarageDetails.put("smokeDetection", smokeditection.isSelected() ? "yes" : "no");
        allgarageDetails.put("camera", camera.isSelected() ? "yes" : "no");
        allgarageDetails.put("climateControlled", climatecontrolled.isSelected() ? "yes" : "no");
        allgarageDetails.put("lockedArea", lockarea.isSelected() ? "yes" : "no");
        allgarageDetails.put("privateEntrance", privateentrnce.isSelected() ? "yes" : "no");

        // Print the updated map for verification
        System.out.println("Updated garage details with checkpoints: " + allgarageDetails);

        // Proceed to the next step or handle navigation logic here
        openNextWindow(allgarageDetails);
    }

    private void openNextWindow(Map<String, Object> selections) {
        try {
            // Load the FXML file and create a new scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Position_Specification.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Get the controller after loading the FXML
            PositionSpecificationControll controller = fxmlLoader.getController();

            // Pass the selections to the new controller
            controller.garageDetails(selections);

            // Create and set the new stage
            Stage stage = new Stage();
            stage.setTitle("Requirements");
            stage.setScene(scene);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) next_2.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backTohome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LocationType.fxml"));
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
