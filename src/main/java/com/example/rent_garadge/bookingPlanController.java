package com.example.rent_garadge;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
//import java.time.LocalDate;
import java.util.*;

public class bookingPlanController {

    @FXML
    private TextField Plate_num; // Vehicle number text field

    @FXML
    private DatePicker date; // Date picker
    @FXML
    private Text area;

    @FXML
    private CheckBox parkNowCheckBox; // Checkbox for immediate parking

    @FXML
    private CheckBox oneHourCheckBox; // 1-hour duration checkbox

    @FXML
    private CheckBox twoHourCheckBox; // 2-hour duration checkbox

    @FXML
    private CheckBox fourHourCheckBox; // 4-hour duration checkbox

    @FXML
    private CheckBox slot01CheckBox; // Parking slot P01 checkbox

    @FXML
    private CheckBox slot02CheckBox; // Parking slot P02 checkbox

    @FXML
    private CheckBox slot03CheckBox; // Parking slot P03 checkbox

    @FXML
    private CheckBox slot04CheckBox; // Parking slot P04 checkbox

    @FXML
    private Button Next; // Next button

    @FXML
    private Text current_date;
    @FXML
    private Button BackButton; // Back button image

    Map<String, Object> rentDetails;
    Map<String, Object> slot;

    Map<String, Object> garageDetails;
    private String dateFormated;

    public void garageDetails(Map<String, Object> garageDetails) {
        this.garageDetails=garageDetails;
        System.out.println(garageDetails);
        String address = garageDetails.get("address")+"";
        String city = garageDetails.get("city")+"";

// Make the first character uppercase for both address and city
        String formattedAddress = address.substring(0, 1).toUpperCase() + address.substring(1);
        String formattedCity = city.substring(0, 1).toUpperCase() + city.substring(1);

// Set the text in the area
        area.setText(formattedAddress + "," + formattedCity);

        slot=FirebaseConfig.getUserData("garage_slot",garageDetails.get("email")+"");


        rentDetails=garageDetails;
        // Set slot availability (true or false from the slot data map)
        System.out.println(slot);
        setupSlotAvailability();
    }



    // Initialize the controller
    @FXML
    public void initialize() {
        // Set default date to today
//        date.setValue(LocalDate.now());
//
        // Set up mutual exclusivity for duration checkboxes
        setupDurationCheckBoxLogic();

        // Set up mutual exclusivity for parking slot checkboxes
        setupParkingSlotCheckBoxLogic();

        // Set slot availability (true or false from the slot data map)
        Calendar c = Calendar.getInstance();
        String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        dateFormated =  c.get(Calendar.DATE) + " "+ month +", " + c.get(Calendar.YEAR);
        current_date.setText(dateFormated);

        // Disable the Next button initially
        Next.setDisable(true);

        // Set listeners to validate when any checkbox is clicked
        setupValidationLogic();

        Next.setOnAction(event -> handleNextButtonClick());
        BackButton.setOnAction(event -> {
            // Load the FXML file and create a new scene
            try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("homes.fxml"));
            Scene scene = new Scene(fxmlLoader.load());


            // Create and set the new stage
            Stage stage = new Stage();
            stage.setTitle("Requirements");
            stage.setScene(scene);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) BackButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        });
    }
    // Setup validation logic to check if all required conditions are met
    private void setupValidationLogic() {
        parkNowCheckBox.setOnAction(event -> validateForm());
        oneHourCheckBox.setOnAction(event -> validateForm());
        twoHourCheckBox.setOnAction(event -> validateForm());
        fourHourCheckBox.setOnAction(event -> validateForm());
        slot01CheckBox.setOnAction(event -> validateForm());
        slot02CheckBox.setOnAction(event -> validateForm());
        slot03CheckBox.setOnAction(event -> validateForm());
        slot04CheckBox.setOnAction(event -> validateForm());
    }

    private void validateForm() {
        // Check if 'parkNowCheckBox' is selected
        boolean isParkNowSelected = parkNowCheckBox.isSelected();

        // Check if any duration is selected
        boolean isDurationSelected = oneHourCheckBox.isSelected() || twoHourCheckBox.isSelected() || fourHourCheckBox.isSelected();

        // Check if any parking slot is selected
        boolean isSlotSelected = slot01CheckBox.isSelected() || slot02CheckBox.isSelected() || slot03CheckBox.isSelected() || slot04CheckBox.isSelected();

        // Enable the 'Next' button only if all conditions are met
        if (isParkNowSelected && isDurationSelected && isSlotSelected) {
            Next.setDisable(false); // Enable Next button
        } else {
            Next.setDisable(true);  // Keep Next button disabled
        }
    }

    private void setupSlotAvailability() {
        // Assuming 'slot' contains the slot data fetched from FirebaseConfig in the form of a map
        // Example: slot.get("slot1") returns true or false

        if (slot != null) {
            updateSlotUI(slot01CheckBox, (Boolean) slot.get("slot1"));
            updateSlotUI(slot02CheckBox, (Boolean) slot.get("slot2"));
            updateSlotUI(slot03CheckBox, (Boolean) slot.get("slot3"));
            updateSlotUI(slot04CheckBox, (Boolean) slot.get("slot4"));
        }
    }

    // Helper method to update the checkbox UI based on slot availability
    private void updateSlotUI(CheckBox checkBox, boolean isAvailable) {
        if (isAvailable) {
            checkBox.setStyle("-fx-background-color: #006f04;"); // Set green background for available slots
            checkBox.setDisable(false); // Enable the checkbox
        } else {
            checkBox.setStyle("-fx-background-color: #7b7b7b;"); // Set red background for unavailable slots
            checkBox.setDisable(true); // Disable the checkbox
        }
    }

    // Handle the "Next" button click

    @FXML
    private void handleNextButtonClick() {
        // Initialize rentDetails map
        rentDetails = new HashMap<>();

        // Get the vehicle number
        String vehicleNumber = Plate_num.getText();

        // Get the selected date
//        LocalDate selectedDate = date.getValue();

        // Get the parking preferences
        boolean parkNow = parkNowCheckBox.isSelected();
        String duration = getSelectedDuration();
        String slot = getSelectedSlot();

        // Store values in the rentDetails map
        rentDetails.put("vehicleNumber", vehicleNumber);
//        rentDetails.put("selectedDate", selectedDate);
        rentDetails.put("parkNow", parkNow);
        rentDetails.put("duration", duration);
        rentDetails.put("slot", slot);
        rentDetails.put("garageowner",garageDetails.get("email"));
        rentDetails.put("renter",RentGaradge.user_id);
        rentDetails.put("address",garageDetails.get("address"));
        rentDetails.put("city",garageDetails.get("city"));
        Random random = new Random();
        int randomValue = Math.abs(random.nextInt()); // Ensures the value is non-negative
        rentDetails.put("random", randomValue * 500 + "");


        // Print values for testing (You can handle the data here, like passing it to another method)
//        System.out.println("Vehicle Number: " + vehicleNumber);
//        System.out.println("Selected Date: " + selectedDate);
//        System.out.println("Park Now: " + parkNow);
//        System.out.println("Duration: " + duration);
//        System.out.println("Selected Slot: " + slot);

        // Proceed to the next window
        System.out.println(rentDetails);
        openNextWindow(rentDetails);
    }



    private void openNextWindow(Map<String, Object> selections) {
        try {
            // Load the FXML file and create a new scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BookingData.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Get the controller after loading the FXML
            BookingdataController controller = fxmlLoader.getController();

            // Pass the selections to the new controller
            controller.garageDetails(selections);

            // Create and set the new stage
            Stage stage = new Stage();
            stage.setTitle("Requirements");
            stage.setScene(scene);
            stage.show();

            // Close the current stage
            Stage currentStage = (Stage) Next.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get the selected parking duration
    private String getSelectedDuration() {
        if (oneHourCheckBox.isSelected()) {
            return "1 hour";
        } else if (twoHourCheckBox.isSelected()) {
            return "2 hours";
        } else if (fourHourCheckBox.isSelected()) {
            return "4 hours";
        }
        return "No duration selected";
    }

    // Get the selected parking slot
    private String getSelectedSlot() {
        if (slot01CheckBox.isSelected()) {
            return "slot1";
        } else if (slot02CheckBox.isSelected()) {
            return "slot2";
        } else if (slot03CheckBox.isSelected()) {
            return "slot3";
        } else if (slot04CheckBox.isSelected()) {
            return "slot4";
        }
        return "No slot selected";
    }

    // Handle the back button click
    @FXML
    private void handleBackButtonClick(MouseEvent event) {
        // Logic to go back to the previous page
        System.out.println("Back button clicked.");
    }

    // Set up mutual exclusivity for duration checkboxes
    private void setupDurationCheckBoxLogic() {
        oneHourCheckBox.setOnAction(event -> {
            if (oneHourCheckBox.isSelected()) {
                twoHourCheckBox.setSelected(false);
                fourHourCheckBox.setSelected(false);
            }
        });

        twoHourCheckBox.setOnAction(event -> {
            if (twoHourCheckBox.isSelected()) {
                oneHourCheckBox.setSelected(false);
                fourHourCheckBox.setSelected(false);
            }
        });

        fourHourCheckBox.setOnAction(event -> {
            if (fourHourCheckBox.isSelected()) {
                oneHourCheckBox.setSelected(false);
                twoHourCheckBox.setSelected(false);
            }
        });
    }

    // Set up mutual exclusivity for parking slot checkboxes
    private void setupParkingSlotCheckBoxLogic() {
        slot01CheckBox.setOnAction(event -> {
            if (slot01CheckBox.isSelected()) {
                slot02CheckBox.setSelected(false);
                slot03CheckBox.setSelected(false);
                slot04CheckBox.setSelected(false);
            }
        });

        slot02CheckBox.setOnAction(event -> {
            if (slot02CheckBox.isSelected()) {
                slot01CheckBox.setSelected(false);
                slot03CheckBox.setSelected(false);
                slot04CheckBox.setSelected(false);
            }
        });

        slot03CheckBox.setOnAction(event -> {
            if (slot03CheckBox.isSelected()) {
                slot01CheckBox.setSelected(false);
                slot02CheckBox.setSelected(false);
                slot04CheckBox.setSelected(false);
            }
        });

        slot04CheckBox.setOnAction(event -> {
            if (slot04CheckBox.isSelected()) {
                slot01CheckBox.setSelected(false);
                slot02CheckBox.setSelected(false);
                slot03CheckBox.setSelected(false);
            }
        });
    }

}
