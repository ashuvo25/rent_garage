package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Host_details_controll {

//    @FXML
//    private CheckBox outdoor_id;
//
//    @FXML
//    private CheckBox indor_id;
//
//    @FXML
//    private CheckBox car_id;
//
//    @FXML
//    private CheckBox bike_id;
//
//    @FXML
//    private Button next_0;
//
//    // This method is called by the FXMLLoader when initialization is complete
//    @FXML
//    public void initialize() {
//        // Add logic to handle the "Next" button click
//        next_0.setOnAction(event -> handleNextAction());
//    }
//
//    // This method is triggered when the "Next" button is clicked
//    private void handleNextAction() {
//        // Get the selections from checkboxes
//        boolean isOutdoorSelected = outdoor_id.isSelected();
//        boolean isIndoorSelected = indor_id.isSelected();
//        boolean isCarSelected = car_id.isSelected();
//        boolean isBikeSelected = bike_id.isSelected();
//
//        // Validate the user's input
//        if (!isOutdoorSelected && !isIndoorSelected) {
//            showAlert("Selection Error", "Please select either Outdoor or Indoor parking.");
//            return;
//        }
//
//        if (!isCarSelected && !isBikeSelected) {
//            showAlert("Selection Error", "Please select either Car or Bike.");
//            return;
//        }
//
//        // You can add further logic here to handle the selected options, such as passing data
//        // to the next screen or processing it.
//
//        String parkingType = isOutdoorSelected ? "Outdoor" : "Indoor";
//        String vehicleType = isCarSelected ? "Car" : "Bike";
//
//        showAlert("Selection Confirmed", "You have selected " + parkingType + " parking for a " + vehicleType + ".");
//    }
//
//    // Utility method to show alerts
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
}
