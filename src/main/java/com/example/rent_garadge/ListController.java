package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class ListController {

    // Linking the UI elements from the FXML file using the @FXML annotation
    @FXML
    private Pane garage_list;

    @FXML
    private Text garage_address;

    @FXML
    private Text fare;

    @FXML
    private Text distance_id;

    @FXML
    private Text slot;

    @FXML
    private Button visit_garage;

//    Map<String, Object> garageDetails;

    // Method to set data for a single garage
    public void setGarageDetails(Map<String, Object> garageDetails) {
        visit_garage.setOnAction(event -> {
            // Load the FXML file and create a new scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BookingPlan.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

//             Get the controller after loading the FXML
            bookingPlanController controller = fxmlLoader.getController();

//             Pass the selections to the new controller
            controller.garageDetails(garageDetails);

            // Create and set the new stage
            Stage stage = new Stage();
            stage.setTitle("Requirements");
            stage.setScene(scene);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) visit_garage.getScene().getWindow();
            currentStage.close();
        });
        // Setting values to the UI components
        garage_address.setText( garageDetails.get("address")+"");

        fare.setText("9");
        distance_id.setText(garageDetails.get("distance")+" km");
        slot.setText("5");


    }

    // You can also add an event handler for the "Visit" button if needed
//    @FXML
//    private void handleVisitButtonClick(Map<String, Object> garageDetails) {
//        System.out.println(garageDetails.get("email"));
//        // Implement navigation or other actions for visiting the garage
//    }
}
