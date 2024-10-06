package com.example.rent_garadge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cencleController {

    @FXML
    private Text start_time;

    @FXML
    private Text end_time;

    @FXML
    private Text time_remain;

    @FXML
    private Button cencle_button;

    Map<String, Object> renterdetails;
   

    // Date formatter
    // Date formatter for "yyyy-MM-dd HH:mm:ss"
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public void initialize() {
        cencle_button.setOnAction(event ->openSignupWindow());
        try {
            List<Map<String, Object>> rent = FirebaseConfig.getAllGarageDetails("rent_list");
            System.out.println(rent);

            for (Map<String, Object> map : rent) {
                if ("run".equals(map.get("state")) && RentGaradge.user_id.equals(map.get("renter"))) {
                    renterdetails = map;
                    System.out.println("Hellooo:"+map);
                    break; // Exit loop once the matching map is found
                }
            }


        if (renterdetails != null) {
            int hours = Integer.parseInt(renterdetails.get("duration").toString());
            LocalDateTime startTime = LocalDateTime.parse(renterdetails.get("start_time").toString(), formatter);

            // Calculate the end time
            LocalDateTime endTime = startTime.plusHours(hours);
            System.out.println(endTime);

            // Set start and end times in the view
            final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

            start_time.setText(startTime.format(timeFormatter));
            end_time.setText(endTime.format(timeFormatter));


            // Start a timer to update remaining time every second
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> updateRemainingTime(endTime))
            );
            timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
            timeline.play();
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void openSignupWindow() {
        try {
            String val=FirebaseConfig.updateFieldInDocument("garage_slot",renterdetails.get("garageowner")+"",renterdetails.get("slot")+"",(Object) true);
            System.out.println(val);
             val=FirebaseConfig.updateFieldInDocument("rent_list",renterdetails.get("random")+"","state",(Object)"end");
            System.out.println(val);

            LocalDateTime currentDateTime = LocalDateTime.now();

            // Define the format to include seconds
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Format and print the date and time with seconds
            String formattedDateTime = currentDateTime.format(formatter);
            System.out.println(formattedDateTime);

            try {
                val = FirebaseConfig.updateFieldInDocument("rent_list", renterdetails.get("random") + "","end_time",formattedDateTime);
                System.out.println(val);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Homes");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            Stage currentStage = (Stage) cencle_button.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateRemainingTime(LocalDateTime endTime) {
        // Calculate the remaining time
        LocalDateTime currentTime = LocalDateTime.now();
        long minutesRemaining = java.time.Duration.between(currentTime, endTime).toMinutes();

        if (minutesRemaining <= 0) {
            time_remain.setText("Time's up");
        } else {
            long hoursRemaining = minutesRemaining / 60;
            long remainingMinutes = minutesRemaining % 60;
            time_remain.setText(String.format("Time Remaining: %d h %d m", hoursRemaining, remainingMinutes));
        }
    }
}
