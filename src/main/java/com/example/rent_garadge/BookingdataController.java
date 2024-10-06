package com.example.rent_garadge;

import com.google.cloud.Timestamp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.Map;

public class BookingdataController {

    @FXML
    private Button pay50;
    Map<String, Object> selections;

    public void garageDetails(Map<String, Object> selections) {
        System.out.println(selections);
        this.selections=selections;
    }
    @FXML
    public void initialize() {
//        pay50.setOnAction(event -> openNextWindow(selections.get("random")+""));
        pay50.setOnAction(event -> openNextWindow());

    }

    private void openNextWindow () {
        try {
            // Load the FXML file and create a new scene
            System.out.println(selections.get("garageowner")+""+selections.get("slot"));
            boolean b=false;
            String val=FirebaseConfig.updateFieldInDocument("garage_slot",selections.get("garageowner")+"",selections.get("slot")+"",(Object) b);
            System.out.println(val);

            // Get the current date and time
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Define the format to include seconds
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Format and print the date and time with seconds
            String formattedDateTime = currentDateTime.format(formatter);
            System.out.println("Current Date and Time: " + formattedDateTime);


            selections.put("start_time", formattedDateTime);

            String som=FirebaseConfig.datainput("rent_list",selections.get("random")+"",selections);
            System.out.println(som);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cencle.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Get the controller after loading the FXML
//            map_controller controller = fxmlLoader.getController();
//
//            // Pass the selections to the new controller
//            controller.garageDetails(random);

            // Create and set the new stage
            Stage stage = new Stage();
            stage.setTitle("Requirements");
            stage.setScene(scene);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) pay50.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
